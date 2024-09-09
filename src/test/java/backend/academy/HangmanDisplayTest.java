package backend.academy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HangmanDisplayTest {

    private HangmanDisplay display;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        display = new HangmanDisplay();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testUpdateDisplayWithSystemOut() {
        display.updateDisplay("hello", Set.of('h', 'l'), new OutputWriter() {
            @Override
            public void print(String message) {
                System.out.print(message);
            }

            @Override
            public void println(String message) {
                System.out.println(message);
            }

            @Override
            public void writeLine(String message) {
                System.out.println(message);
            }
        });

        String output = outContent.toString();
        assertTrue(output.contains("Word: h _ l l _"));
    }

    @Test
    void testUpdateDisplayWithMock() {
        OutputWriter mockOutputWriter = mock(OutputWriter.class);
        display.updateDisplay("hello", Set.of('h', 'l'), mockOutputWriter);

        verify(mockOutputWriter).println("Word: h _ l l _");
    }

    @Test
    void testDrawHangman() {
        OutputWriter mockOutputWriter = mock(OutputWriter.class);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        display.drawHangman(0, mockOutputWriter);
        verify(mockOutputWriter).println(captor.capture());

        // Убираем лишние пробелы и переносы строк
        String capturedOutput = captor.getValue().replaceAll("\\s+", "");
        String expectedOutput = "\n\n\n\n\n".replaceAll("\\s+", "");
        assertEquals(expectedOutput, capturedOutput);

        display.drawHangman(6, mockOutputWriter);
        verify(mockOutputWriter, times(2)).println(captor.capture());

        capturedOutput = captor.getValue().replaceAll("\\s+", "");
        expectedOutput = "_____\n|   |\n|   O\n|  /|\\\n|  / \\\n|____".replaceAll("\\s+", "");
        assertEquals(expectedOutput, capturedOutput);
    }

}
