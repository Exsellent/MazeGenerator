package backend.academy.Maze.interfaces;

import backend.academy.Maze.Maze;
import backend.academy.Maze.utils.Coordinate;
import java.util.List;

public interface Solver {
    /**
     * Находит путь в лабиринте от начальной точки до конечной.
     *
     * @param maze
     *            лабиринт, в котором нужно найти путь
     * @param start
     *            координаты начальной точки
     * @param end
     *            координаты конечной точки
     *
     * @return список координат, представляющий путь, или пустой список, если путь не найден
     */
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
