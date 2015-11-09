package piotrsliwa.blackbocs.execution;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomCommandExecStandardOutputReaderTest {

    private final CustomCommandExec customExecMock = mock(CustomCommandExec.class);
    private final CustomCommandExecStandardOutputGetter sut = new CustomCommandExecStandardOutputGetter(customExecMock);

    @Test
    public void shallReadFromStdOutput() throws Exception {
        String str = "dummyString";
        when(customExecMock.readLineFromStandardOutput()).thenReturn(str);
        assertEquals(str, sut.call());
    }

}
