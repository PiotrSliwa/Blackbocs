package piotrsliwa.blackbocs.execution;

public interface Action {
    public void run() throws ActionFailedException;
}
