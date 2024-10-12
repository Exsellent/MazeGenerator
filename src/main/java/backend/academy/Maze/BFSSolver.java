package backend.academy.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BFSSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate goal) {
        int height = maze.getHeight();
        int width = maze.getWidth();

        // Матрица для отслеживания посещённых клеток
        boolean[][] visited = new boolean[height][width];
        // Словарь для хранения пути (откуда пришли в каждую клетку)
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();
        // Очередь для обработки клеток в порядке BFS
        Queue<Coordinate> queue = new LinkedList<>();

        // Добавляем стартовую клетку в очередь и помечаем её как посещённую
        queue.add(start);
        visited[start.getRow()][start.getCol()] = true;

        // CHECKSTYLE:OFF
        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } // Вверх, вниз, влево, вправо
        }; // CHECKSTYLE:ON

        // Основной цикл обхода в ширину
        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            // Если достигли цели, восстанавливаем путь
            if (current.equals(goal)) {
                return reconstructPath(cameFrom, goal);
            }

            // Проходим по всем возможным направлениям (вверх, вниз, влево, вправо)
            for (int[] direction : directions) {
                int newRow = current.getRow() + direction[0];
                int newCol = current.getCol() + direction[1];

                // Проверяем, является ли новая клетка допустимой для посещения
                if (isValid(newRow, newCol, height, width, maze, visited)) {
                    Coordinate neighbor = new Coordinate(newRow, newCol);

                    // Если клетка ещё не была посещена
                    if (!visited[newRow][newCol]) {
                        queue.add(neighbor); // Добавляем в очередь
                        visited[newRow][newCol] = true; // Отмечаем как посещённую
                        cameFrom.put(neighbor, current); // Запоминаем путь
                    }
                }
            }
        }

        // Если путь не найден, возвращаем пустой список
        return Collections.emptyList();
    }

    // Метод для восстановления пути от цели к старту, используя словарь cameFrom
    private List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> cameFrom, Coordinate goal) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = goal;

        // Восстанавливаем путь, следуя по цепочке пришедших координат
        while (cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }

        // Добавляем стартовую клетку и разворачиваем путь в правильном порядке
        path.add(current);
        Collections.reverse(path);
        return path;
    }

    // Метод проверки допустимости клетки (не выходит за границы, не является стеной и не посещена)
    private boolean isValid(int row, int col, int height, int width, Maze maze, boolean[][] visited) {
        return row >= 0 && row < height && col >= 0 && col < width
                && maze.getCell(row, col).getType() != Cell.CellType.WALL && !visited[row][col];
    }
}
