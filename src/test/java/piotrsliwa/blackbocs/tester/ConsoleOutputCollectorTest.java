package piotrsliwa.blackbocs.tester;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConsoleOutputCollectorTest {
    
    private final ConsoleOutputProvider consoleOutputProviderMock = mock(ConsoleOutputProvider.class);
    private final ConsoleOutputCollector sut = new ConsoleOutputCollector(consoleOutputProviderMock);
    
    private void sleepForQueryIntervalTimes(int multiplicationOfQueryIntervalToSleepFor) throws InterruptedException {
        Thread.sleep(multiplicationOfQueryIntervalToSleepFor * sut.getQueryInterval());
    }

    @Test
    public void shallReturnCollectedStandardOutput() throws InterruptedException {
        String strOne = "smellycat";
        String strTwo = "doggycoin";
        when(consoleOutputProviderMock.readLineFromStandardOutput()).thenReturn(strOne, strTwo);
        sut.run();
        sleepForQueryIntervalTimes(3);
        sut.finish();
        assertEquals(strOne, sut.getStandardOutput().get(0));
        assertEquals(strTwo, sut.getStandardOutput().get(1));
    }
    
    @Test
    public void shallNotCollectNullNorEmptyStandardOutput() throws InterruptedException {
        String strOne = "smellycat";
        String strTwo = "doggycoin";
        String emptyString = "";
        when(consoleOutputProviderMock.readLineFromStandardOutput()).thenReturn(strOne, null, strTwo, emptyString);
        sut.run();
        sleepForQueryIntervalTimes(5);
        sut.finish();
        assertEquals(2, sut.getStandardOutput().size());
        assertEquals(strOne, sut.getStandardOutput().get(0));
        assertEquals(strTwo, sut.getStandardOutput().get(1));
    }
    
    @Test
    public void shallReturnCollectedErrorOutput() throws InterruptedException {
        String strOne = "smellycat";
        String strTwo = "doggycoin";
        when(consoleOutputProviderMock.readLineFromErrorOutput()).thenReturn(strOne, strTwo);
        sut.run();
        sleepForQueryIntervalTimes(3);
        sut.finish();
        assertEquals(strOne, sut.getErrorOutput().get(0));
        assertEquals(strTwo, sut.getErrorOutput().get(1));
    }
    
    @Test
    public void shallNotCollectNullNorEmptyErrorOutput() throws InterruptedException {
        String strOne = "smellycat";
        String strTwo = "doggycoin";
        String emptyString = "";
        when(consoleOutputProviderMock.readLineFromErrorOutput()).thenReturn(strOne, null, strTwo, emptyString);
        sut.run();
        sleepForQueryIntervalTimes(5);
        sut.finish();
        assertEquals(2, sut.getErrorOutput().size());
        assertEquals(strOne, sut.getErrorOutput().get(0));
        assertEquals(strTwo, sut.getErrorOutput().get(1));
    }
    
    @Test
    public void bothStandardAndErrorCollectedOutputsShallBeCleared() throws InterruptedException {
        String str = "dummyString";
        when(consoleOutputProviderMock.readLineFromStandardOutput()).thenReturn(str);
        when(consoleOutputProviderMock.readLineFromErrorOutput()).thenReturn(str);
        sut.run();
        sleepForQueryIntervalTimes(5);
        sut.finish();
        sut.clear();
        assertEquals(0, sut.getErrorOutput().size());
        assertEquals(0, sut.getStandardOutput().size());
    }
    
}
