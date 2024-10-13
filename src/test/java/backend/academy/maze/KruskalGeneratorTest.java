package backend.academy.maze;

import backend.academy.Maze.Cell;
import backend.academy.Maze.Generator;
import backend.academy.Maze.KruskalGenerator;
import backend.academy.Maze.Maze;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KruskalGeneratorTest {
    private static final int MAX_ATTEMPTS = 100; // Максимальное количество попыток для генерации валидного лабиринта

    @Test
    void testKruskalGenerator() {
        Generator kruskalGenerator = new KruskalGenerator();
        boolean testPassed = false;

        // Пытаемся создать валидный лабиринт за MAX_ATTEMPTS попыток
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            Maze maze = kruskalGenerator.generate(10, 10);
            if (validateMaze(maze)) {
                testPassed = true; // Лабиринт успешно прошел проверку
                break;
            }
        }

        // Проверяем, что хотя бы один лабиринт был сгенерирован успешно
        assertTrue(testPassed, "Kruskal generator failed to create a valid maze after " + MAX_ATTEMPTS + " attempts");
    }

    // Проверка валидности сгенерированного лабиринта
    private boolean validateMaze(Maze maze) {
        return maze.getHeight() == 21 && maze.getWidth() == 21 && maze.getCell(1, 1).getType() == Cell.CellType.PASSAGE
                && maze.getCell(maze.getHeight() - 2, maze.getWidth() - 2).getType() == Cell.CellType.PASSAGE
                && isConnected(maze) && hasSpecialSurfaces(maze);
    }

    // Проверка, что лабиринт связан, то есть можно добраться до всех проходов
    private boolean isConnected(Maze maze) {
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];
        dfs(maze, 1, 1, visited); // Начинаем обход с первой доступной клетки

        // Проверяем, что все проходы доступны
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
            return; // Выход, если достигли границы, уже посетили или наткнулись на стену
        }

        visited[row][col] = true; // Отмечаем клетку как посещенную

        // Рекурсивно обходим все соседние клетки
        dfs(maze, row + 1, col, visited);
        dfs(maze, row - 1, col, visited);
        dfs(maze, row, col + 1, visited);
        dfs(maze, row, col - 1, visited);
    }

    // Проверка наличия специальных поверхностей в лабиринте (болота, песок, монеты)
    private boolean hasSpecialSurfaces(Maze maze) {
        boolean hasSwamp = false, hasSand = false, hasCoin = false;

        // Перебираем все клетки лабиринта и проверяем их типы
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                Cell.CellType cellType = maze.getCell(row, col).getType();
                if (cellType == Cell.CellType.SWAMP)
                    hasSwamp = true; // Обнаружена клетка типа "болото"
                if (cellType == Cell.CellType.SAND)
                    hasSand = true; // Обнаружена клетка типа "песок"
                if (cellType == Cell.CellType.COIN)
                    hasCoin = true; // Обнаружена клетка типа "монета"

                // Если все три типа найдены, возвращаем true
                if (hasSwamp && hasSand && hasCoin)
                    return true;
            }
        }
        return false; // Если хотя бы один из типов не найден, возвращаем false
    }
}
