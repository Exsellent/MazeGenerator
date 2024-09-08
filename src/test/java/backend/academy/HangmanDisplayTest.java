package backend.academy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.Set;

class HangmanDisplayTest {

    private HangmanDisplay display;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private OutputWriter mockOutputWriter;

    @BeforeEach
    void setUp() {
        display = new HangmanDisplay();
        mockOutputWriter = new ConsoleOutputWriter();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testUpdateDisplay() {
        display.updateDisplay("hello", Set.of('h', 'l'), mockOutputWriter);
        String output = outContent.toString();
        assertTrue(output.contains("Word: h_ll_"));
    }

    @Test
    void testDrawHangman() {
        display.drawHangman(0, mockOutputWriter);
        String output = outContent.toString();
        assertTrue(output.contains("\n\n\n\n\n"));

        outContent.reset();
        display.drawHangman(6, mockOutputWriter);
        output = outContent.toString();
        assertTrue(output.contains("_____\n|   |\n|   O\n|  /|\\\n|  / \\\n|____"));
    }
}
