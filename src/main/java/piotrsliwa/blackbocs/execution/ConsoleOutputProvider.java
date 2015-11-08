package piotrsliwa.blackbocs.execution;

public interface ConsoleOutputProvider {
    public String readLineFromStandardOutput();
    public String readLineFromErrorOutput();
}
