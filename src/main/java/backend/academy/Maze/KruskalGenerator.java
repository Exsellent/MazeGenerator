package backend.academy.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Maze generator использует Kruskal's алгоритм.
 */
public class KruskalGenerator implements Generator {
    private final Random random = new Random();

    @Override
    public Maze generate(int height, int width) {
        // Валидация входных данных
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Height and width must be positive integers.");
        }

        int gridHeight = height * 2 + 1;
        int gridWidth = width * 2 + 1;
        Cell[][] grid = new Cell[gridHeight][gridWidth];
        List<Edge> edges = new ArrayList<>();
        DisjointSet disjointSet = new DisjointSet(height * width);

        // Инициализация сетки и ребер
        initializeGrid(grid, gridHeight, gridWidth, edges);

        // Перемешиваем рёбра случайным образом
        Collections.shuffle(edges, random);

        // Алгоритм Краскала: объединение клеток
        for (Edge edge : edges) {
            int index1 = (edge.row1 / 2) * width + (edge.col1 / 2);
            int index2 = (edge.row2 / 2) * width + (edge.col2 / 2);

            if (disjointSet.find(index1) != disjointSet.find(index2)) {
                disjointSet.union(index1, index2);
                int middleRow = (edge.row1 + edge.row2) / 2;
                int middleCol = (edge.col1 + edge.col2) / 2;
                grid[middleRow][middleCol].setType(Cell.CellType.PASSAGE);
            }
        }

        // Обеспечиваем проходимость начальной и конечной точек
        grid[1][1].setType(Cell.CellType.PASSAGE);
        grid[gridHeight - 2][gridWidth - 2].setType(Cell.CellType.PASSAGE);

        // Добавление специальных поверхностей
        addSpecialSurfaces(grid);

        return new Maze(gridHeight, gridWidth, grid);
    }

    /**
     * Инициализирует сетку и создает стены и проходы.
     *
     * @param grid
     *            The maze grid
     * @param gridHeight
     *            The grid height
     * @param gridWidth
     *            The grid width
     * @param edges
     *            List of edges for Kruskal's algorithm
     */
    private void initializeGrid(Cell[][] grid, int gridHeight, int gridWidth, List<Edge> edges) {
        // Инициализация сетки: все клетки - стены
        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                grid[row][col] = new Cell(row, col, Cell.CellType.WALL);
            }
        }

        // Создаем проходы и добавляем рёбра
        for (int row = 1; row < gridHeight; row += 2) {
            for (int col = 1; col < gridWidth; col += 2) {
                grid[row][col].setType(Cell.CellType.PASSAGE);
                if (row < gridHeight - 2) {
                    edges.add(new Edge(row, col, row + 2, col));
                }
                if (col < gridWidth - 2) {
                    edges.add(new Edge(row, col, row, col + 2));
                }
            }
        }
    }

    /**
     * Добавляет специальные поверхности к сетке лабиринта.
     *
     * @param grid
     *            The maze grid
     */
    private void addSpecialSurfaces(Cell[][] grid) {
        int totalCells = grid.length * grid[0].length;
        int specialCells = totalCells / MazeConfig.SPECIAL_CELL_RATIO;

        for (int i = 0; i < specialCells; i++) {
            int row = random.nextInt(grid.length);
            int col = random.nextInt(grid[0].length);

            if (grid[row][col].getType() == Cell.CellType.PASSAGE) {
                int cellType = random.nextInt(MazeConfig.SPECIAL_CELL_TYPES);
                switch (cellType) {
                case 0 -> grid[row][col].setType(Cell.CellType.SWAMP);
                case 1 -> grid[row][col].setType(Cell.CellType.SAND);
                case 2 -> grid[row][col].setType(Cell.CellType.COIN);
                default -> {
                    // оставляем как PASSAGE
                }
                }
            }
        }
    }

    /**
     * Представляет собой преимущество в Kruskal's алгоритме.
     */
    private static class Edge {
        private final int row1;
        private final int col1;
        private final int row2;
        private final int col2;

        Edge(int row1, int col1, int row2, int col2) {
            this.row1 = row1;
            this.col1 = col1;
            this.row2 = row2;
            this.col2 = col2;
        }
    }
}
