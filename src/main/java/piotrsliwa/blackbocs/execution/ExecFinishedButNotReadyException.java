package piotrsliwa.blackbocs.execution;

public class ExecFinishedButNotReadyException extends Exception {

    public ExecFinishedButNotReadyException() {
        super("Exec has finished but it's still not ready!");
    }
}
