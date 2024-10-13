package backend.academy.Maze;

import java.util.Random;

public final class MazeConfig {
    private MazeConfig() {
        // Приватный конструктор для предотвращения создания экземпляров
    }

    @SuppressWarnings("all") // Ложно-положительные срабатывания Checkstyle
    public static final int[][] DIRECTIONS = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public static final int SPECIAL_CELL_RATIO = 40; // Соотношение для распределения специальных клеток
    public static final int SPECIAL_CELL_TYPES = 4; // Количество типов специальных поверхностей

    // Константы, представляющие типы поверхностей
    public static final int SWAMP_TYPE = 0;
    public static final int SAND_TYPE = 1;
    public static final int COIN_TYPE = 2;
    public static final int PASSAGE_TYPE = 3;

    // Константы для расчета размеров сетки
    public static final int GRID_MULTIPLIER = 2;
    public static final int GRID_OFFSET = 1;

    // Инициализация сетки лабиринта: установка всех клеток как стены
    public static void initializeGrid(Cell[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = new Cell(row, col, Cell.CellType.WALL);
            }
        }
    }

    // Добавление специальных поверхностей в лабиринт (болота, песок, монеты)
    public static void addSpecialSurfaces(Cell[][] grid, Random random) {
        int totalCells = grid.length * grid[0].length;
        int specialCells = totalCells / SPECIAL_CELL_RATIO;

        for (int i = 0; i < specialCells; i++) {
            int row = random.nextInt(grid.length);
            int col = random.nextInt(grid[0].length);

            // Проверка, что клетка является проходом перед добавлением специальной поверхности
            if (grid[row][col].getType() == Cell.CellType.PASSAGE) {
                int cellType = random.nextInt(SPECIAL_CELL_TYPES);
                switch (cellType) {
                case SWAMP_TYPE -> grid[row][col].setType(Cell.CellType.SWAMP); // Устанавливаем тип "болото"
                case SAND_TYPE -> grid[row][col].setType(Cell.CellType.SAND); // Устанавливаем тип "песок"
                case COIN_TYPE -> grid[row][col].setType(Cell.CellType.COIN); // Устанавливаем тип "монета"
                case PASSAGE_TYPE -> {
                    // Оставляем как проход
                }
                default -> {
                    // Неожиданный тип клетки, оставляем как есть
                }
                }
            }
        }
    }
}
