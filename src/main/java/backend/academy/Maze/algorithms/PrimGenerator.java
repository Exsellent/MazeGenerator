package backend.academy.Maze.algorithms;

import backend.academy.Maze.Maze;
import backend.academy.Maze.config.MazeConfig;
import backend.academy.Maze.interfaces.Generator;
import backend.academy.Maze.utils.Cell;
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
        Cell[][] grid = initializeGrid(gridHeight, gridWidth);

        int startRow = random.nextInt(height) * STEP_SIZE + 1;
        int startCol = random.nextInt(width) * STEP_SIZE + 1;
        grid[startRow][startCol].setType(Cell.CellType.PASSAGE);

        List<int[]> frontiers = new ArrayList<>();
        addFrontiers(grid, startRow, startCol, frontiers);

        generateMaze(grid, frontiers);

        MazeConfig.addSpecialSurfaces(grid, random);

        grid[1][1].setType(Cell.CellType.PASSAGE);
        grid[gridHeight - STEP_SIZE][gridWidth - STEP_SIZE].setType(Cell.CellType.PASSAGE);

        return new Maze(gridHeight, gridWidth, grid);
    }

    private Cell[][] initializeGrid(int gridHeight, int gridWidth) {
        Cell[][] grid = new Cell[gridHeight][gridWidth];
        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                grid[row][col] = new Cell(row, col, Cell.CellType.WALL);
            }
        }
        return grid;
    }

    private void generateMaze(Cell[][] grid, List<int[]> frontiers) {
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
    }

    @SuppressWarnings("checkstyle:NoWhitespaceAfter")

    private void addFrontiers(Cell[][] grid, int row, int col, List<int[]> frontiers) {
        int[][] directions = { { -STEP_SIZE, 0 }, { STEP_SIZE, 0 }, { 0, -STEP_SIZE }, { 0, STEP_SIZE } };
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValidCell(grid, newRow, newCol) && grid[newRow][newCol].getType() == Cell.CellType.WALL) {
                frontiers.add(new int[] { newRow, newCol });
            }
        }
    }

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

    private boolean isValidCell(Cell[][] grid, int row, int col) {
        return row > 0 && col > 0 && row < grid.length - 1 && col < grid[0].length - 1;
    }
}
