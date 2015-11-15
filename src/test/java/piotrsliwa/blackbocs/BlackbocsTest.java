package piotrsliwa.blackbocs;

import org.junit.Test;
import static org.mockito.Mockito.*;
import piotrsliwa.blackbocs.execution.Exec;
import piotrsliwa.blackbocs.execution.ExecNotProvidedException;

public class BlackbocsTest {

    @Test(expected = ExecNotProvidedException.class)
    public void shallThrowAnErrorWhenNullSutProvided() throws Exception {
        TestSuite testSuiteMock = mock(TestSuite.class);
        Blackbocs.createEnvironment().withSut(null).withTestSuite(testSuiteMock).run();
    }

    @Test(expected = NullTestSuiteException.class)
    public void shallThrowAnErrorWhenNullTestSuiteProvided() throws Exception {
        Exec execMock = mock(Exec.class);
        Blackbocs.createEnvironment().withSut(execMock).withTestSuite(null).run();
    }

}
