package piotrsliwa.blackbocs.testing;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;
import piotrsliwa.blackbocs.execution.Action;
import piotrsliwa.blackbocs.execution.ActionFailedException;

public class TestSuiteRunnerTest {
    
    private final Action beforeTestSuiteAction = mock(Action.class);
    private final Action beforeTestAction = mock(Action.class);
    private final Test dummyTest = mock(Test.class);
    private final Action afterTestSuiteAction = mock(Action.class);
    private final Action afterTestAction = mock(Action.class);
    
    private final List<Action> beforeTestSuiteActions = new ArrayList<>();
    private final List<Action> beforeEachTestActions = new ArrayList<>();
    private final List<Test> tests = new ArrayList<>();
    private final List<Action> afterEachTestActions = new ArrayList<>();
    private final List<Action> afterTestSuiteActions = new ArrayList<>();
    private final TestSuiteRunner sut = new TestSuiteRunner(
            beforeTestSuiteActions,
            beforeEachTestActions,
            tests,
            afterEachTestActions,
            afterTestSuiteActions);
    
    @Before
    public void addDefaultActions() {
        beforeTestSuiteActions.add(beforeTestSuiteAction);
        beforeEachTestActions.add(beforeTestAction);
        afterTestSuiteActions.add(afterTestSuiteAction);
        afterEachTestActions.add(afterTestAction);
    }
    
    @org.junit.Test
    public void shallDoNothingWhenNoTestProvided() throws TestFailedException, ActionFailedException {
        sut.run();
        verify(dummyTest, never()).run();
    }
    
    @org.junit.Test
    public void shallPerformAllActionsInTheCorrectOrder() throws ActionFailedException, TestFailedException {
        tests.add(dummyTest);
        sut.run();
        
        InOrder order = inOrder(
            beforeTestSuiteAction,
            beforeTestAction,
            dummyTest,
            afterTestSuiteAction,
            afterTestAction);
        
        order.verify(beforeTestSuiteAction).run();
        order.verify(beforeTestAction).run();
        order.verify(dummyTest).run();
        order.verify(afterTestAction).run();
        order.verify(afterTestSuiteAction).run();
    }
    
    @org.junit.Test
    public void shallThrowActionErrorWhenBeforeTestSuiteActionThrowsOne() throws ActionFailedException, TestFailedException {
        tests.add(dummyTest);
        try {
            doThrow(ActionFailedException.class).when(beforeTestSuiteAction).run();
            sut.run();
            fail("No exception has been thrown!");
        } catch (ActionFailedException | TestFailedException ex) {
            verify(beforeTestAction, never()).run();
            verify(dummyTest, never()).run();
            verify(afterTestAction, never()).run();
            verify(afterTestSuiteAction, never()).run();
        }
    }
    
    @org.junit.Test
    public void shallThrowActionErrorWhenBeforeTestActionThrowsOneButShallRunAfterTestSuiteActions() throws TestFailedException, ActionFailedException {
        tests.add(dummyTest);
        try {
            doThrow(ActionFailedException.class).when(beforeTestAction).run();
            sut.run();
            fail("No exception has been thrown!");
        } catch (ActionFailedException | TestFailedException ex) {
            verify(dummyTest, never()).run();
            verify(afterTestAction, never()).run();
            verify(afterTestSuiteAction).run();
        }
    }
    
    @org.junit.Test
    public void shallThrowTestFailureExceptionWhenTestThrowsOneButShallRunAfterTestAndAfterTestSuiteActions() throws ActionFailedException {
        tests.add(dummyTest);
        try {
            doThrow(TestFailedException.class).when(dummyTest).run();
            sut.run();
            fail("No exception has been thrown!");
        } catch (TestFailedException ex) {
            verify(afterTestAction).run();
            verify(afterTestSuiteAction).run();
        }
    }
    
    @org.junit.Test
    public void shallThrowActionErrorWhenAfterTestActionThrowsOneButShallRunAfterTestSuiteActions() throws TestFailedException, ActionFailedException {
        tests.add(dummyTest);
        try {
            doThrow(ActionFailedException.class).when(afterTestAction).run();
            sut.run();
            fail("No exception has been thrown!");
        } catch (ActionFailedException ex) {
            verify(afterTestSuiteAction).run();
        }
    }
    
    @org.junit.Test(expected = ActionFailedException.class)
    public void shallThrowActionErrorWhenAfterTestSuiteActionThrowsOne() throws ActionFailedException, TestFailedException {
        tests.add(dummyTest);
        doThrow(ActionFailedException.class).when(afterTestSuiteAction).run();
        sut.run();
    }
    
}
