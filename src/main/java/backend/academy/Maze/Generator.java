package backend.academy.Maze;

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
