package backend.academy.Maze.algorithms;

import backend.academy.Maze.Maze;
import backend.academy.Maze.config.MazeConfig;
import backend.academy.Maze.interfaces.Generator;
import backend.academy.Maze.utils.Cell;
import backend.academy.Maze.utils.DisjointSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Класс KruskalGenerator реализует алгоритм генерации лабиринтов на основе алгоритма Краскала. Использует структуру
 * данных DisjointSet для предотвращения создания циклов.
 */
public class KruskalGenerator implements Generator {
    private final Random random = new Random();

    private static final int WALL_THICKNESS = 1;
    private static final int CELL_SIZE = 2;

    @Override
    public Maze generate(int height, int width) {
        int gridHeight = height * CELL_SIZE + WALL_THICKNESS;
        int gridWidth = width * CELL_SIZE + WALL_THICKNESS;
        Cell[][] grid = new Cell[gridHeight][gridWidth];
        List<Edge> edges = new ArrayList<>();
        DisjointSet disjointSet = new DisjointSet(height * width);

        initializeGrid(grid, gridHeight, gridWidth, edges);
        Collections.shuffle(edges, random);
        processEdges(grid, edges, disjointSet, width);

        setStartAndEndPoints(grid, gridHeight, gridWidth);
        MazeConfig.addSpecialSurfaces(grid, random);

        return new Maze(gridHeight, gridWidth, grid);
    }

    private void initializeGrid(Cell[][] grid, int gridHeight, int gridWidth, List<Edge> edges) {
        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                grid[row][col] = new Cell(row, col, Cell.CellType.WALL);
            }
        }

        for (int row = WALL_THICKNESS; row < gridHeight - WALL_THICKNESS; row += CELL_SIZE) {
            for (int col = WALL_THICKNESS; col < gridWidth - WALL_THICKNESS; col += CELL_SIZE) {
                if (row < gridHeight - CELL_SIZE) {
                    edges.add(new Edge(row, col, row + CELL_SIZE, col));
                }
                if (col < gridWidth - CELL_SIZE) {
                    edges.add(new Edge(row, col, row, col + CELL_SIZE));
                }
            }
        }
    }

    private void processEdges(Cell[][] grid, List<Edge> edges, DisjointSet disjointSet, int width) {
        for (Edge edge : edges) {
            int index1 = (edge.row1 / CELL_SIZE) * width + (edge.col1 / CELL_SIZE);
            int index2 = (edge.row2 / CELL_SIZE) * width + (edge.col2 / CELL_SIZE);

            if (disjointSet.find(index1) != disjointSet.find(index2)) {
                disjointSet.union(index1, index2);
                int middleRow = (edge.row1 + edge.row2) / CELL_SIZE;
                int middleCol = (edge.col1 + edge.col2) / CELL_SIZE;
                grid[middleRow][middleCol].setType(Cell.CellType.PASSAGE);
                grid[edge.row1][edge.col1].setType(Cell.CellType.PASSAGE);
                grid[edge.row2][edge.col2].setType(Cell.CellType.PASSAGE);
            }
        }
    }

    private void setStartAndEndPoints(Cell[][] grid, int gridHeight, int gridWidth) {
        grid[WALL_THICKNESS][WALL_THICKNESS].setType(Cell.CellType.PASSAGE);
        grid[gridHeight - WALL_THICKNESS - 1][gridWidth - WALL_THICKNESS - 1].setType(Cell.CellType.PASSAGE);
    }

    private static class Edge {
        private final int row1;
        private final int col1;
        private final int row2;
        private final int col2;

        Edge(int row1, int col1, int row2, int col2) {
            this.row1 = row1;
            this.col1 = col1;
            this.row2 = row2;
            this.col2 = col2;
        }
    }
}
