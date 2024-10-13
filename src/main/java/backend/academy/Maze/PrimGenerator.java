package backend.academy.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Алгоритм Прима (Prim): Этот алгоритм выбирает случайную начальную точку и постепенно расширяет лабиринт, добавляя
 * соседние клетки с наименьшей стоимостью. Он создает лабиринты с длинными коридорами и случайными развилками.
 */
public class PrimGenerator implements Generator {
    private static final int STEP_SIZE = 2;
    private final Random random = new Random();

    @Override
    public Maze generate(int height, int width) {
        int gridHeight = height * 2 + 1;
        int gridWidth = width * 2 + 1;
        Cell[][] grid = new Cell[gridHeight][gridWidth];

        // Инициализация сетки
        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                grid[row][col] = new Cell(row, col, Cell.CellType.WALL);
            }
        }

        // Выбор случайной стартовой точки
        int startRow = random.nextInt(height) * STEP_SIZE + 1;
        int startCol = random.nextInt(width) * STEP_SIZE + 1;
        grid[startRow][startCol].setType(Cell.CellType.PASSAGE);

        // Создание списка фронтиров
        List<int[]> frontiers = new ArrayList<>();
        addFrontiers(grid, startRow, startCol, frontiers);

        // Основной цикл генерации лабиринта
        while (!frontiers.isEmpty()) {
            int[] frontier = frontiers.remove(random.nextInt(frontiers.size()));
            int row = frontier[0];
            int col = frontier[1];

            List<int[]> neighbors = getPassageNeighbors(grid, row, col);
            if (!neighbors.isEmpty()) {
                int[] neighbor = neighbors.get(random.nextInt(neighbors.size()));
                int wallRow = (row + neighbor[0]) / 2;
                int wallCol = (col + neighbor[1]) / 2;

                grid[row][col].setType(Cell.CellType.PASSAGE);
                grid[wallRow][wallCol].setType(Cell.CellType.PASSAGE);

                addFrontiers(grid, row, col, frontiers);
            }
        }

        // Добавление специальных поверхностей
        MazeConfig.addSpecialSurfaces(grid, random);

        // Убедимся, что начало и конец - проходы
        grid[1][1].setType(Cell.CellType.PASSAGE);
        grid[gridHeight - STEP_SIZE][gridWidth - STEP_SIZE].setType(Cell.CellType.PASSAGE);

        return new Maze(gridHeight, gridWidth, grid);
    }

    /**
     * Добавляет соседние клетки к списку фронтиров, если они могут быть потенциальными проходами.
     *
     * @param grid
     *            Сетка клеток лабиринта
     * @param row
     *            Текущая строка клетки
     * @param col
     *            Текущий столбец клетки
     * @param frontiers
     *            Список фронтиров
     */
    @SuppressWarnings("checkstyle:NoWhitespaceAfter")
    private void addFrontiers(Cell[][] grid, int row, int col, List<int[]> frontiers) {
        int[][] directions = { { -STEP_SIZE, 0 }, { STEP_SIZE, 0 }, { 0, -STEP_SIZE }, { 0, STEP_SIZE } };
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValidCell(grid, newRow, newCol) && grid[newRow][newCol].getType() == Cell.CellType.WALL) {
                frontiers.add(new int[] {newRow, newCol});
            }
        }
    }

    /**
     * Возвращает список соседей, которые являются проходами, для данной клетки.
     *
     * @param grid
     *            Сетка клеток лабиринта
     * @param row
     *            Текущая строка клетки
     * @param col
     *            Текущий столбец клетки
     *
     * @return Список соседних клеток, являющихся проходами
     */
    @SuppressWarnings("checkstyle:NoWhitespaceAfter")
    private List<int[]> getPassageNeighbors(Cell[][] grid, int row, int col) {
        List<int[]> neighbors = new ArrayList<>();
        int[][] directions = { { -STEP_SIZE, 0 }, { STEP_SIZE, 0 }, { 0, -STEP_SIZE }, { 0, STEP_SIZE } };
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValidCell(grid, newRow, newCol) && grid[newRow][newCol].getType() == Cell.CellType.PASSAGE) {
                neighbors.add(new int[] { newRow, newCol });
            }
        }
        return neighbors;
    }

    /**
     * Проверяет, является ли данная клетка допустимой внутри границ сетки.
     *
     * @param grid
     *            Сетка клеток лабиринта
     * @param row
     *            Проверяемая строка клетки
     * @param col
     *            Проверяемый столбец клетки
     *
     * @return true, если клетка находится в пределах допустимых координат, иначе false
     */
    private boolean isValidCell(Cell[][] grid, int row, int col) {
        return row > 0 && col > 0 && row < grid.length - 1 && col < grid[0].length - 1;
    }
}
