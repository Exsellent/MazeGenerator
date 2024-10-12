package backend.academy.Maze;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Main {
    private Main() {
        // Приватный конструктор для предотвращения создания экземпляров
    }

    public static void main(String[] args) {
        log.info("Starting Maze Generator and Solver");

        GameLauncher gameLauncher = new GameLauncher();
        gameLauncher.launch();

        log.info("Game finished");
    }
}
