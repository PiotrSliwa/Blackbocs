package piotrsliwa.blackbocs.tester;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class SutRunnerTest {
    
    private final Sut sutMock = mock(Sut.class);
    private final SutReseter sutReseterMock = mock(SutReseter.class);
    private final ReadinessChecker readinessCheckerMock = mock(ReadinessChecker.class);
    private final OutputCollector outputCollectorMock = mock(OutputCollector.class);
    private final SutRunner sut = new SutRunner(sutMock, sutReseterMock, readinessCheckerMock, outputCollectorMock);
    
    @Test(expected = SutNotProvidedException.class)
    public void shallThrowErrorWhenRunAndSutIsNull() throws Exception {
        SutRunner sut = new SutRunner(null, sutReseterMock, readinessCheckerMock, outputCollectorMock);
        sut.run();
    }
    
    @Test
    public void mustNotCrashWhenKilledAndSutIsNull() throws Exception {
        SutRunner sut = new SutRunner(null, sutReseterMock, readinessCheckerMock, outputCollectorMock);
        sut.kill();
    }
    
    @Test
    public void shallAlwaysRunSutAndOutputCollectorWhenTheyAreProvided() throws Exception {
        when(readinessCheckerMock.isReady()).thenReturn(true);
        sut.run();
        verify(sutMock).run();
        verify(outputCollectorMock).run();
    }
    
    @Test
    public void mustNotCrashWhenOutputCollectorIsNull() throws Exception {
        SutRunner sut = new SutRunner(sutMock, sutReseterMock, readinessCheckerMock, null);
        when(readinessCheckerMock.isReady()).thenReturn(true);
        when(sutReseterMock.reset()).thenReturn(true);
        sut.run();
        sut.reset();
        sut.kill();
    }
    
    @Test
    public void shallWaitForSutToBeReady() throws Exception {
        when(readinessCheckerMock.isReady()).thenReturn(false, false, true);
        sut.run();
        verify(readinessCheckerMock, atLeast(3)).isReady();
    }
    
    @Test(expected = SutFinishedButItsNotReadyException.class)
    public void shallThrowAnExceptionWhenSutHasFinishedButItsNotReady() throws Exception {
        when(sutMock.isFinished()).thenReturn(false, false, true);
        when(readinessCheckerMock.isReady()).thenReturn(false);
        sut.run();
        verify(sutMock, atLeast(3)).isFinished();
    }
    
    public void shallAlwaysTratSutAsReadyWhenNullReadinessCheckerProvidedInConstructor() throws Exception {
        SutRunner sut = new SutRunner(sutMock, sutReseterMock, null, outputCollectorMock);
        when(sutMock.isFinished()).thenReturn(true);
        sut.run();
    }
    
    @Test(expected = SutExecutionException.class)
    public void shallThrowExceptionAndStopWaitingWhenSutErrorOccured() throws Exception {
        when(sutMock.errorOccured()).thenReturn(false, false, true);
        sut.run();
        verify(sutMock, times(3)).errorOccured();
    }
    
    @Test
    public void killShallKillSutAndOutputCollectorAndItIsNotRunningAnymore() throws Exception {
        when(readinessCheckerMock.isReady()).thenReturn(true);
        
        sut.run();
        sut.kill();
        
        verify(sutMock).finish();
        verify(outputCollectorMock).finish();
    }
    
    @Test(expected = UnableToResetException.class)
    public void resetShallThrowErrorAndPreventFromClearingOutputCollectorWhenCannotReset() {
        when(sutReseterMock.reset()).thenReturn(false);
        sut.reset();
        verify(outputCollectorMock, never()).clear();
    }
    
    @Test
    public void mustNotCrashWhenAskedForResetButNullSutReseterHasBeenProvidedInConstructor() {
        SutRunner sutRunner = new SutRunner(sutMock, null, readinessCheckerMock, outputCollectorMock);
        sutRunner.reset();
    }
    
    @Test
    public void testGettingAndSettingOfQueryInterval() {
        assertEquals(100, sut.getQueryInterval());
        sut.setQueryInterval(42);
        assertEquals(42, sut.getQueryInterval());
    }
    
    @Test
    public void shallApplyQueryIntervalWithinRunningReadynessQueryingLoop() throws Exception {
        when(readinessCheckerMock.isReady()).thenReturn(false, false, true);
        sut.setQueryInterval(100);
        long start = System.currentTimeMillis();
        sut.run();
        long end = System.currentTimeMillis();
        assertTrue(end - start >= 200);
    }
    
}
