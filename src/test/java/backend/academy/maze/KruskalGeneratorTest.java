package backend.academy.maze;

import backend.academy.Maze.Cell;
import backend.academy.Maze.Generator;
import backend.academy.Maze.KruskalGenerator;
import backend.academy.Maze.Maze;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KruskalGeneratorTest {
    private static final int MAX_ATTEMPTS = 100; // Максимальное количество попыток генерации лабиринта

    // Основной тест для проверки работы генератора Краскала
    @Test
    void testKruskalGenerator() {
        Generator kruskalGenerator = new KruskalGenerator(); // Создаем экземпляр генератора
        boolean testPassed = false; // Флаг успешного прохождения теста

        // Пробуем сгенерировать лабиринт до MAX_ATTEMPTS раз
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            Maze maze = kruskalGenerator.generate(10, 10); // Генерируем лабиринт 10x10

            // Если лабиринт валиден, считаем тест пройденным
            if (validateMaze(maze)) {
                testPassed = true;
                break;
            }
        }

        // Проверяем, что хотя бы одна попытка генерации лабиринта прошла успешно
        assertTrue(testPassed, "Kruskal generator failed to create a valid maze after " + MAX_ATTEMPTS + " attempts");
    }

    // Метод для проверки валидности сгенерированного лабиринта
    private boolean validateMaze(Maze maze) {
        // Лабиринт должен иметь размер 21x21, начальная и конечная клетки должны быть проходами,
        // лабиринт должен быть связанным и содержать специальные поверхности
        return maze.getHeight() == 21 && maze.getWidth() == 21 && maze.getCell(1, 1).getType() == Cell.CellType.PASSAGE
                && maze.getCell(19, 19).getType() == Cell.CellType.PASSAGE && isConnected(maze) // Проверка на
                                                                                                // связанность лабиринта
                && hasSpecialSurfaces(maze); // Проверка наличия специальных поверхностей
    }

    // Метод для проверки, что все проходы лабиринта связаны
    private boolean isConnected(Maze maze) {
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()]; // Массив для отслеживания посещенных
                                                                              // клеток
        dfs(maze, 1, 1, visited); // Запускаем поиск в глубину (DFS) с начальной клетки

        // Проверяем, что все проходы были посещены
        for (int row = 1; row < maze.getHeight() - 1; row += 2) {
            for (int col = 1; col < maze.getWidth() - 1; col += 2) {
                if (maze.getCell(row, col).getType() == Cell.CellType.PASSAGE && !visited[row][col]) {
                    return false; // Если есть непосещенные проходы, лабиринт несвязан
                }
            }
        }
        return true; // Лабиринт связан
    }

    // Поиск в глубину (DFS) для проверки связанности проходов лабиринта
    private void dfs(Maze maze, int row, int col, boolean[][] visited) {
        // Проверяем границы и тип клетки (если это стена или клетка уже посещена, возвращаемся)
        if (row <= 0 || col <= 0 || row >= maze.getHeight() - 1 || col >= maze.getWidth() - 1)
            return;
        if (visited[row][col] || maze.getCell(row, col).getType() == Cell.CellType.WALL)
            return;

        visited[row][col] = true; // Отмечаем клетку как посещенную

        // Рекурсивно обходим соседние клетки через два шага (для проходов)
        dfs(maze, row + 2, col, visited);
        dfs(maze, row - 2, col, visited);
        dfs(maze, row, col + 2, visited);
        dfs(maze, row, col - 2, visited);
    }

    // Метод для проверки наличия специальных поверхностей в лабиринте
    private boolean hasSpecialSurfaces(Maze maze) {
        boolean hasSwamp = false; // Флаг наличия болота
        boolean hasSand = false; // Флаг наличия песка
        boolean hasCoin = false; // Флаг наличия монеты

        // Проходим по всем клеткам лабиринта
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                Cell.CellType cellType = maze.getCell(row, col).getType();
                if (cellType == Cell.CellType.SWAMP)
                    hasSwamp = true;
                if (cellType == Cell.CellType.SAND)
                    hasSand = true;
                if (cellType == Cell.CellType.COIN)
                    hasCoin = true;

                // Если все три типа поверхностей найдены, возвращаем true
                if (hasSwamp && hasSand && hasCoin)
                    return true;
            }
        }

        return false; // Если не все типы поверхностей найдены, возвращаем false
    }
}
