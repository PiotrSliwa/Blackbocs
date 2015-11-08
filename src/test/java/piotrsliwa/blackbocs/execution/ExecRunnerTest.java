package piotrsliwa.blackbocs.execution;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class ExecRunnerTest {
    
    private final Exec execMock = mock(Exec.class);
    private final StateReseter stateReseterMock = mock(StateReseter.class);
    private final ReadinessCondition readinessCheckerMock = mock(ReadinessCondition.class);
    private final OutputCollector outputCollectorMock = mock(OutputCollector.class);
    private final Delayer queryDelayerMock = mock(Delayer.class);
    private final ExecRunner sut = new ExecRunner(execMock, stateReseterMock, readinessCheckerMock, outputCollectorMock);
    
    @Before
    public void setQueryDelayer() {
        sut.setQueryDelayer(queryDelayerMock);
    }
    
    @Test(expected = ExecNotProvidedException.class)
    public void shallThrowErrorWhenRunAndExecIsNull() throws Exception {
        ExecRunner sut = new ExecRunner(null, stateReseterMock, readinessCheckerMock, outputCollectorMock);
        sut.run();
    }
    
    @Test
    public void mustNotCrashWhenKilledAndExecIsNull() throws Exception {
        ExecRunner sut = new ExecRunner(null, stateReseterMock, readinessCheckerMock, outputCollectorMock);
        sut.kill();
    }
    
    @Test
    public void shallAlwaysRunExecAndOutputCollectorWhenTheyAreProvided() throws Exception {
        when(readinessCheckerMock.isReady()).thenReturn(true);
        sut.run();
        verify(execMock).run();
        verify(outputCollectorMock).run();
    }
    
    @Test
    public void mustNotCrashWhenOutputCollectorIsNull() throws Exception {
        ExecRunner sut = new ExecRunner(execMock, stateReseterMock, readinessCheckerMock, null);
        when(readinessCheckerMock.isReady()).thenReturn(true);
        when(stateReseterMock.reset()).thenReturn(true);
        sut.run();
        sut.reset();
        sut.kill();
    }
    
    @Test
    public void shallWaitForExecToBeReady() throws Exception {
        when(readinessCheckerMock.isReady()).thenReturn(false, false, true);
        sut.run();
        verify(readinessCheckerMock, times(3)).isReady();
        verify(queryDelayerMock, times(2)).sleep();
    }
    
    @Test(expected = ExecFinishedButNotReadyException.class)
    public void shallThrowAnExceptionWhenExecHasFinishedButItsNotReady() throws Exception {
        when(execMock.isFinished()).thenReturn(false, false, true);
        when(readinessCheckerMock.isReady()).thenReturn(false);
        sut.run();
        verify(execMock, atLeast(3)).isFinished();
    }
    
    public void shallAlwaysTratExecAsReadyWhenNullReadinessCheckerProvidedInConstructor() throws Exception {
        ExecRunner sut = new ExecRunner(execMock, stateReseterMock, null, outputCollectorMock);
        when(execMock.isFinished()).thenReturn(true);
        sut.run();
    }
    
    @Test(expected = ExecutionException.class)
    public void shallThrowExceptionAndStopWaitingWhenExecErrorOccured() throws Exception {
        when(execMock.errorOccured()).thenReturn(false, false, true);
        sut.run();
        verify(execMock, times(3)).errorOccured();
    }
    
    @Test
    public void killShallKillExecAndOutputCollectorAndItIsNotRunningAnymore() throws Exception {
        when(readinessCheckerMock.isReady()).thenReturn(true);
        
        sut.run();
        sut.kill();
        
        verify(execMock).finish();
        verify(outputCollectorMock).finish();
    }
    
    @Test(expected = UnableToResetException.class)
    public void resetShallThrowErrorAndPreventFromClearingOutputCollectorWhenCannotReset() {
        when(stateReseterMock.reset()).thenReturn(false);
        sut.reset();
        verify(outputCollectorMock, never()).clear();
    }
    
    @Test
    public void mustNotCrashWhenAskedForResetButNullExecReseterHasBeenProvidedInConstructor() {
        ExecRunner sut = new ExecRunner(execMock, null, readinessCheckerMock, outputCollectorMock);
        sut.reset();
    }
    
}
