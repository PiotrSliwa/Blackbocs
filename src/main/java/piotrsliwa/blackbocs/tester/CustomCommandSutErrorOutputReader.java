package piotrsliwa.blackbocs.tester;

import java.util.concurrent.Callable;

public class CustomCommandSutErrorOutputReader implements Callable<String> {
    
    private final CustomCommandSut customSut;

    public CustomCommandSutErrorOutputReader(CustomCommandSut customSut) {
        this.customSut = customSut;
    }

    @Override
    public String call() throws Exception {
        return customSut.readLineFromErrorOutput();
    }
    
}
