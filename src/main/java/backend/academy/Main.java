package backend.academy;

public final class Main {
    private Main() {
        // Private constructor to prevent instantiation
    }

    public static void main(String[] args) {
        InputReader inputReader = new ConsoleInputReader();
        OutputWriter outputWriter = new ConsoleOutputWriter();
        WordBank wordBank = new WordBank();

        HangmanGame game = new HangmanGame(wordBank, inputReader, outputWriter);
        game.startGame();
    }
}
