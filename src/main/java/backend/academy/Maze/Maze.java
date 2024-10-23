package backend.academy.Maze;

import backend.academy.Maze.config.MazeConfig;
import backend.academy.Maze.utils.Cell;
import backend.academy.Maze.utils.Coordinate;
import java.util.LinkedList;
import java.util.Queue;

public class Maze {
    private final Cell[][] cells;
    private final int height;
    private final int width;
    private Coordinate entrance;
    private Coordinate exit;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.cells = new Cell[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                this.cells[row][col] = new Cell(row, col, Cell.CellType.WALL);
            }
        }
    }

    public Maze(int height, int width, Cell[][] cells) {
        this.height = height;
        this.width = width;
        this.cells = cells;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Coordinate getEntrance() {
        return entrance;
    }

    public void setEntrance(Coordinate entrance) {
        this.entrance = entrance;
    }

    public Coordinate getExit() {
        return exit;
    }

    public void setExit(Coordinate exit) {
        this.exit = exit;
    }

    // Проверка валидности координат (координаты должны быть внутри лабиринта и не быть стеной)
    public boolean isValidCoordinate(Coordinate coordinate) {
        int row = coordinate.getRow();
        int col = coordinate.getCol();
        return row >= 0 && row < height && col >= 0 && col < width && cells[row][col].getType() != Cell.CellType.WALL;
    }

    public boolean hasValidPath(Coordinate start, Coordinate end) {
        if (!isValidCoordinate(start) || !isValidCoordinate(end)) {
            return false;
        }

        return bfs(start, end);
    }

    private boolean bfs(Coordinate start, Coordinate end) {
        boolean[][] visited = new boolean[height][width];
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);
        visited[start.getRow()][start.getCol()] = true;

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            if (current.equals(end)) {
                return true;
            }

            for (int[] direction : MazeConfig.DIRECTIONS) {
                int newRow = current.getRow() + direction[0];
                int newCol = current.getCol() + direction[1];

                if (isValidCoordinate(new Coordinate(newRow, newCol)) && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    queue.add(new Coordinate(newRow, newCol));
                }
            }
        }
        return false;
    }
}
