package piotrsliwa.blackbocs.execution;

import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomCommandExecTest {
    
    private final static String DUMMY_COMMAND = "dummyCommand";
    
    private final Runtime runtimeMock = mock(Runtime.class);
    private final Process processMock = mock(Process.class);
    private final InputStream inputStreamMock = mock(InputStream.class);
    
    private final CustomCommandExec sut = new CustomCommandExec(DUMMY_COMMAND, runtimeMock);
    
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
        CustomCommandExec sut = new CustomCommandExec(null, null);
        sut.run();
        assertFalse(sut.isRunning());
        assertTrue(sut.errorOccured());
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
    public void finishShallNotDoAnythingWhenExecIsNotRun() throws IOException {
        configSuccessfulCommandExecution();
        sut.finish();
        verify(processMock, never()).destroy();
        assertFalse(sut.isFinished());
    }
    
    @Test
    public void shallBeAbleToFinishExecProcess() throws IOException {
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
