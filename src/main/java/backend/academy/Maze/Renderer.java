package backend.academy.Maze;

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
