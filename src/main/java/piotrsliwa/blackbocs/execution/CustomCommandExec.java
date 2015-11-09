package piotrsliwa.blackbocs.execution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomCommandExec implements Exec, ConsoleOutputProvider {

    private final String command;
    private final Runtime runtime;

    private Process process;
    private BufferedReader bufferedExecStdOutput;
    private BufferedReader bufferedExecErrOutput;
    private boolean hasErrorOccured = false;
    private boolean hasBeenStarted = false;

    public CustomCommandExec(String command) {
        this.command = command;
        this.runtime = Runtime.getRuntime();
    }

    public CustomCommandExec(String command, Runtime runtime) {
        this.command = command;
        this.runtime = runtime;
    }

    @Override
    public boolean errorOccured() {
        return hasErrorOccured;
    }

    @Override
    public boolean isRunning() {
        return process != null && process.isAlive();
    }

    @Override
    public boolean isFinished() {
        return !isRunning() && hasBeenStarted;
    }

    @Override
    public void finish() {
        if (process != null) {
            process.destroy();
            process = null;
        }
    }

    @Override
    public void run() {
        try {
            process = runtime.exec(command);
            hasBeenStarted = true;
            bufferedExecStdOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            bufferedExecErrOutput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        } catch (Exception ex) {
            hasErrorOccured = true;
            Logger.getLogger(CustomCommandExec.class.getName()).log(Level.SEVERE, "Blackbocs Error: CustomExec.run()", ex);
        }
    }

    @Override
    public String readLineFromStandardOutput() {
        return readLine(bufferedExecStdOutput);
    }

    @Override
    public String readLineFromErrorOutput() {
        return readLine(bufferedExecErrOutput);
    }

    private String readLine(BufferedReader reader) {
        if (reader != null) {
            try {
                String str = reader.readLine();
                if (str != null)
                    return str;
            } catch (IOException ex) {
                Logger.getLogger(CustomCommandExec.class.getName()).log(Level.INFO, "Blackbocs Info", ex);
            }
        }
        return "";
    }

}
