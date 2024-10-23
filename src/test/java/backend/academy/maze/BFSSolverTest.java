package backend.academy.maze;

import backend.academy.Maze.Maze;
import backend.academy.Maze.interfaces.Solver;
import backend.academy.Maze.solvers.BFSSolver;
import backend.academy.Maze.utils.Cell;
import backend.academy.Maze.utils.Coordinate;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BFSSolverTest {

    @Test
    void testBFSSolver() {
        Solver bfsSolver = new BFSSolver();
        Maze maze = createTestMaze();
        Coordinate start = new Coordinate(1, 1);
        Coordinate goal = new Coordinate(maze.getHeight() - 2, maze.getWidth() - 2);

        List<Coordinate> path = bfsSolver.solve(maze, start, goal);

        assertFalse(path.isEmpty());
        assertEquals(start, path.get(0));
        assertEquals(goal, path.get(path.size() - 1));
        assertNoWallsInPath(maze, path);
        assertContinuousPath(path);
    }

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

        cells[2][2].setType(Cell.CellType.WALL);
        cells[2][3].setType(Cell.CellType.WALL);
        cells[3][2].setType(Cell.CellType.WALL);
        cells[3][3].setType(Cell.CellType.SWAMP);
        cells[4][4].setType(Cell.CellType.SAND);
        cells[5][5].setType(Cell.CellType.COIN);

        return new Maze(height, width, cells);
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

    private boolean areAdjacent(Coordinate c1, Coordinate c2) {
        int rowDiff = Math.abs(c1.getRow() - c2.getRow());
        int colDiff = Math.abs(c1.getCol() - c2.getCol());
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }
}
