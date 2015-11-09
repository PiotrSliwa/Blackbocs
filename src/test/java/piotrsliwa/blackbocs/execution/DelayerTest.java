package piotrsliwa.blackbocs.execution;

import org.junit.Test;
import static org.junit.Assert.*;

public class DelayerTest {

    private static final int DELAY_MS = 100;
    private final Delayer sut = new Delayer(DELAY_MS);

    public long runSleepAndReturnTimeSlept() throws InterruptedException {
        long start = System.currentTimeMillis();
        sut.sleep();
        long end = System.currentTimeMillis();
        return end - start;
    }

    @Test
    public void shallSleepForAPeriodGivenDuringConstruction() throws InterruptedException {
        long timePeriod = runSleepAndReturnTimeSlept();
        assertTrue(timePeriod >= DELAY_MS);
    }

    @Test
    public void shallSleepForAPeriodGivenInSetter() throws InterruptedException {
        int delay = DELAY_MS / 2;
        sut.setDelay(delay);
        long timePeriod = runSleepAndReturnTimeSlept();
        assertTrue(timePeriod >= delay && timePeriod <= DELAY_MS);
    }

}
