package piotrsliwa.blackbocs;

import piotrsliwa.blackbocs.execution.ExecRunner;

public class EnvironmentWithSut extends Environment {

    private final Environment environment;
    private final ExecRunner execRunner;

    EnvironmentWithSut(Environment environment, ExecRunner execRunner) {
        this.environment = environment;
        this.execRunner = execRunner;
    }

    public EnvironmentWithTestSuite withTestSuite(Object testContainer) {
        return new EnvironmentWithTestSuite(this, new TestSuite(testContainer));
    }

    @Override
    void run() throws Exception {
        environment.run();
        execRunner.run();
    }

}
