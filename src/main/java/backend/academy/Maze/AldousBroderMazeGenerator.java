package backend.academy.Maze;

import java.util.Random;

/**
 * Класс AldousBroderMazeGenerator реализует алгоритм генерации лабиринтов на основе алгоритма Алдуса-Бродера. Этот
 * алгоритм случайным образом перемещается по сетке, пока не посетит все клетки, соединяя непосещённые клетки с
 * проходами.
 */
public class AldousBroderMazeGenerator implements Generator {
    private final Random random = new Random(); // Генератор случайных чисел

    @Override
    public Maze generate(int height, int width) {
        int gridHeight = height * 2 + 1; // Высота сетки с учётом стен
        int gridWidth = width * 2 + 1; // Ширина сетки с учётом стен
        Cell[][] cells = new Cell[gridHeight][gridWidth];

        // Инициализация сетки как стен
        MazeConfig.initializeGrid(cells);

        // Выбор случайной стартовой ячейки, которая станет проходом
        int row = 1 + 2 * random.nextInt(height);
        int col = 1 + 2 * random.nextInt(width);
        cells[row][col].setType(Cell.CellType.PASSAGE);

        int visitedCells = 1; // Количество посещённых клеток
        int totalCells = height * width; // Общее количество клеток

        // Основной цикл генерации лабиринта
        while (visitedCells < totalCells) {
            // Выбираем случайное направление из доступных
            int[] direction = MazeConfig.DIRECTIONS[random.nextInt(MazeConfig.DIRECTIONS.length)];
            int newRow = row + 2 * direction[0];
            int newCol = col + 2 * direction[1];

            // Проверяем, является ли выбранная ячейка допустимой
            if (isValidCell(newRow, newCol, gridHeight, gridWidth)) {
                // Если выбранная ячейка стена, то делаем её и промежуточную ячейку проходом
                if (cells[newRow][newCol].getType() == Cell.CellType.WALL) {
                    cells[(row + newRow) / 2][(col + newCol) / 2].setType(Cell.CellType.PASSAGE);
                    cells[newRow][newCol].setType(Cell.CellType.PASSAGE); // Новая ячейка
                    visitedCells++; // Увеличиваем счётчик посещённых клеток
                }
                // Переход на новую ячейку
                row = newRow;
                col = newCol;
            }
        }

        // Добавление специальных поверхностей (болота, песка, монет)
        MazeConfig.addSpecialSurfaces(cells, random);

        return new Maze(gridHeight, gridWidth, cells); // Возвращаем сгенерированный лабиринт
    }

    /**
     * Проверяет, что координаты находятся в допустимых пределах.
     *
     * @param row
     *            строка ячейки
     * @param col
     *            столбец ячейки
     * @param height
     *            высота сетки
     * @param width
     *            ширина сетки
     *
     * @return true, если координаты в пределах допустимого диапазона
     */
    private boolean isValidCell(int row, int col, int height, int width) {
        return row > 0 && col > 0 && row < height - 1 && col < width - 1;
    }
}
