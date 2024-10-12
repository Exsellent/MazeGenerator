package backend.academy.Maze;

import java.util.Random;

public class AldousBroderMazeGenerator implements Generator {
    private final Random random = new Random();

    @Override
    public Maze generate(int height, int width) {
        // Рассчитываем фактические размеры сетки лабиринта (с учётом стен).
        int gridHeight = height * 2 + 1;
        int gridWidth = width * 2 + 1;
        Cell[][] cells = new Cell[gridHeight][gridWidth];

        // Инициализируем сетку: все клетки становятся стенами.
        initializeGrid(cells);

        // Начинаем с произвольной клетки внутри лабиринта.
        int row = random.nextInt(height) * 2 + 1;
        int col = random.nextInt(width) * 2 + 1;
        cells[row][col].setType(Cell.CellType.PASSAGE); // Устанавливаем эту клетку как проход.

        int visitedCells = 1; // Считаем уже посещённую клетку.
        int totalCells = height * width; // Общее количество клеток в лабиринте.

        // Основной цикл алгоритма: пока не все клетки посещены.
        while (visitedCells < totalCells) {
            // Выбираем случайного соседа текущей клетки.
            int[] nextCell = getRandomNeighbor(row, col, height, width);
            int newRow = nextCell[0];
            int newCol = nextCell[1];

            // Если выбранный сосед ещё не посещён (является стеной), создаём проход.
            if (cells[newRow][newCol].getType() == Cell.CellType.WALL) {
                // Пробиваем стену между текущей и новой клетками.
                cells[(row + newRow) / 2][(col + newCol) / 2].setType(Cell.CellType.PASSAGE);
                cells[newRow][newCol].setType(Cell.CellType.PASSAGE);
                visitedCells++; // Увеличиваем счётчик посещённых клеток.
            }

            // Переходим к новой клетке.
            row = newRow;
            col = newCol;
        }

        // Добавляем специальные поверхности (болота, пески, монеты).
        addSpecialSurfaces(cells);

        // Возвращаем сгенерированный лабиринт.
        return new Maze(gridHeight, gridWidth, cells);
    }

    // Метод для инициализации сетки: все клетки сначала заполняются стенами.
    private void initializeGrid(Cell[][] cells) {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                cells[row][col] = new Cell(row, col, Cell.CellType.WALL);
            }
        }
    }

    // Метод для выбора случайного соседа текущей клетки, с учётом границ.
    private int[] getRandomNeighbor(int row, int col, int height, int width) {
        int[] direction = MazeConfig.DIRECTIONS[random.nextInt(MazeConfig.DIRECTIONS.length)];
        int newRow = row + direction[0];
        int newCol = col + direction[1];

        // Ограничиваем новые координаты в пределах лабиринта.
        newRow = Math.max(1, Math.min(newRow, height * 2 - 1));
        newCol = Math.max(1, Math.min(newCol, width * 2 - 1));

        // Возвращаем новые координаты соседа.
        // CHECKSTYLE:OFF
        return new int[] {newRow, newCol};
        // CHECKSTYLE:ON
    }

    // Метод для добавления специальных клеток на лабиринт (например, болота, пески, монеты).
    private void addSpecialSurfaces(Cell[][] grid) {
        int totalCells = grid.length * grid[0].length; // Общее количество клеток.
        int specialCells = totalCells / MazeConfig.SPECIAL_CELL_RATIO; // Количество специальных клеток.

        // Случайным образом выбираем клетки для специальных поверхностей.
        for (int i = 0; i < specialCells; i++) {
            int row = random.nextInt(grid.length);
            int col = random.nextInt(grid[0].length);

            // Добавляем специальную поверхность, если это клетка-проход.
            if (grid[row][col].getType() == Cell.CellType.PASSAGE) {
                int cellType = random.nextInt(MazeConfig.SPECIAL_CELL_TYPES);
                switch (cellType) {
                case 0 -> grid[row][col].setType(Cell.CellType.SWAMP); // Болото
                case 1 -> grid[row][col].setType(Cell.CellType.SAND); // Песок
                case 2 -> grid[row][col].setType(Cell.CellType.COIN); // Монета
                default -> {
                    // Оставляем клетку как проход.
                }
                }
            }
        }
    }
}
