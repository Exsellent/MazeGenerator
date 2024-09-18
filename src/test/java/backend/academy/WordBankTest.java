package backend.academy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordBankTest {

    @Test
    void testSelectWord() {
        WordBank wordBank = new WordBank();
        Word word = wordBank.selectWord(Category.ANIMALS, Difficulty.EASY);
        assertNotNull(word.getWord()); // Corrected usage
        assertTrue(word.getWord().length() > 0); // Corrected usage
    }

    @Test
    void testSelectWordDifferentCategories() {
        WordBank wordBank = new WordBank();
        Word animalWord = wordBank.selectWord(Category.ANIMALS, Difficulty.EASY);
        Word fruitWord = wordBank.selectWord(Category.FRUITS, Difficulty.EASY);

        assertNotNull(animalWord.getWord()); // Corrected usage
        assertNotNull(fruitWord.getWord()); // Corrected usage
        assertNotEquals(animalWord.getWord(), fruitWord.getWord()); // Corrected usage
    }

    @Test
    void testSelectWordDifferentDifficulties() {
        WordBank wordBank = new WordBank();
        Word easyWord = wordBank.selectWord(Category.ANIMALS, Difficulty.EASY);
        Word hardWord = wordBank.selectWord(Category.ANIMALS, Difficulty.HARD);

        assertNotNull(easyWord.getWord()); // Corrected usage
        assertNotNull(hardWord.getWord()); // Corrected usage
        assertNotEquals(easyWord.getWord(), hardWord.getWord()); // Corrected usage
    }
}
