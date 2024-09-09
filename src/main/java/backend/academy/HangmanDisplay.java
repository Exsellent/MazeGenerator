package backend.academy;

import java.util.Set;

public class HangmanDisplay {

    private static final String NEWLINE = "\n";
    private static final String VERTICAL_LINE = "|";
    private static final String FLOOR = "____";
    private static final String ROOF = "_____";
    private static final String EMPTY = "";
    private static final String HEAD = "O";
    private static final String BODY = "|";
    private static final String LEFT_ARM = "/";
    private static final String RIGHT_ARM = "\\";
    private static final String LEFT_LEG = "/";
    private static final String RIGHT_LEG = "\\";
    private static final String PADDING = "   ";

    private static final String[] HANGMAN_STAGES = {

            String.join(NEWLINE, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
            String.join(NEWLINE, EMPTY, EMPTY, EMPTY, EMPTY, FLOOR),
            String.join(NEWLINE, VERTICAL_LINE, VERTICAL_LINE, VERTICAL_LINE, VERTICAL_LINE, VERTICAL_LINE + FLOOR),
            String.join(NEWLINE, ROOF, VERTICAL_LINE, VERTICAL_LINE, VERTICAL_LINE, VERTICAL_LINE,
                    VERTICAL_LINE + FLOOR),
            String.join(NEWLINE, ROOF, VERTICAL_LINE + PADDING + VERTICAL_LINE, VERTICAL_LINE, VERTICAL_LINE,
                    VERTICAL_LINE, VERTICAL_LINE + FLOOR),
            String.join(NEWLINE, ROOF, VERTICAL_LINE + PADDING + VERTICAL_LINE, VERTICAL_LINE + PADDING + HEAD,
                    VERTICAL_LINE, VERTICAL_LINE, VERTICAL_LINE + FLOOR),
            String.join(NEWLINE, ROOF, VERTICAL_LINE + PADDING + VERTICAL_LINE, VERTICAL_LINE + PADDING + HEAD,
                    VERTICAL_LINE + "  " + LEFT_ARM + BODY + RIGHT_ARM,
                    VERTICAL_LINE + "  " + LEFT_LEG + " " + RIGHT_LEG, VERTICAL_LINE + FLOOR)

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
