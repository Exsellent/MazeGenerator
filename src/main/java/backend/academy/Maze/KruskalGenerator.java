package backend.academy.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KruskalGenerator implements Generator {
    private final Random random = new Random();

    @Override
    public Maze generate(int height, int width) {
        int gridHeight = height * 2 + 1;
        int gridWidth = width * 2 + 1;
        Cell[][] grid = new Cell[gridHeight][gridWidth];
        List<Edge> edges = new ArrayList<>();
        DisjointSet disjointSet = new DisjointSet(height * width);

        initializeGrid(grid, gridHeight, gridWidth, edges);
        Collections.shuffle(edges, random);

        for (Edge edge : edges) {
            int index1 = (edge.row1 / 2) * width + (edge.col1 / 2);
            int index2 = (edge.row2 / 2) * width + (edge.col2 / 2);

            if (disjointSet.find(index1) != disjointSet.find(index2)) {
                disjointSet.union(index1, index2);
                int middleRow = (edge.row1 + edge.row2) / 2;
                int middleCol = (edge.col1 + edge.col2) / 2;
                grid[middleRow][middleCol].setType(Cell.CellType.PASSAGE);
                grid[edge.row1][edge.col1].setType(Cell.CellType.PASSAGE);
                grid[edge.row2][edge.col2].setType(Cell.CellType.PASSAGE);
            }
        }

        grid[1][1].setType(Cell.CellType.PASSAGE);
        grid[gridHeight - 2][gridWidth - 2].setType(Cell.CellType.PASSAGE);

        MazeConfig.addSpecialSurfaces(grid, random);

        return new Maze(gridHeight, gridWidth, grid);
    }

    private void initializeGrid(Cell[][] grid, int gridHeight, int gridWidth, List<Edge> edges) {
        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                grid[row][col] = new Cell(row, col, Cell.CellType.WALL);
            }
        }

        for (int row = 1; row < gridHeight - 1; row += 2) {
            for (int col = 1; col < gridWidth - 1; col += 2) {
                if (row < gridHeight - 2) {
                    edges.add(new Edge(row, col, row + 2, col));
                }
                if (col < gridWidth - 2) {
                    edges.add(new Edge(row, col, row, col + 2));
                }
            }
        }
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
