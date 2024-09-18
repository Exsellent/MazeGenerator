package backend.academy;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
class CategoryWords {
    private final Map<Difficulty, List<Word>> words;

    CategoryWords() {
        words = new EnumMap<>(Difficulty.class);
        for (Difficulty difficulty : Difficulty.values()) {
            words.put(difficulty, new ArrayList<>());
        }
    }

    void addWord(Word word) {
        words.get(word.getDifficulty()).add(word);
    }

    Word getRandomWord(Difficulty difficulty) {
        List<Word> wordList = words.get(difficulty);
        return wordList.get(new Random().nextInt(wordList.size()));
    }

    Word getRandomWord() {
        List<Difficulty> difficulties = new ArrayList<>(words.keySet());
        Difficulty randomDifficulty = difficulties.get(new Random().nextInt(difficulties.size()));
        return getRandomWord(randomDifficulty);
    }
}
