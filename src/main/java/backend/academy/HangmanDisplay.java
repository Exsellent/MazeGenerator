package backend.academy;

import java.util.Set;

public class HangmanDisplay {

    public void updateDisplay(String word, Set<Character> guessedLetters, OutputWriter outputWriter) {
        StringBuilder displayWord = new StringBuilder();
        for (char c : word.toCharArray()) {
            displayWord.append(guessedLetters.contains(c) ? c : "_");
        }
        outputWriter.println("Word: " + displayWord);
    }

    public void drawHangman(int attempts, OutputWriter outputWriter) {
        String[] hangmanParts = { "\n\n\n\n\n", "\n\n\n\n\n____", "|\n|\n|\n|\n|____", "_____\n|\n|\n|\n|\n|____",
                "_____\n|   |\n|\n|\n|\n|____", "_____\n|   |\n|   O\n|\n|\n|____",
                "_____\n|   |\n|   O\n|  /|\\\n|  / \\\n|____" };
        outputWriter.println(hangmanParts[attempts]);
    }
}
