package piotrsliwa.blackbocs;

class TestSuite {

    private final Object testContainer;

    TestSuite(Object testContainer) {
        this.testContainer = testContainer;
    }

    void runAllTests() throws CannotFindAnyMatchingTestException {
        throw new CannotFindAnyMatchingTestException();
    }

}
