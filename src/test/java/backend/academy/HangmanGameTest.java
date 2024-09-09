package backend.academy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HangmanGameTest {

    private HangmanGame game;
    private WordBank mockWordBank;
    private InputReader mockInputReader;
    private OutputWriter mockOutputWriter;
    private HangmanDisplay mockHangmanDisplay;

    @BeforeEach
    void setUp() {
        mockWordBank = mock(WordBank.class);
        mockInputReader = mock(InputReader.class);
        mockOutputWriter = mock(OutputWriter.class);
        mockHangmanDisplay = mock(HangmanDisplay.class);

        game = new HangmanGame(mockWordBank, mockInputReader, mockOutputWriter);
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testGameScenarioLoss() throws IOException {
        when(mockInputReader.readLine())
            .thenReturn("1")  // Category
            .thenReturn("1")  // Difficulty
            .thenReturn("x")  // Incorrect letter
            .thenReturn("y")  // Incorrect letter
            .thenReturn("z")  // Incorrect letter
            .thenReturn("w")  // Incorrect letter
            .thenReturn("v")  // Incorrect letter
            .thenReturn("u"); // Incorrect letter

        when(mockWordBank.selectWordAndHint(any(), any()))
            .thenReturn(Map.entry("cat", "A small domesticated carnivorous mammal."));

        game.startGame();

        verify(mockOutputWriter).println(contains("Sorry, you've been hanged. The word was: cat"));
        verify(mockOutputWriter, never()).println(contains("Congratulations"));
    }
}
