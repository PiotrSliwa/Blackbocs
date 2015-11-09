package piotrsliwa.blackbocs.execution;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.*;

public class ConsoleOutputCollectorTest {

    private static final String STRING_1 = "smellycat";
    private static final String STRING_2 = "doggycoin";
    private static final String NULL_STRING = null;

    private final ConsoleOutputProvider consoleOutputProviderMock = mock(ConsoleOutputProvider.class);
    private final Delayer queryDelayerMock = mock(Delayer.class);
    private final ConsoleOutputCollector sut = new ConsoleOutputCollector(consoleOutputProviderMock);

    private void sleepForADummySafePeriodOfTime() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsoleOutputCollectorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void runSutAndSleepForAWhileJustToBeSafe() {
        sut.run();
        sleepForADummySafePeriodOfTime();
    }

    private void assertContainsTwoMemberStrings(List<String> output) {
        assertEquals(2, output.size());
        assertEquals(STRING_1, output.get(0));
        assertEquals(STRING_2, output.get(1));
    }

    @Before
    public void setQueryDelayer() {
        sut.setQueryDelayer(queryDelayerMock);
    }

    @Test
    public void shallReturnCollectedStandardOutput() throws InterruptedException {
        when(consoleOutputProviderMock.readLineFromStandardOutput()).thenReturn(STRING_1, STRING_2, NULL_STRING);
        runSutAndSleepForAWhileJustToBeSafe();
        assertContainsTwoMemberStrings(sut.getStandardOutput());
        verify(queryDelayerMock, atLeast(3)).sleep();
        sut.finish();
    }

    @Test
    public void shallNotCollectNullNorEmptyStandardOutput() throws InterruptedException {
        String emptyString = "";
        when(consoleOutputProviderMock.readLineFromStandardOutput()).thenReturn(STRING_1, NULL_STRING, STRING_2, emptyString, NULL_STRING);
        runSutAndSleepForAWhileJustToBeSafe();
        assertContainsTwoMemberStrings(sut.getStandardOutput());
        verify(queryDelayerMock, atLeast(3)).sleep();
        sut.finish();
    }

    @Test
    public void shallReturnCollectedErrorOutput() throws InterruptedException {
        when(consoleOutputProviderMock.readLineFromErrorOutput()).thenReturn(STRING_1, STRING_2, NULL_STRING);
        runSutAndSleepForAWhileJustToBeSafe();
        assertContainsTwoMemberStrings(sut.getErrorOutput());
        verify(queryDelayerMock, atLeast(3)).sleep();
        sut.finish();
    }

    @Test
    public void shallNotCollectNullNorEmptyErrorOutput() throws InterruptedException {
        String emptyString = "";
        when(consoleOutputProviderMock.readLineFromErrorOutput()).thenReturn(STRING_1, null, STRING_2, emptyString, NULL_STRING);
        runSutAndSleepForAWhileJustToBeSafe();
        assertContainsTwoMemberStrings(sut.getErrorOutput());
        verify(queryDelayerMock, atLeast(3)).sleep();
        sut.finish();
    }

    @Test
    public void bothStandardAndErrorCollectedOutputsShallBeCleared() throws InterruptedException {
        String str = "dummyString";
        when(consoleOutputProviderMock.readLineFromStandardOutput()).thenReturn(str);
        when(consoleOutputProviderMock.readLineFromErrorOutput()).thenReturn(str);
        runSutAndSleepForAWhileJustToBeSafe();
        sut.finish();
        sleepForADummySafePeriodOfTime();
        sut.clear();
        assertEquals(0, sut.getErrorOutput().size());
        assertEquals(0, sut.getStandardOutput().size());
    }

}
