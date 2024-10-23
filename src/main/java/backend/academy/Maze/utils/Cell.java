package backend.academy.Maze.utils;

public class Cell {

    public enum CellType {
        WALL, PASSAGE, SWAMP, SAND, COIN, ENTRY, EXIT
    }

    private final int row;
    private final int col;

    // Тип ячейки (по умолчанию может быть WALL, PASSAGE и т.д.)
    private CellType type;

    public Cell(int row, int col, CellType type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public boolean isWall() {
        return this.type == CellType.WALL;
    }

    // для вывода информации о ячейке
    @Override
    public String toString() {
        return String.format("Cell(%d, %d, %s)", row, col, type);
    }
}
