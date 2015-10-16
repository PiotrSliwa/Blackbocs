package piotrsliwa.blackbocs.tester;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomCommandSutStandardOutputReaderTest {
    
    private final CustomCommandSut customSutMock = mock(CustomCommandSut.class);
    private final CustomCommandSutStandardOutputGetter sut = new CustomCommandSutStandardOutputGetter(customSutMock);

    @Test
    public void shallReadFromStdOutput() throws Exception {
        String str = "dummyString";
        when(customSutMock.readLineFromStandardOutput()).thenReturn(str);
        assertEquals(str, sut.call());
    }
    
}
