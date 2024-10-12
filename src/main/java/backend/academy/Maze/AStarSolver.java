package backend.academy.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AStarSolver implements Solver {

    // Массив направлений для передвижения по лабиринту (вниз, вверх, влево, вправо)
    private static final int[][] DIRECTIONS = // CHECKSTYLE:OFF
            { { 1, 0 }, { -1, 0 }, { 0, -1 }, { 0, 1 } }; // CHECKSTYLE:ON

    // Константы стоимости передвижения по разным типам поверхностей
    private static final double SWAMP_COST = 5.0; // Стоимость передвижения по болоту
    private static final double SAND_COST = 3.0; // Стоимость передвижения по песку
    private static final double COIN_COST = 0.5; // Стоимость передвижения по участкам с монетами
    private static final double PASSAGE_COST = 1.0; // Стоимость передвижения по обычным проходам

    // Основной метод, реализующий алгоритм поиска пути A* (A Star)
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate goal) {
        // Получаем размеры лабиринта
        int height = maze.getHeight();
        int width = maze.getWidth();

        // Очередь с приоритетом для работы с открытым списком (open set) узлов, отсортированных по общей стоимости
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getTotalCost));

        // Карта всех узлов для отслеживания их состояния
        Map<Coordinate, Node> allNodes = new HashMap<>();

        // Инициализируем стартовый узел с начальной позицией, без родителя,
        // с нулевой gCost и расчетом hCost (эвристики)
        Node startNode = new Node(start, null, 0, heuristic(start, goal));
        openSet.add(startNode); // Добавляем стартовый узел в открытый список
        allNodes.put(start, startNode); // Сохраняем узел в карте

        // Пока есть узлы в открытом списке
        while (!openSet.isEmpty()) {
            // Берем узел с наименьшей общей стоимостью
            Node current = openSet.poll();

            // Если текущий узел является целевой позицией, восстанавливаем путь
            if (current.coordinate.equals(goal)) {
                return reconstructPath(current);
            }

            // Проходим по всем соседним клеткам в направлениях вниз, вверх, влево, вправо
            for (int[] direction : DIRECTIONS) {
                int newRow = current.coordinate.getRow() + direction[0];
                int newCol = current.coordinate.getCol() + direction[1];
                Coordinate neighbor = new Coordinate(newRow, newCol);

                // Проверяем, является ли новая позиция допустимой для передвижения
                if (isValid(newRow, newCol, height, width, maze)) {
                    // Вычисляем новую стоимость пути до соседа
                    double newCost = current.gCost + getCost(maze.getCell(newRow, newCol));

                    // Получаем узел соседа, если он уже существует, или создаем новый с максимальной gCost
                    Node neighborNode = allNodes.getOrDefault(neighbor, new Node(neighbor, null, Double.MAX_VALUE, 0));

                    // Если новая стоимость пути меньше текущей стоимости для этого узла
                    if (newCost < neighborNode.gCost) {
                        // Обновляем узел соседа: указываем родителя, обновляем gCost и вычисляем hCost
                        neighborNode.parent = current;
                        neighborNode.gCost = newCost;
                        neighborNode.hCost = heuristic(neighbor, goal);
                        allNodes.put(neighbor, neighborNode); // Обновляем узел в карте
                        openSet.add(neighborNode); // Добавляем узел в очередь с приоритетом
                    }
                }
            }
        }

        // Если путь не найден, возвращаем пустой список
        return Collections.emptyList();
    }

    // Проверяет, является ли указанная клетка допустимой для передвижения (не выходит за границы и не стена)
    private boolean isValid(int row, int col, int height, int width, Maze maze) {
        return row >= 0 && row < height && col >= 0 && col < width
                && maze.getCell(row, col).getType() != Cell.CellType.WALL;
    }

    // Метод для расчета эвристики (манхэттенское расстояние) между двумя координатами
    private double heuristic(Coordinate from, Coordinate to) {
        return Math.abs(from.getRow() - to.getRow()) + Math.abs(from.getCol() - to.getCol());
    }

    // Восстанавливаем путь от целевой точки к стартовой через цепочку родительских узлов
    private List<Coordinate> reconstructPath(Node node) {
        List<Coordinate> path = new ArrayList<>();
        Node currentNode = node;

        // Идем по цепочке узлов, добавляя координаты в путь
        while (currentNode != null) {
            path.add(currentNode.coordinate);
            currentNode = currentNode.parent;
        }

        // Переворачиваем список, чтобы путь начинался с начальной позиции
        Collections.reverse(path);
        return path;
    }

    // Возвращает стоимость передвижения по клетке, в зависимости от типа поверхности
    private double getCost(Cell cell) {
        return switch (cell.getType()) {
        case SWAMP -> SWAMP_COST; // Стоимость движения по болоту
        case SAND -> SAND_COST; // Стоимость движения по песку
        case COIN -> COIN_COST; // Стоимость движения по участкам с монетами
        case PASSAGE -> PASSAGE_COST; // Стоимость движения по проходу
        default -> PASSAGE_COST; // По умолчанию - обычный проход
        };
    }

    // Внутренний класс для хранения информации об узле лабиринта
    private static class Node {
        Coordinate coordinate; // Координата узла
        Node parent; // Родительский узел (предыдущий шаг)
        double gCost; // Стоимость пути от начала до данного узла (g)
        double hCost; // Оценочная стоимость пути от данного узла до цели (h)

        // Конструктор для создания узла с заданными координатами, родителем, gCost и hCost
        Node(Coordinate coordinate, Node parent, double gCost, double hCost) {
            this.coordinate = coordinate;
            this.parent = parent;
            this.gCost = gCost;
            this.hCost = hCost;
        }

        // Метод для получения общей стоимости узла (gCost + hCost)
        double getTotalCost() {
            return gCost + hCost;
        }
    }
}
