package backend.academy.Maze;

import backend.academy.Maze.algorithms.AldousBroderMazeGenerator;
import backend.academy.Maze.algorithms.KruskalGenerator;
import backend.academy.Maze.algorithms.PrimGenerator;
import backend.academy.Maze.interfaces.Generator;
import backend.academy.Maze.interfaces.Solver;
import backend.academy.Maze.solvers.AStarSolver;
import backend.academy.Maze.solvers.BFSSolver;
import backend.academy.Maze.utils.Coordinate;
import java.util.List;
import java.util.Scanner;

public class GameLauncher {
    private static final int PRIM_ALGORITHM = 1;
    private static final int KRUSKAL_ALGORITHM = 2;
    private static final int ALDOUS_BRODER_ALGORITHM = 3;
    private static final int MIN_GENERATOR_CHOICE = 1;
    private static final int MAX_GENERATOR_CHOICE = 3;

    private static final int ASTAR_ALGORITHM = 1;
    private static final int BFS_ALGORITHM = 2;
    private static final int MIN_SOLVER_CHOICE = 1;
    private static final int MAX_SOLVER_CHOICE = 2;

    private static final String PROMPT_CHOICE_START = "Enter your choice (";
    private static final String PROMPT_CHOICE_END = "): ";
    private static final String ERROR_INVALID_CHOICE = "Invalid choice: ";

    private final ConsoleOutputWriter outputWriter;
    private final Scanner scanner;

    public GameLauncher() {
        this.outputWriter = new ConsoleOutputWriter();
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        outputWriter.writeLine("Welcome to the Maze Generator and Solver!");
        outputWriter.writeLine("Enter the width of the maze: ");
        int width = scanner.nextInt();
        outputWriter.writeLine("Enter the height of the maze: ");
        int height = scanner.nextInt();

        Generator generator = selectGenerationAlgorithm();
        outputWriter.writeLine("Enter the coordinates of the starting point (x y): ");
        int startX = scanner.nextInt();
        int startY = scanner.nextInt();
        outputWriter.writeLine("Enter the coordinates of the endpoint (x y): ");
        int endX = scanner.nextInt();
        int endY = scanner.nextInt();

        outputWriter.writeLine("Generating maze...");
        Maze maze = generator.generate(height, width);
        maze.setEntrance(new Coordinate(startX, startY));
        maze.setExit(new Coordinate(endX, endY));

        outputWriter.writeLine("Rendering maze...");
        ConsoleRenderer renderer = new ConsoleRenderer();
        String renderedMaze = renderer.render(maze);
        outputWriter.writeLine(renderedMaze);

        Solver solver = selectSolverAlgorithm();
        outputWriter.writeLine("Solving maze...");

        List<Coordinate> path = solver.solve(maze, new Coordinate(startX, startY), new Coordinate(endX, endY));

        if (!path.isEmpty()) {
            outputWriter.writeLine("Path found. Rendering solution...");
            String solvedMaze = renderer.render(maze, path);
            outputWriter.writeLine(solvedMaze);
        } else {
            outputWriter.writeLine("No path found.");
        }

        outputWriter.writeLine("Game finished");
    }

    private Generator selectGenerationAlgorithm() {
        outputWriter.writeLine("Select the generation algorithm:");
        outputWriter.writeLine(PRIM_ALGORITHM + " - Prim");
        outputWriter.writeLine(KRUSKAL_ALGORITHM + " - Kruskal");
        outputWriter.writeLine(ALDOUS_BRODER_ALGORITHM + " - Aldous-Broder");
        outputWriter
                .writeLine(PROMPT_CHOICE_START + MIN_GENERATOR_CHOICE + "-" + MAX_GENERATOR_CHOICE + PROMPT_CHOICE_END);
        int choice = scanner.nextInt();

        return switch (choice) {
        case PRIM_ALGORITHM -> new PrimGenerator();
        case KRUSKAL_ALGORITHM -> new KruskalGenerator();
        case ALDOUS_BRODER_ALGORITHM -> new AldousBroderMazeGenerator();
        default -> throw new IllegalArgumentException(ERROR_INVALID_CHOICE + choice);
        };
    }

    private Solver selectSolverAlgorithm() {
        outputWriter.writeLine("Select the solving algorithm:");
        outputWriter.writeLine(ASTAR_ALGORITHM + " - A* (A-Star)");
        outputWriter.writeLine(BFS_ALGORITHM + " - BFS (Breadth-First Search)");
        outputWriter.writeLine(PROMPT_CHOICE_START + MIN_SOLVER_CHOICE + "-" + MAX_SOLVER_CHOICE + PROMPT_CHOICE_END);
        int choice = scanner.nextInt();

        return switch (choice) {
        case ASTAR_ALGORITHM -> new AStarSolver();
        case BFS_ALGORITHM -> new BFSSolver();
        default -> throw new IllegalArgumentException(ERROR_INVALID_CHOICE + choice);
        };
    }
}
