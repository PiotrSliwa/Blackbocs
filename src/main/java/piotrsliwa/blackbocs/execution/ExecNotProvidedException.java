package piotrsliwa.blackbocs.execution;

public class ExecNotProvidedException extends Exception {

    public ExecNotProvidedException() {
        super("Exec has not been provided!");
    }
}
