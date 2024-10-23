package backend.academy.Maze.interfaces;

import backend.academy.Maze.algorithms.AldousBroderMazeGenerator;
import backend.academy.Maze.algorithms.KruskalGenerator;
import backend.academy.Maze.algorithms.PrimGenerator;

public final class MazeGeneratorFactory {
    private static final int PRIM_ALGORITHM = 1;
    private static final int KRUSKAL_ALGORITHM = 2;
    private static final int ALDOUS_BRODER_ALGORITHM = 3;

    private MazeGeneratorFactory() {

        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Generator createGenerator(int algorithmChoice) {
        return switch (algorithmChoice) {
        case PRIM_ALGORITHM -> new PrimGenerator();
        case KRUSKAL_ALGORITHM -> new KruskalGenerator();
        case ALDOUS_BRODER_ALGORITHM -> new AldousBroderMazeGenerator();
        default -> throw new IllegalArgumentException("Invalid generator algorithm choice: " + algorithmChoice);
        };
    }
}
