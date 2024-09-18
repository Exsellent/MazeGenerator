package backend.academy;

import java.util.Set;

public class HangmanDisplay {
    private static final String[] HANGMAN_STAGES = {
            // The beginning of the construction
            """
                    \s
                    \s
                    \s
                    \s
                    \s
                    """, """
                    \s
                    \s
                    \s
                    \s
                    ____
                    """, """
                    |
                    |
                    |
                    |
                    |____
                    """, """
                    _____
                    |
                    |
                    |
                    |____
                    """, """
                    _____
                    |   |
                    |
                    |
                    |____
                    """, """
                    _____
                    |   |
                    |   O
                    |
                    |____
                    """, """
                    _____
                    |   |
                    |   O
                    |  /|\\
                    |  / \\
                    |____
                    """
            // The end of the construction
    };

    public void updateDisplay(String word, Set<Character> guessedLetters, OutputWriter outputWriter) {
        StringBuilder displayWord = new StringBuilder();
        for (char c : word.toCharArray()) {
            displayWord.append(guessedLetters.contains(c) ? c : "_").append(" ");
        }
        outputWriter.println("Word: " + displayWord.toString().trim());
    }

    public void drawHangman(int incorrectAttempts, OutputWriter outputWriter) {
        if (incorrectAttempts < 0 || incorrectAttempts >= HANGMAN_STAGES.length) {
            throw new IllegalArgumentException("Invalid number of incorrect attempts");
        }
        outputWriter.println(HANGMAN_STAGES[incorrectAttempts]);
    }
}
