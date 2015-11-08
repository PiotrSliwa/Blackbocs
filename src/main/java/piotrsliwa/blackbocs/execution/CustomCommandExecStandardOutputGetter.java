package piotrsliwa.blackbocs.execution;

import java.util.concurrent.Callable;

public class CustomCommandExecStandardOutputGetter implements Callable<String> {
    
    private final CustomCommandExec customExec;

    public CustomCommandExecStandardOutputGetter(CustomCommandExec customExec) {
        this.customExec = customExec;
    }

    @Override
    public String call() throws Exception {
        return customExec.readLineFromStandardOutput();
    }
    
}
