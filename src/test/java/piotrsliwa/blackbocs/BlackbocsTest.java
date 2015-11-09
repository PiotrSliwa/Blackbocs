package piotrsliwa.blackbocs;

import org.junit.Test;
import piotrsliwa.blackbocs.execution.ExecNotProvidedException;

public class BlackbocsTest {

    @Test(expected = ExecNotProvidedException.class)
    public void shallThrowAnErrorWhenNullSutProvided() throws Exception {
        Blackbocs.createEnvironment().withSut(null).withTestSuite(null).run();
    }

}
