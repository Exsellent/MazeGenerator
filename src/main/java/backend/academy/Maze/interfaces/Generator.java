package backend.academy.Maze.interfaces;

import backend.academy.Maze.Maze;

public interface Generator {
    /**
     * Генерирует лабиринт заданного размера.
     *
     * @param height
     *            высота лабиринта
     * @param width
     *            ширина лабиринта
     *
     * @return сгенерированный объект типа Maze
     */
    Maze generate(int height, int width);
}
