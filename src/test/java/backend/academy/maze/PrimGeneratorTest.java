package backend.academy.maze;

import backend.academy.Maze.Cell;
import backend.academy.Maze.Generator;
import backend.academy.Maze.Maze;
import backend.academy.Maze.PrimGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrimGeneratorTest {
    private static final int MAX_ATTEMPTS = 100; // Максимальное количество попыток генерации лабиринта

    @Test
    void testPrimGenerator() {
        Generator primGenerator = new PrimGenerator(); // Создание генератора лабиринтов по алгоритму Прима
        boolean testPassed = false; // Флаг успешности теста

        // Попытка сгенерировать валидный лабиринт за ограниченное количество попыток
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            Maze maze = primGenerator.generate(10, 10); // Генерация лабиринта 10x10

            // Проверка, что лабиринт валиден
            if (validateMaze(maze)) {
                testPassed = true;
                break;
            }
        }

        // Убедимся, что генерация завершилась успешно хотя бы за одну из попыток
        assertTrue(testPassed, "Генератор Прима не смог создать валидный лабиринт после " + MAX_ATTEMPTS + " попыток");
    }

    // Проверка валидности лабиринта
    private boolean validateMaze(Maze maze) {
        return maze.getHeight() == 21 && maze.getWidth() == 21 // Проверка высоты и ширины лабиринта
                && maze.getCell(1, 1).getType() == Cell.CellType.PASSAGE // Начальная клетка должна быть проходом
                && maze.getCell(19, 19).getType() == Cell.CellType.PASSAGE // Конечная клетка также должна быть проходом
                && isConnected(maze) // Проверка, что все проходы соединены
                && hasSpecialSurfaces(maze); // Проверка наличия особых поверхностей (болото, песок, монеты)
    }

    // Проверка, что все проходы лабиринта связаны
    private boolean isConnected(Maze maze) {
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()]; // Массив посещенных клеток
        dfs(maze, 1, 1, visited); // Запуск обхода в глубину с начальной клетки

        // Проверка, что каждая клетка-проход была посещена
        for (int row = 1; row < maze.getHeight() - 1; row += 2) {
            for (int col = 1; col < maze.getWidth() - 1; col += 2) {
                if (maze.getCell(row, col).getType() == Cell.CellType.PASSAGE && !visited[row][col]) {
                    return false; // Если хоть одна клетка-проход не была посещена, лабиринт не валиден
                }
            }
        }
        return true; // Все проходы связаны
    }

    // Алгоритм обхода лабиринта в глубину (DFS)
    private void dfs(Maze maze, int row, int col, boolean[][] visited) {
        // Если вышли за границы лабиринта или клетка уже посещена/является стеной, выходим из рекурсии
        if (row <= 0 || col <= 0 || row >= maze.getHeight() - 1 || col >= maze.getWidth() - 1)
            return;
        if (visited[row][col] || maze.getCell(row, col).getType() == Cell.CellType.WALL)
            return;

        visited[row][col] = true; // Отмечаем клетку как посещенную

        // Рекурсивно обходим соседние клетки
        dfs(maze, row + 2, col, visited);
        dfs(maze, row - 2, col, visited);
        dfs(maze, row, col + 2, visited);
        dfs(maze, row, col - 2, visited);
    }

    // Проверка наличия в лабиринте особых типов поверхностей (болото, песок, монеты)
    private boolean hasSpecialSurfaces(Maze maze) {
        boolean hasSwamp = false;
        boolean hasSand = false;
        boolean hasCoin = false;

        // Проходим по всем клеткам лабиринта
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                Cell.CellType cellType = maze.getCell(row, col).getType();

                // Проверяем тип клетки
                if (cellType == Cell.CellType.SWAMP)
                    hasSwamp = true; // Клетка - болото
                if (cellType == Cell.CellType.SAND)
                    hasSand = true; // Клетка - песок
                if (cellType == Cell.CellType.COIN)
                    hasCoin = true; // Клетка с монетой

                // Если найдены все три типа поверхностей, дальнейший обход не требуется
                if (hasSwamp && hasSand && hasCoin)
                    return true;
            }
        }
        return false; // Не все особые поверхности найдены
    }
}
