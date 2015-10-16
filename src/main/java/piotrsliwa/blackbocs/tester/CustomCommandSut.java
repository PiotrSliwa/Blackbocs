package piotrsliwa.blackbocs.tester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomCommandSut implements Sut, ConsoleOutputProvider {

    private final String command;
    private final Runtime runtime;
    
    private Process process;
    private BufferedReader bufferedSutStdOutput;
    private BufferedReader bufferedSutErrOutput;
    private boolean hasErrorOccured = false;
    private boolean hasBeenStarted = false;

    public CustomCommandSut(String command) {
        this.command = command;
        this.runtime = Runtime.getRuntime();
    }

    public CustomCommandSut(String command, Runtime runtime) {
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
            bufferedSutStdOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            bufferedSutErrOutput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        } catch (Exception ex) {
            hasErrorOccured = true;
            Logger.getLogger(CustomCommandSut.class.getName()).log(Level.SEVERE, "Blackbocs Error: CustomSut.run()", ex);
        }
    }

    @Override
    public String readLineFromStandardOutput() {
        return readLine(bufferedSutStdOutput);
    }

    @Override
    public String readLineFromErrorOutput() {
        return readLine(bufferedSutErrOutput);
    }
    
    private String readLine(BufferedReader reader) {
        if (reader != null) {
            try {
                String str = reader.readLine();
                if (str != null)
                    return str;
            } catch (IOException ex) {
                Logger.getLogger(CustomCommandSut.class.getName()).log(Level.INFO, "Blackbocs Info", ex);
            }
        }
        return "";
    }
    
}
