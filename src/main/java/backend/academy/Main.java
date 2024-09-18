package backend.academy;

public final class Main {
    private Main() {
        // Private constructor to prevent instantiation
    }

    public static void main(String[] args) {
        GameLauncher gameLauncher = new GameLauncher();
        gameLauncher.launch();
    }
}
