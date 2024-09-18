package backend.academy;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.mockito.Mockito.*;

class HangmanGameTest {

    private HangmanGame game;
    private WordBank mockWordBank;
    private InputReader mockInputReader;
    private OutputWriter mockOutputWriter;

    @BeforeEach
    void setUp() {
        mockWordBank = mock(WordBank.class);
        mockInputReader = mock(InputReader.class);
        mockOutputWriter = mock(OutputWriter.class);

        game = new HangmanGame(mockWordBank, mockInputReader, mockOutputWriter);
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testGameScenarioLoss() throws IOException {
        // Настройка поведения моков
        when(mockInputReader.readLine())
            .thenReturn("1")  // Category
            .thenReturn("1")  // Difficulty
            .thenReturn("x")  // Incorrect letter
            .thenReturn("y")  // Incorrect letter
            .thenReturn("z")  // Incorrect letter
            .thenReturn("w")  // Incorrect letter
            .thenReturn("v")  // Incorrect letter
            .thenReturn("u"); // Incorrect letter

        // Настройка возвращаемого значения метода selectWord с правильными параметрами
        when(mockWordBank.selectWord(any(Category.class), any(Difficulty.class)))
            .thenReturn(new Word("cat", "A small domesticated carnivorous mammal.", Difficulty.EASY));

        // Запуск игры
        game.startGame();

        // Проверка вывода
        verify(mockOutputWriter).println(contains("Sorry, you've been hanged. The word was: cat"));
        verify(mockOutputWriter, never()).println(contains("Congratulations"));
    }
}
