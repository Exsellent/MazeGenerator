package backend.academy;

import lombok.ToString;

@ToString
class Word {
    private final String word;
    private final String hint;
    private final Difficulty difficulty;

    // Constructor
    Word(String word, String hint, Difficulty difficulty) {
        this.word = word;
        this.hint = hint;
        this.difficulty = difficulty;
    }

    String getWord() { // Removed 'public'
        return word;
    }

    String getHint() { // Removed 'public'
        return hint;
    }

    Difficulty getDifficulty() { // Removed 'public'
        return difficulty;
    }
}
