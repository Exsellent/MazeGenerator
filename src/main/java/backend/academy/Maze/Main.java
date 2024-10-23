package backend.academy.Maze;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Main {
    private Main() {

    }

    public static void main(String[] args) {
        log.info("Starting Maze Generator and Solver");

        GameLauncher gameLauncher = new GameLauncher();
        gameLauncher.startGame();

        log.info("Game finished");
    }
}
