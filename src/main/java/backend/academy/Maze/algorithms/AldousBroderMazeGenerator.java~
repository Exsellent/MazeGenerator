package backend.academy.Maze.algorithms;

import backend.academy.Maze.Maze;
import backend.academy.Maze.config.MazeConfig;
import backend.academy.Maze.interfaces.Generator;
import backend.academy.Maze.utils.Cell;
import java.util.Random;

/**
 * Класс AldousBroderMazeGenerator реализует алгоритм генерации лабиринтов на основе алгоритма Алдуса-Бродера. Этот
 * алгоритм случайным образом перемещается по сетке, пока не посетит все клетки, соединяя непосещённые клетки с
 * проходами.
 */
public class AldousBroderMazeGenerator implements Generator {
    private final Random random = new Random();

    @Override
    public Maze generate(int height, int width) {
        int gridHeight = height * 2 + 1;
        int gridWidth = width * 2 + 1;
        Cell[][] cells = new Cell[gridHeight][gridWidth];

        initializeGrid(cells);
        generateMaze(cells, height, width);

        MazeConfig.addSpecialSurfaces(cells, random);

        return new Maze(gridHeight, gridWidth, cells);
    }

    private void initializeGrid(Cell[][] cells) {
        MazeConfig.initializeGrid(cells);
    }

    private void generateMaze(Cell[][] cells, int height, int width) {
        int row = 1 + 2 * random.nextInt(height);
        int col = 1 + 2 * random.nextInt(width);
        cells[row][col].setType(Cell.CellType.PASSAGE);

        int visitedCells = 1;
        int totalCells = height * width;

        while (visitedCells < totalCells) {
            int[] direction = MazeConfig.DIRECTIONS[random.nextInt(MazeConfig.DIRECTIONS.length)];
            int newRow = row + 2 * direction[0];
            int newCol = col + 2 * direction[1];

            if (isValidCell(newRow, newCol, cells.length, cells[0].length)) {
                if (cells[newRow][newCol].getType() == Cell.CellType.WALL) {
                    cells[(row + newRow) / 2][(col + newCol) / 2].setType(Cell.CellType.PASSAGE);
                    cells[newRow][newCol].setType(Cell.CellType.PASSAGE);
                    visitedCells++;
                }
                row = newRow;
                col = newCol;
            }
        }
    }

    private boolean isValidCell(int row, int col, int height, int width) {
        return row > 0 && col > 0 && row < height - 1 && col < width - 1;
    }
}
