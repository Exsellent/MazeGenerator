package backend.academy.Maze.interfaces;

import backend.academy.Maze.Maze;
import backend.academy.Maze.utils.Coordinate;
import java.util.List;

public interface Renderer {
    /**
     * Отображает лабиринт без решения.
     *
     * @param maze
     *            лабиринт для отображения
     *
     * @return строка, представляющая визуализацию лабиринта
     */
    String render(Maze maze);

    /**
     * Отображает лабиринт с найденным путём.
     *
     * @param maze
     *            лабиринт для отображения
     * @param path
     *            список координат, представляющий путь
     *
     * @return строка, представляющая визуализацию лабиринта с путём
     */
    String render(Maze maze, List<Coordinate> path);
}
