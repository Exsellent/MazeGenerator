package backend.academy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    @Timeout(value = 10, unit = TimeUnit.SECONDS)  // Ограничение времени теста
    void testGameScenario() throws IOException {
        when(mockInputReader.readLine())
            .thenReturn("1")  // Категория
            .thenReturn("1")  // Сложность
            .thenReturn("c")  // Первая буква
            .thenReturn("a")  // Вторая буква
            .thenReturn("t");  // Третья буква (слово отгадано)

        when(mockWordBank.selectWordAndHint(any(), any()))
            .thenReturn(Map.entry("cat", "A small domesticated carnivorous mammal."));

        game.startGame();

        verify(mockOutputWriter, atLeastOnce()).println(contains("Congratulations"));
        verify(mockOutputWriter, never()).println(contains("Sorry, you've been hanged"));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testGameScenarioLoss() throws IOException {
        when(mockInputReader.readLine())
            .thenReturn("1")  // Категория
            .thenReturn("1")  // Сложность
            .thenReturn("x")  // Неправильная буква
            .thenReturn("y")  // Неправильная буква
            .thenReturn("z")  // Неправильная буква
            .thenReturn("w")  // Неправильная буква
            .thenReturn("v")  // Неправильная буква
            .thenReturn("u");  // Неправильная буква

        when(mockWordBank.selectWordAndHint(any(), any()))
            .thenReturn(Map.entry("cat", "A small domesticated carnivorous mammal."));

        game.startGame();

        verify(mockOutputWriter, atLeastOnce()).println(contains("Sorry, you've been hanged"));
        verify(mockOutputWriter, never()).println(contains("Congratulations"));
    }
}
