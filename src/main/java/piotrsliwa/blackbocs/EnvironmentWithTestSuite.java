package piotrsliwa.blackbocs;

public class EnvironmentWithTestSuite extends Environment {

    private final Environment environment;
    private final TestSuite testSuite;

    EnvironmentWithTestSuite(Environment environment, TestSuite testSuite) {
        this.environment = environment;
        this.testSuite = testSuite;
    }

    @Override
    void run() throws Exception {
        environment.run();
        testSuite.runAllTests();
    }

}
