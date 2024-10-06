package backend.academy.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;

@Getter public class Maze {
    private final int height; // Высота лабиринта
    private final int width;  // Ширина лабиринта
    private final Cell[][] grid; // Двумерный массив ячеек, представляющий лабиринт
    private final Random random = new Random(); // Генератор случайных чисел для выбора соседей

    // Конструктор класса Maze
    public Maze(int height, int width, Cell[][] grid) {
        this.height = height; // Устанавливаем высоту лабиринта
        this.width = width;   // Устанавливаем ширину лабиринта
        // Если переданная сетка ячеек (grid) равна null, создаем новую сетку
        this.grid = grid == null ? new Cell[height][width] : grid;

        // Если сетка не была передана, инициализируем каждую ячейку как стену
        if (grid == null) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    this.grid[row][col] = new Cell(row, col, Cell.CellType.WALL);
                }
            }
        }
    }

    // Метод для получения ячейки по координатам (row, col)
    public Cell getCell(int row, int col) {
        // Проверяем, находится ли ячейка в пределах лабиринта
        if (row >= 0 && row < height && col >= 0 && col < width) {
            return grid[row][col]; // Возвращаем ячейку, если она в пределах
        } else {
            return null; // Возвращаем null, если координаты выходят за пределы
        }
    }

    // Метод для получения случайного соседа ячейки
    public Coordinate getRandomNeighbor(int row, int col) {
        List<Coordinate> neighbors = new ArrayList<>(); // Список для хранения соседей
        // Проверяем, есть ли сосед сверху, и добавляем его в список
        if (row > 0) {
            neighbors.add(new Coordinate(row - 1, col));
        }
        // Проверяем, есть ли сосед снизу
        if (row < height - 1) {
            neighbors.add(new Coordinate(row + 1, col));
        }
        // Проверяем, есть ли сосед слева
        if (col > 0) {
            neighbors.add(new Coordinate(row, col - 1));
        }
        // Проверяем, есть ли сосед справа
        if (col < width - 1) {
            neighbors.add(new Coordinate(row, col + 1));
        }

        // Если у ячейки есть соседи, выбираем случайного соседа
        if (!neighbors.isEmpty()) {
            return neighbors.get(random.nextInt(neighbors.size()));
        }

        return null; // Если соседей нет, возвращаем null
    }

    // Метод для установки входа в лабиринт
    public void setEntrance(Coordinate coordinate) {
        Cell cell = getCell(coordinate.getRow(), coordinate.getCol()); // Получаем ячейку по координатам
        if (cell != null) {
            cell.setType(Cell.CellType.ENTRY); // Устанавливаем тип ячейки как вход
        } else {
            // Если координаты некорректны, выбрасываем исключение
            throw new IllegalArgumentException("Invalid entrance coordinates: " + coordinate);
        }
    }

    // Метод для установки выхода из лабиринта
    public void setExit(Coordinate coordinate) {
        Cell cell = getCell(coordinate.getRow(), coordinate.getCol()); // Получаем ячейку по координатам
        if (cell != null) {
            cell.setType(Cell.CellType.EXIT); // Устанавливаем тип ячейки как выход
        } else {
            // Если координаты некорректны, выбрасываем исключение
            throw new IllegalArgumentException("Invalid exit coordinates: " + coordinate);
        }
    }

    // Геттеры для высоты и ширины лабиринта
    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }
}
