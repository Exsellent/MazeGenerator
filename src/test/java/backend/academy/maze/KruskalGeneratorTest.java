package backend.academy.maze;

import backend.academy.Maze.Maze;
import backend.academy.Maze.algorithms.KruskalGenerator;
import backend.academy.Maze.interfaces.Generator;
import backend.academy.Maze.utils.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KruskalGeneratorTest {
    private static final int MAX_ATTEMPTS = 100;

    @Test
    void testKruskalGenerator() {
        Generator kruskalGenerator = new KruskalGenerator();
        boolean testPassed = false;

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            Maze maze = kruskalGenerator.generate(10, 10);
            if (validateMaze(maze)) {
                testPassed = true;
                break;
            }
        }

        // Проверяем, что хотя бы один лабиринт был сгенерирован успешно
        assertTrue(testPassed, "Kruskal generator failed to create a valid maze after " + MAX_ATTEMPTS + " attempts");
    }

    private boolean validateMaze(Maze maze) {
        return maze.getHeight() == 21 && maze.getWidth() == 21 && maze.getCell(1, 1).getType() == Cell.CellType.PASSAGE
                && maze.getCell(maze.getHeight() - 2, maze.getWidth() - 2).getType() == Cell.CellType.PASSAGE
                && isConnected(maze) && hasSpecialSurfaces(maze);
    }

    // Проверка, что лабиринт связан, то есть можно добраться до всех проходов
    private boolean isConnected(Maze maze) {
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];
        dfs(maze, 1, 1, visited);

        for (int row = 1; row < maze.getHeight() - 1; row += 2) {
            for (int col = 1; col < maze.getWidth() - 1; col += 2) {
                if (maze.getCell(row, col).getType() == Cell.CellType.PASSAGE && !visited[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Рекурсивный обход лабиринта (DFS) для проверки связи
    private void dfs(Maze maze, int row, int col, boolean[][] visited) {
        if (row <= 0 || col <= 0 || row >= maze.getHeight() - 1 || col >= maze.getWidth() - 1 || visited[row][col]
                || maze.getCell(row, col).getType() == Cell.CellType.WALL) {
            return;
        }

        visited[row][col] = true;

        dfs(maze, row + 1, col, visited);
        dfs(maze, row - 1, col, visited);
        dfs(maze, row, col + 1, visited);
        dfs(maze, row, col - 1, visited);
    }

    // Проверка наличия специальных поверхностей в лабиринте
    private boolean hasSpecialSurfaces(Maze maze) {
        boolean hasSwamp = false, hasSand = false, hasCoin = false;

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

                // Если все три типа найдены
                if (hasSwamp && hasSand && hasCoin)
                    return true;
            }
        }
        return false; // Если хотя бы один из типов не найден
    }
}
