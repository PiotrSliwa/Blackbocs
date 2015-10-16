package piotrsliwa.blackbocs.tester;

public class SutFinishedButItsNotReadyException extends Exception {

    public SutFinishedButItsNotReadyException() {
        super("Sut has finished but it's still not ready!");
    }
}
