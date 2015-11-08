package piotrsliwa.blackbocs.execution;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomCommandExecErrorOutputReaderTest {
    
    private final CustomCommandExec customExecMock = mock(CustomCommandExec.class);
    private final CustomCommandExecErrorOutputReader sut = new CustomCommandExecErrorOutputReader(customExecMock);

    @Test
    public void shallReadFromStdOutput() throws Exception {
        String str = "dummyString";
        when(customExecMock.readLineFromErrorOutput()).thenReturn(str);
        assertEquals(str, sut.call());
    }
    
}
