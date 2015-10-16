package piotrsliwa.blackbocs.tester;

import java.util.concurrent.Callable;

public class CustomCommandSutStandardOutputGetter implements Callable<String> {
    
    private final CustomCommandSut customSut;

    public CustomCommandSutStandardOutputGetter(CustomCommandSut customSut) {
        this.customSut = customSut;
    }

    @Override
    public String call() throws Exception {
        return customSut.readLineFromStandardOutput();
    }
    
}
