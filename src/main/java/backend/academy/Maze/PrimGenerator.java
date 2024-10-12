package backend.academy.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimGenerator implements Generator {
    private final Random random = new Random();

    @Override
    public Maze generate(int height, int width) {
        int gridHeight = height * 2 + 1;
        int gridWidth = width * 2 + 1;
        Cell[][] grid = new Cell[gridHeight][gridWidth];

        // Инициализация сетки
        initializeGrid(grid);

        // Выбор случайной стартовой точки
        int startRow = random.nextInt(height) * 2 + 1;
        int startCol = random.nextInt(width) * 2 + 1;
        grid[startRow][startCol].setType(Cell.CellType.PASSAGE);

        // Создание списка фронтиров
        List<int[]> frontiers = new ArrayList<>();
        addFrontiers(grid, startRow, startCol, frontiers);

        // Основной цикл генерации лабиринта
        while (!frontiers.isEmpty()) {
            int[] frontier = frontiers.remove(random.nextInt(frontiers.size()));
            int row = frontier[0];
            int col = frontier[1];

            // Получение соседей-проходов
            List<int[]> neighbors = getPassageNeighbors(grid, row, col);
            if (!neighbors.isEmpty()) {
                int[] neighbor = neighbors.get(random.nextInt(neighbors.size()));
                int wallRow = (row + neighbor[0]) / 2;
                int wallCol = (col + neighbor[1]) / 2;

                // Превращение стены в проход
                grid[row][col].setType(Cell.CellType.PASSAGE);
                grid[wallRow][wallCol].setType(Cell.CellType.PASSAGE);

                // Добавление новых фронтиров
                addFrontiers(grid, row, col, frontiers);
            }
        }

        // Добавление специальных поверхностей
        addSpecialSurfaces(grid);
        return new Maze(gridHeight, gridWidth, grid);
    }

    // Метод инициализации сетки
    private void initializeGrid(Cell[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = new Cell(row, col, Cell.CellType.WALL);
            }
        }
    }

    @SuppressWarnings("all") // Ложно-положительные срабатывания Checkstyle
    // Метод добавления фронтиров
    private void addFrontiers(Cell[][] grid, int row, int col, List<int[]> frontiers) {
        for (int[] dir : MazeConfig.DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValidCell(grid, newRow, newCol) && grid[newRow][newCol].getType() == Cell.CellType.WALL) {
                frontiers.add(new int[] { newRow, newCol });
            }
        }
    }

    @SuppressWarnings("all") // Ложно-положительные срабатывания Checkstyle
    // Метод получения соседей-проходов
    private List<int[]> getPassageNeighbors(Cell[][] grid, int row, int col) {
        List<int[]> neighbors = new ArrayList<>();
        for (int[] dir : MazeConfig.DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValidCell(grid, newRow, newCol) && grid[newRow][newCol].getType() == Cell.CellType.PASSAGE) {
                neighbors.add(new int[] { newRow, newCol });
            }
        }
        return neighbors;
    }

    // Метод проверки валидности ячейки
    private boolean isValidCell(Cell[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    // Метод добавления специальных поверхностей
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
}
