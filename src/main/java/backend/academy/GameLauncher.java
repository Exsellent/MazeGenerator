package backend.academy;

public class GameLauncher {
    public void launch() {
        InputReader inputReader = new ConsoleInputReader();
        OutputWriter outputWriter = new ConsoleOutputWriter();
        WordBank wordBank = new WordBank();

        HangmanGame game = new HangmanGame(wordBank, inputReader, outputWriter);
        game.startGame();
    }
}
