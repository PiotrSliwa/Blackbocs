package piotrsliwa.blackbocs.testing;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import piotrsliwa.blackbocs.execution.Action;
import piotrsliwa.blackbocs.execution.ActionFailedException;

public class TestSuiteRunner {
    
    private final List<Action> beforeTestSuiteActions;
    private final List<Action> beforeEachTestActions;
    private final List<Test> tests;
    private final List<Action> afterEachTestActions;
    private final List<Action> afterTestSuiteActions;

    public TestSuiteRunner(
            List<Action> beforeTestSuiteActions,
            List<Action> beforeEachTestActions,
            List<Test> tests,
            List<Action> afterEachTestActions,
            List<Action> afterTestSuiteActions) {
        this.beforeTestSuiteActions = beforeTestSuiteActions;
        this.beforeEachTestActions = beforeEachTestActions;
        this.tests = tests;
        this.afterEachTestActions = afterEachTestActions;
        this.afterTestSuiteActions = afterTestSuiteActions;
    }
    
    private ActionFailedException runAllActions(List<Action> actionsToBeRun) {
        for (Action action : actionsToBeRun) {
            try { action.run(); }
            catch (ActionFailedException ex) { return ex; }
        }
        return null;
    }
    
    public void run() throws ActionFailedException, TestFailedException {
        ActionFailedException actionFailedException = null;
        if ((actionFailedException = runAllActions(beforeTestSuiteActions)) != null)
            throw actionFailedException;
        for (Test test : tests) {
            if ((actionFailedException = runAllActions(beforeEachTestActions)) != null) {
                runAllActions(afterTestSuiteActions);
                throw actionFailedException;
            }
            try {
                test.run();
            } catch (TestFailedException ex) {
                runAllActions(afterEachTestActions);
                runAllActions(afterTestSuiteActions);
                throw ex;
            }
            if ((actionFailedException = runAllActions(afterEachTestActions)) != null) {
                runAllActions(afterTestSuiteActions);
                throw actionFailedException;
            }
        }
        if ((actionFailedException = runAllActions(afterTestSuiteActions)) != null) {
            throw actionFailedException;
        }
    }
    
}
