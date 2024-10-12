package backend.academy.Maze;

import java.util.Random;

public class AldousBroderMazeGenerator implements Generator {
    private final Random random = new Random();

    @Override
    public Maze generate(int height, int width) {
        int gridHeight = height * 2 + 1;
        int gridWidth = width * 2 + 1;
        Cell[][] cells = new Cell[gridHeight][gridWidth];

        MazeConfig.initializeGrid(cells);

        int row = 1 + 2 * random.nextInt(height);
        int col = 1 + 2 * random.nextInt(width);
        cells[row][col].setType(Cell.CellType.PASSAGE);

        int visitedCells = 1;
        int totalCells = height * width;

        while (visitedCells < totalCells) {
            int[] direction = MazeConfig.DIRECTIONS[random.nextInt(MazeConfig.DIRECTIONS.length)];
            int newRow = row + 2 * direction[0];
            int newCol = col + 2 * direction[1];

            if (isValidCell(newRow, newCol, gridHeight, gridWidth)) {
                if (cells[newRow][newCol].getType() == Cell.CellType.WALL) {
                    cells[(row + newRow) / 2][(col + newCol) / 2].setType(Cell.CellType.PASSAGE);
                    cells[newRow][newCol].setType(Cell.CellType.PASSAGE);
                    visitedCells++;
                }
                row = newRow;
                col = newCol;
            }
        }

        MazeConfig.addSpecialSurfaces(cells, random);

        return new Maze(gridHeight, gridWidth, cells);
    }

    private boolean isValidCell(int row, int col, int height, int width) {
        return row > 0 && col > 0 && row < height - 1 && col < width - 1;
    }
}
