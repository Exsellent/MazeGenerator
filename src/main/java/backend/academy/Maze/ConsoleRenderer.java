package backend.academy.Maze;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleRenderer implements Renderer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleRenderer.class);

    // ANSI escape codes для цветов
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";

    private static final char WALL = '▓';
    private static final char PASSAGE = ' ';
    private static final char PATH = '*';
    private static final char ENTRY = 'S';
    private static final char EXIT = 'E';
    private static final char SWAMP = '#';
    private static final char SAND = '$';
    private static final char COIN = '@';

    @Override
    public String render(Maze maze) {
        return render(maze, null);
    }

    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder sb = new StringBuilder();
        int height = maze.getHeight();
        int width = maze.getWidth();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Coordinate current = new Coordinate(row, col);
                if (maze.getEntrance().equals(current)) {
                    sb.append(GREEN).append(ENTRY).append(RESET); // Вход - зеленый
                } else if (maze.getExit().equals(current)) {
                    sb.append(PURPLE).append(EXIT).append(RESET); // Выход - фиолетовый
                } else if (path != null && path.contains(current)
                        && maze.getCell(row, col).getType() != Cell.CellType.WALL) {
                    sb.append(YELLOW).append(PATH).append(RESET); // Путь - желтый
                } else {
                    sb.append(getColoredSymbolForCell(maze.getCell(row, col)));
                }
            }
            sb.append(System.lineSeparator());
        }

        String result = sb.toString();

        return result;
    }

    private String getColoredSymbolForCell(Cell cell) {
        return switch (cell.getType()) {
        case WALL -> BLUE + WALL + RESET; // Стены - синие
        case PASSAGE -> PASSAGE + "";
        case SWAMP -> GREEN + SWAMP + RESET; // Болото - зеленый
        case SAND -> YELLOW + SAND + RESET; // Песок - желтый
        case COIN -> RED + COIN + RESET; // Монета - красный
        default -> PASSAGE + "";
        };
    }
}
