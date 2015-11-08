package piotrsliwa.blackbocs.testing;

public class TestFailedException extends Exception {
    
    private final Test test;
    
    public TestFailedException(Test test) {
        this.test = test;
    }

    public Test getTest() {
        return test;
    }
    
}
