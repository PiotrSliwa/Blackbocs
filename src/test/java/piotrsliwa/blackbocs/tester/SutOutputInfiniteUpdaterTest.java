package piotrsliwa.blackbocs.tester;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SutOutputInfiniteUpdaterTest {
    
    private final Sut sutMock = mock(Sut.class);
    
    private final SutOutputInfiniteUpdater sut = new SutOutputInfiniteUpdater(sutMock) {
        @Override
        public String getOutputChunk() {
            return Integer.toString(dummyNumber++);
        }
        private int dummyNumber = 0;
    };
    
    private static final String strOne = "0";
    private static final String strTwo = "1";
    
    @Test
    public void shallUpdateOutputUntilSutIsRunningWhenAllOutputsAreNotNull() throws Exception {
        when(sutMock.isFinished()).thenReturn(false, false, true);
        sut.run();
        assertEquals(2, sut.getOutput().size());
        assertEquals(strOne, sut.getOutput().get(0));
        assertEquals(strTwo, sut.getOutput().get(1));
    }
    
    @Test
    public void shallNotUpdateWithNullOutput() throws Exception {
        
        SutOutputInfiniteUpdater sut = new SutOutputInfiniteUpdater(sutMock) {
            @Override
            public String getOutputChunk() {
                return calledTimes++ == 0 ? null : strOne;
            }
            private int calledTimes = 0;
        };
        
        when(sutMock.isFinished()).thenReturn(false, false, true);
        sut.run();
        assertEquals(1, sut.getOutput().size());
        assertEquals(strOne, sut.getOutput().get(0));
    }
    
    @Test
    public void testGettingAndSettingOfQueryInterval() {
        assertEquals(100, sut.getQueryInterval());
        sut.setQueryInterval(42);
        assertEquals(42, sut.getQueryInterval());
    }
    
    @Test
    public void shallApplyQueryIntervalWithinRunningLoop() {
        when(sutMock.isFinished()).thenReturn(false, false, true);
        sut.setQueryInterval(100);
        long start = System.currentTimeMillis();
        sut.run();
        long end = System.currentTimeMillis();
        assertTrue(end - start > 200);
    }
    
}
