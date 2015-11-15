package piotrsliwa.blackbocs;

public class NullTestSuiteException extends Exception {
    public NullTestSuiteException() {
        super("Null test suite provided!");
    }
}
