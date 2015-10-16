package piotrsliwa.blackbocs.tester;

public class SutNotProvidedException extends Exception {

    public SutNotProvidedException() {
        super("Sut has not been provided!");
    }
}
