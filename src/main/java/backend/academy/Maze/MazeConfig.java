package backend.academy.Maze;

public final class MazeConfig {
    private MazeConfig() {
        // Приватный конструктор для предотвращения создания экземпляров
    }

    public static final int[][] DIRECTIONS =
            // CHECKSTYLE:OFF
            {{-2, 0}, {2, 0}, {0, -2}, {0, 2}};
    // CHECKSTYLE:ON
    public static final int SPECIAL_CELL_RATIO = 40;
    public static final int SPECIAL_CELL_TYPES = 4;
}
