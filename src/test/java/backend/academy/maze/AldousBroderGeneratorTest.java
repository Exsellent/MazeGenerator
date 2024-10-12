package backend.academy.maze;

import backend.academy.Maze.AldousBroderMazeGenerator;
import backend.academy.Maze.Cell;
import backend.academy.Maze.Generator;
import backend.academy.Maze.Maze;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AldousBroderGeneratorTest {
    private static final int MAX_ATTEMPTS = 100; // Максимальное количество попыток для генерации валидного лабиринта

    @Test
    void testAldousBroderGenerator() {
        Generator aldousBroderGenerator = new AldousBroderMazeGenerator();
        boolean testPassed = false;

        // Выполняем генерацию лабиринта с максимальным количеством попыток
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            Maze maze = aldousBroderGenerator.generate(10, 10); // Генерация лабиринта 10x10

            // Если лабиринт валиден, тест пройден
            if (validateMaze(maze)) {
                testPassed = true;
                break;
            }
        }

        // Проверяем, что лабиринт сгенерирован успешно в рамках допустимого количества попыток
        assertTrue(testPassed,
                "Генератор Aldous-Broder не смог создать валидный лабиринт после " + MAX_ATTEMPTS + " попыток");
    }

    // Метод проверки валидности сгенерированного лабиринта
    private boolean validateMaze(Maze maze) {
        return maze.getHeight() == 21 && maze.getWidth() == 21 // Проверяем размеры лабиринта
                && maze.getCell(1, 1).getType() == Cell.CellType.PASSAGE // Начальная точка проходима
                && maze.getCell(19, 19).getType() == Cell.CellType.PASSAGE // Конечная точка проходима
                && isConnected(maze) // Проверяем связанность лабиринта
                && hasSpecialSurfaces(maze); // Проверяем наличие специальных поверхностей
    }

    // Метод проверки связанности лабиринта (все клетки должны быть достижимы)
    private boolean isConnected(Maze maze) {
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];
        dfs(maze, 1, 1, visited); // Обход в глубину (DFS) начиная с клетки (1,1)

        // Проверяем каждую проходимую клетку, чтобы убедиться, что она была посещена
        for (int row = 1; row < maze.getHeight() - 1; row += 2) {
            for (int col = 1; col < maze.getWidth() - 1; col += 2) {
                if (maze.getCell(row, col).getType() == Cell.CellType.PASSAGE && !visited[row][col]) {
                    return false; // Если клетка не была посещена, лабиринт не связан
                }
            }
        }
        return true;
    }

    // Обход в глубину для проверки связанности лабиринта
    private void dfs(Maze maze, int row, int col, boolean[][] visited) {
        // Проверка границ лабиринта и уже посещенных клеток
        if (row <= 0 || col <= 0 || row >= maze.getHeight() - 1 || col >= maze.getWidth() - 1)
            return;
        if (visited[row][col] || maze.getCell(row, col).getType() == Cell.CellType.WALL)
            return;

        visited[row][col] = true; // Отмечаем клетку как посещенную

        // Продолжаем обход в четырех направлениях (вверх, вниз, влево, вправо)
        dfs(maze, row + 2, col, visited);
        dfs(maze, row - 2, col, visited);
        dfs(maze, row, col + 2, visited);
        dfs(maze, row, col - 2, visited);
    }

    // Метод для проверки наличия специальных поверхностей в лабиринте (болото, песок, монеты)
    private boolean hasSpecialSurfaces(Maze maze) {
        boolean hasSwamp = false; // Флаг наличия болота
        boolean hasSand = false; // Флаг наличия песка
        boolean hasCoin = false; // Флаг наличия монет

        // Проверяем каждую клетку лабиринта
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                Cell.CellType cellType = maze.getCell(row, col).getType();

                // Если находим специальную поверхность, отмечаем соответствующий флаг
                if (cellType == Cell.CellType.SWAMP)
                    hasSwamp = true;
                if (cellType == Cell.CellType.SAND)
                    hasSand = true;
                if (cellType == Cell.CellType.COIN)
                    hasCoin = true;

                // Если все специальные поверхности найдены, возвращаем true
                if (hasSwamp && hasSand && hasCoin)
                    return true;
            }
        }
        return false; // Если не найдены все поверхности, возвращаем false
    }
}
