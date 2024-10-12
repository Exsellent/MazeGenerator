package backend.academy.Maze;

import java.util.List;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameLauncher {
    private static final int PRIM_GENERATOR_OPTION = 1;
    private static final int KRUSKAL_GENERATOR_OPTION = 2;
    private static final int ALDOUS_BRODER_GENERATOR_OPTION = 3;

    private final Generator primGenerator;
    private final Generator kruskalGenerator;
    private final Generator aldousBroderGenerator;
    private final Solver solver;
    private final Renderer renderer;
    private final OutputWriter outputWriter;
    private final Scanner scanner;

    public GameLauncher() {
        this.primGenerator = new PrimGenerator();
        this.kruskalGenerator = new KruskalGenerator();
        this.aldousBroderGenerator = new AldousBroderMazeGenerator();
        this.solver = new AStarSolver();
        this.renderer = new ConsoleRenderer();
        this.outputWriter = new ConsoleOutputWriter();
        this.scanner = new Scanner(System.in);
    }

    public void launch() {
        outputWriter.println("Welcome to the Maze Generator and Solver!");

        int width = getIntInput("Enter the width of the maze: ");
        int height = getIntInput("Enter the height of the maze: ");

        Generator selectedGenerator = selectGenerator();

        Coordinate start = getCoordinateInput("Enter the coordinates of the starting point (x y): ", width, height);
        Coordinate end = getCoordinateInput("Enter the coordinates of the endpoint (x y): ", width, height);

        run(height, width, selectedGenerator, start, end);
    }

    private int getIntInput(String prompt) {
        outputWriter.print(prompt);
        while (!scanner.hasNextInt()) {
            outputWriter.print("Invalid input. Please enter an integer: ");
            scanner.next(); // Сбрасываем неправильный ввод
        }
        return scanner.nextInt();
    }

    private Generator selectGenerator() {
        outputWriter.println("Select the generation algorithm:");
        outputWriter.println(PRIM_GENERATOR_OPTION + " - Prim");
        outputWriter.println(KRUSKAL_GENERATOR_OPTION + " - Kruskal");
        outputWriter.println(ALDOUS_BRODER_GENERATOR_OPTION + " - Aldous-Broder");

        int choice;
        do {
            choice = getIntInput(
                    "Enter your choice (" + PRIM_GENERATOR_OPTION + "-" + ALDOUS_BRODER_GENERATOR_OPTION + "): ");
        } while (choice < PRIM_GENERATOR_OPTION || choice > ALDOUS_BRODER_GENERATOR_OPTION);

        switch (choice) {
        case PRIM_GENERATOR_OPTION:
            return primGenerator;
        case KRUSKAL_GENERATOR_OPTION:
            return kruskalGenerator;
        case ALDOUS_BRODER_GENERATOR_OPTION:
            return aldousBroderGenerator;
        default:
            return primGenerator; // Недостижимо
        }
    }

    private Coordinate getCoordinateInput(String prompt, int maxX, int maxY) {
        int x;
        int y;
        do {
            outputWriter.print(prompt);
            while (!scanner.hasNextInt()) {
                outputWriter.print("Invalid input. Please enter two integers: ");
                scanner.next(); // Сбрасываем неправильный ввод
            }
            x = scanner.nextInt();
            y = scanner.nextInt();
        } while (!isValidCoordinate(x, y, maxX, maxY)); // Повторяем, если координаты недействительны

        return new Coordinate(x, y);
    }

    private boolean isValidCoordinate(int x, int y, int maxX, int maxY) {
        if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
            outputWriter.println("Coordinates out of bounds. Please try again.");
            return false;
        }
        return true;
    }

    private void run(int height, int width, Generator generator, Coordinate start, Coordinate end) {
        outputWriter.println("Generating maze...");
        Maze maze = generator.generate(height, width);

        maze.setEntrance(start);
        maze.setExit(end);

        outputWriter.println("Rendering maze...");
        String mazeRendering = renderer.render(maze);
        outputWriter.println(mazeRendering);

        outputWriter.println("Solving maze...");
        List<Coordinate> path = solver.solve(maze, start, end);

        if (path.isEmpty()) {
            outputWriter.println("No path found.");
        } else {
            outputWriter.println("Path found. Rendering solution...");
            String solvedMazeRendering = renderer.render(maze, path);
            outputWriter.println(solvedMazeRendering);
        }
    }
}
