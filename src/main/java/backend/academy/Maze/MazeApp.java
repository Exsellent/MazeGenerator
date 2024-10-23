package backend.academy.Maze;

import backend.academy.Maze.interfaces.MazeGenerator;
import backend.academy.Maze.utils.Coordinate;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MazeApp {
    private final MazeGenerator generator;
    private final ConsoleRenderer renderer;

    public MazeApp(MazeGenerator generator) {
        this.generator = generator;
        this.renderer = new ConsoleRenderer();
    }

    public void run() {

        try (Scanner scanner = new Scanner(System.in)) {

            int width = promptForDimension(scanner, "Enter the width of the maze:");
            int height = promptForDimension(scanner, "Enter the height of the maze:");
            Maze maze = generator.generate(width, height);

            maze.setEntrance(new Coordinate(0, 0));
            maze.setExit(new Coordinate(width - 1, height - 1));

            log.info("Generated maze:");
            log.info(renderer.render(maze));

        } catch (Exception e) {

            log.error("An error occurred during the maze generation.", e);
        } // Scanner закрывается автоматически при выходе из блока try
    }

    private int promptForDimension(Scanner scanner, String message) {
        log.info(message);
        while (!scanner.hasNextInt()) {
            log.warn("Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
