package piotrsliwa.blackbocs;

import org.junit.Test;

public class TestSuiteTest {

    @Test(expected = CannotFindAnyMatchingTestException.class)
    public void shallThrowWhenConstructedWithNull() throws CannotFindAnyMatchingTestException {
        TestSuite sut = new TestSuite(null);
        sut.runAllTests();
    }

}
