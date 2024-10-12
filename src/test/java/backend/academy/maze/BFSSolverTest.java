package backend.academy.maze;

import backend.academy.Maze.BFSSolver;
import backend.academy.Maze.Cell;
import backend.academy.Maze.Coordinate;
import backend.academy.Maze.Maze;
import backend.academy.Maze.Solver;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class BFSSolverTest {

    // Основной тест для проверки работы алгоритма поиска в ширину (BFS) на тестовом лабиринте
    @Test
    void testBFSSolver() {
        Solver bfsSolver = new BFSSolver(); // Создаем экземпляр решателя BFS
        Maze maze = createTestMaze(); // Создаем тестовый лабиринт
        Coordinate start = new Coordinate(1, 1); // Стартовая точка
        Coordinate goal = new Coordinate(maze.getHeight() - 2, maze.getWidth() - 2); // Целевая точка

        List<Coordinate> path = bfsSolver.solve(maze, start, goal); // Решаем лабиринт с использованием BFS

        assertFalse(path.isEmpty()); // Проверяем, что путь не пуст
        assertEquals(start, path.get(0)); // Проверяем, что путь начинается с начальной точки
        assertEquals(goal, path.get(path.size() - 1)); // Проверяем, что путь заканчивается в целевой точке
        assertNoWallsInPath(maze, path); // Проверяем, что в пути нет стен
        assertContinuousPath(path); // Проверяем, что путь является непрерывным (соседние клетки)
    }

    // Метод для создания тестового лабиринта размером 7x7
    private Maze createTestMaze() {
        int height = 7;
        int width = 7;
        Cell[][] cells = new Cell[height][width];

        // Заполняем лабиринт клетками типа "проход"
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[row][col] = new Cell(row, col, Cell.CellType.PASSAGE);
            }
        }

        // Устанавливаем стены по краям лабиринта
        for (int i = 0; i < width; i++) {
            cells[0][i].setType(Cell.CellType.WALL);
            cells[height - 1][i].setType(Cell.CellType.WALL);
        }
        for (int i = 0; i < height; i++) {
            cells[i][0].setType(Cell.CellType.WALL);
            cells[i][width - 1].setType(Cell.CellType.WALL);
        }

        // Устанавливаем несколько внутренних стен и специальные поверхности
        cells[2][2].setType(Cell.CellType.WALL); // Внутренние стены
        cells[2][3].setType(Cell.CellType.WALL);
        cells[3][2].setType(Cell.CellType.WALL);
        cells[3][3].setType(Cell.CellType.SWAMP); // Болото
        cells[4][4].setType(Cell.CellType.SAND); // Песок
        cells[5][5].setType(Cell.CellType.COIN); // Монета

        return new Maze(height, width, cells); // Возвращаем созданный лабиринт
    }

    // Проверяем, что на пути нет стен
    private void assertNoWallsInPath(Maze maze, List<Coordinate> path) {
        for (Coordinate coord : path) {
            assertNotEquals(Cell.CellType.WALL, maze.getCell(coord.getRow(), coord.getCol()).getType());
        }
    }

    // Проверяем, что путь непрерывный, то есть каждая клетка соседствует с предыдущей
    private void assertContinuousPath(List<Coordinate> path) {
        for (int i = 1; i < path.size(); i++) {
            assertTrue(areAdjacent(path.get(i - 1), path.get(i)));
        }
    }

    // Метод для проверки, что две клетки соседствуют
    private boolean areAdjacent(Coordinate c1, Coordinate c2) {
        int rowDiff = Math.abs(c1.getRow() - c2.getRow());
        int colDiff = Math.abs(c1.getCol() - c2.getCol());
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1); // Соседние по строке или колонке
    }
}
