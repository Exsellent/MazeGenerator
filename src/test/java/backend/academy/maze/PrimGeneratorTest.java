package backend.academy.maze;

import backend.academy.Maze.Maze;
import backend.academy.Maze.algorithms.PrimGenerator;
import backend.academy.Maze.interfaces.Generator;
import backend.academy.Maze.utils.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimGeneratorTest {
    private static final int MAX_ATTEMPTS = 100;

    @Test
    void testPrimGenerator() {
        Generator primGenerator = new PrimGenerator();
        boolean testPassed = false;

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            Maze maze = primGenerator.generate(10, 10);

            if (validateMaze(maze)) {
                testPassed = true;
                break;
            }
        }

        // Убедимся, что генерация завершилась успешно хотя бы за одну из попыток
        assertTrue(testPassed, "Генератор Прима не смог создать валидный лабиринт после " + MAX_ATTEMPTS + " попыток");
    }

    private boolean validateMaze(Maze maze) {
        return maze.getHeight() == 21 && maze.getWidth() == 21 && maze.getCell(1, 1).getType() == Cell.CellType.PASSAGE
                && maze.getCell(19, 19).getType() == Cell.CellType.PASSAGE && isConnected(maze)
                && hasSpecialSurfaces(maze);
    }

    private boolean isConnected(Maze maze) {
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];
        dfs(maze, 1, 1, visited);

        // Проверка, что каждая клетка-проход была посещена
        for (int row = 1; row < maze.getHeight() - 1; row += 2) {
            for (int col = 1; col < maze.getWidth() - 1; col += 2) {
                if (maze.getCell(row, col).getType() == Cell.CellType.PASSAGE && !visited[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Алгоритм обхода лабиринта в глубину (DFS)
    private void dfs(Maze maze, int row, int col, boolean[][] visited) {

        if (row <= 0 || col <= 0 || row >= maze.getHeight() - 1 || col >= maze.getWidth() - 1)
            return;
        if (visited[row][col] || maze.getCell(row, col).getType() == Cell.CellType.WALL)
            return;

        visited[row][col] = true;

        dfs(maze, row + 2, col, visited);
        dfs(maze, row - 2, col, visited);
        dfs(maze, row, col + 2, visited);
        dfs(maze, row, col - 2, visited);
    }

    // Проверка наличия в лабиринте особых типов поверхностей
    private boolean hasSpecialSurfaces(Maze maze) {
        boolean hasSwamp = false;
        boolean hasSand = false;
        boolean hasCoin = false;

        // Проходим по всем клеткам лабиринта
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                Cell.CellType cellType = maze.getCell(row, col).getType();

                if (cellType == Cell.CellType.SWAMP) {
                    hasSwamp = true;
                }
                if (cellType == Cell.CellType.SAND) {
                    hasSand = true;
                }
                if (cellType == Cell.CellType.COIN) {
                    hasCoin = true;
                }

                // Если найдены все три типа поверхностей, дальнейший обход не требуется
                if (hasSwamp && hasSand && hasCoin)
                    return true;
            }
        }
        return false; // Не все особые поверхности найдены
    }
}
