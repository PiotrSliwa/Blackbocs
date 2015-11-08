package piotrsliwa.blackbocs.testing;

public interface Test {
    public String getName();
    public String getTestSuiteName();
    public void run() throws TestFailedException;
}
