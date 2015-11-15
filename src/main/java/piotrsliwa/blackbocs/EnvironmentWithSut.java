package piotrsliwa.blackbocs;

import piotrsliwa.blackbocs.execution.Exec;
import piotrsliwa.blackbocs.execution.ExecRunner;

public class EnvironmentWithSut extends Environment {

    private final Exec sut;

    EnvironmentWithSut(Exec sut) {
        this.sut = sut;
    }

    @Override
    void run() throws Exception {
        ExecRunner execRunner = new ExecRunner(sut, null, null, null);
        execRunner.run();
    }

    EnvironmentWithTestSuite withTestSuite(TestSuite testSuite) {
        return new EnvironmentWithTestSuite(this, testSuite);
    }

}
