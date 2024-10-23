package backend.academy.Maze.solvers;

import backend.academy.Maze.Maze;
import backend.academy.Maze.interfaces.Solver;
import backend.academy.Maze.utils.Cell;
import backend.academy.Maze.utils.Coordinate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AStarSolver implements Solver {

    private static final int[][] DIRECTIONS = // CHECKSTYLE:OFF
            { { 1, 0 }, { -1, 0 }, { 0, -1 }, { 0, 1 } }; // CHECKSTYLE:ON

    private static final double SWAMP_COST = 5.0;
    private static final double SAND_COST = 3.0;
    private static final double COIN_COST = 0.5;
    private static final double PASSAGE_COST = 1.0;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate goal) {
        int height = maze.getHeight();
        int width = maze.getWidth();

        // Очередь с приоритетом для работы с открытым списком (open set) узлов,
        // отсортированных по общей стоимости

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::totalCost));

        Map<Coordinate, Node> allNodes = new HashMap<>();

        Node startNode = new Node(start, null, 0, heuristic(start, goal));
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {

            Node current = openSet.poll();

            if (current.coordinate().equals(goal)) {
                return reconstructPath(current);
            }

            for (int[] direction : DIRECTIONS) {
                int newRow = current.coordinate().getRow() + direction[0];
                int newCol = current.coordinate().getCol() + direction[1];
                Coordinate neighbor = new Coordinate(newRow, newCol);

                if (isValid(newRow, newCol, height, width, maze)) {

                    double newCost = current.gCost() + getCost(maze.getCell(newRow, newCol));

                    Node neighborNode = allNodes.getOrDefault(neighbor, new Node(neighbor, null, Double.MAX_VALUE, 0));

                    // Если новая стоимость пути меньше текущей стоимости для этого узла
                    // Обновляем узел соседа: указываем родителя, обновляем gCost и вычисляем hCost

                    if (newCost < neighborNode.gCost()) {
                        neighborNode = new Node(neighbor, current, newCost, heuristic(neighbor, goal));
                        allNodes.put(neighbor, neighborNode);
                        openSet.add(neighborNode);
                    }
                }
            }
        }

        return Collections.emptyList();
    }

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

        while (currentNode != null) {
            path.add(currentNode.coordinate());
            currentNode = currentNode.parent();
        }

        Collections.reverse(path);
        return path;
    }

    private double getCost(Cell cell) {
        return switch (cell.getType()) {
        case SWAMP -> SWAMP_COST;
        case SAND -> SAND_COST;
        case COIN -> COIN_COST;
        case PASSAGE -> PASSAGE_COST;
        default -> PASSAGE_COST; // По умолчанию - обычный проход
        };
    }

    private record Node(Coordinate coordinate, Node parent, double gCost, double hCost) {
        double totalCost() {
            return gCost + hCost;
        }
    }
}
