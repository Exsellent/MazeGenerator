package backend.academy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordBankTest {

    @Test
    void testSelectWord() {
        WordBank wordBank = new WordBank();
        var wordAndHint = wordBank.selectWordAndHint(Category.ANIMALS, Difficulty.EASY);
        assertNotNull(wordAndHint.getKey());
        assertTrue(wordAndHint.getKey().length() > 0);
    }

    @Test
    void testSelectWordDifferentCategories() {
        WordBank wordBank = new WordBank();
        var animalWord = wordBank.selectWordAndHint(Category.ANIMALS, Difficulty.EASY);
        var fruitWord = wordBank.selectWordAndHint(Category.FRUITS, Difficulty.EASY);

        assertNotNull(animalWord.getKey());
        assertNotNull(fruitWord.getKey());
        assertNotEquals(animalWord.getKey(), fruitWord.getKey());
    }

    @Test
    void testSelectWordDifferentDifficulties() {
        WordBank wordBank = new WordBank();
        var easyWord = wordBank.selectWordAndHint(Category.ANIMALS, Difficulty.EASY);
        var hardWord = wordBank.selectWordAndHint(Category.ANIMALS, Difficulty.HARD);

        assertNotNull(easyWord.getKey());
        assertNotNull(hardWord.getKey());
        assertNotEquals(easyWord.getKey(), hardWord.getKey());
    }
}
