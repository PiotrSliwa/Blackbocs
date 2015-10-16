package piotrsliwa.blackbocs.tester;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomCommandSutErrorOutputReaderTest {
    
    private final CustomCommandSut customSutMock = mock(CustomCommandSut.class);
    private final CustomCommandSutErrorOutputReader sut = new CustomCommandSutErrorOutputReader(customSutMock);

    @Test
    public void shallReadFromStdOutput() throws Exception {
        String str = "dummyString";
        when(customSutMock.readLineFromErrorOutput()).thenReturn(str);
        assertEquals(str, sut.call());
    }
    
}
