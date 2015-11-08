package piotrsliwa.blackbocs.execution;

import java.util.concurrent.Callable;

public class CustomCommandExecErrorOutputReader implements Callable<String> {
    
    private final CustomCommandExec customExec;

    public CustomCommandExecErrorOutputReader(CustomCommandExec customExec) {
        this.customExec = customExec;
    }

    @Override
    public String call() throws Exception {
        return customExec.readLineFromErrorOutput();
    }
    
}
