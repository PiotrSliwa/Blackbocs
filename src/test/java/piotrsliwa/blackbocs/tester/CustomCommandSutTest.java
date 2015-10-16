package piotrsliwa.blackbocs.tester;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomCommandSutTest {
    
    private final static String DUMMY_COMMAND = "dummyCommand";
    
    private final Runtime runtimeMock = mock(Runtime.class);
    private final Process processMock = mock(Process.class);
    private final InputStream inputStreamMock = mock(InputStream.class);
    
    private final CustomCommandSut sut = new CustomCommandSut(DUMMY_COMMAND, runtimeMock);
    
    private void configSuccessfulCommandExecution() throws IOException {
        when(runtimeMock.exec(any(String.class))).thenReturn(processMock);
        when(processMock.getInputStream()).thenReturn(inputStreamMock);
        when(processMock.getErrorStream()).thenReturn(inputStreamMock);
        when(processMock.isAlive()).thenReturn(true);
    }
    
    @Test
    public void isNotRunningByDefault() {
        assertFalse(sut.isRunning());
    }
    
    @Test
    public void isNotFinishedByDefault() {
        assertFalse(sut.isFinished());
    }
    
    @Test
    public void shallNotBeRunningAndErrorOccuredShallReturnTrueWhenNullCommandProvided() {
        CustomCommandSut customSut = new CustomCommandSut(null, null);
        customSut.run();
        assertFalse(customSut.isRunning());
        assertTrue(customSut.errorOccured());
    }
    
    @Test
    public void shallRunCustomProcessAndBeRunningThen() throws IOException {
        configSuccessfulCommandExecution();
        sut.run();
        verify(runtimeMock).exec(DUMMY_COMMAND);
        assertFalse(sut.errorOccured());
        assertTrue(sut.isRunning());
    }
    
    @Test
    public void finishShallNotDoAnythingWhenSutIsNotRun() throws IOException {
        configSuccessfulCommandExecution();
        sut.finish();
        verify(processMock, never()).destroy();
        assertFalse(sut.isFinished());
    }
    
    @Test
    public void shallBeAbleToFinishSutProcess() throws IOException {
        configSuccessfulCommandExecution();
        sut.run();
        sut.finish();
        verify(processMock).destroy();
    }
    
    @Test
    public void shallBeFinishedWhenFinishedAfterRun() throws IOException {
        configSuccessfulCommandExecution();
        sut.run();
        sut.finish();
        assertTrue(sut.isFinished());
    }
    
    @Test
    public void shallBeNotFinishedAgainWhenRerun() throws IOException {
        configSuccessfulCommandExecution();
        sut.run();
        sut.finish();
        sut.run();
        assertFalse(sut.isFinished());
    }
    
    @Test
    public void errorOccuredShallReturnFalseByDefault() {
        assertFalse(sut.errorOccured());
    }
    
    @Test
    public void errorOccuredShallReturnTrueWhenExceptionCauchtDuringRun() throws IOException {
        when(runtimeMock.exec(DUMMY_COMMAND)).thenThrow(new IOException());
        sut.run();
        assertTrue(sut.errorOccured());
    }
    
}
