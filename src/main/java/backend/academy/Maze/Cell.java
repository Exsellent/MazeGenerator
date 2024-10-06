package backend.academy.Maze;

public class Cell {

    // Определяем перечисление (enum) для типов ячеек в лабиринте
    public enum CellType {
        WALL,        // Стена
        PASSAGE,     // Проход
        SWAMP,       // Болотистая местность (замедляет движение)
        SAND,        // Песок (тоже замедляет движение)
        COIN,        // Монета (увеличивает скорость)
        ENTRY,       // Вход в лабиринт
        EXIT         // Выход из лабиринта
    }

    // Координаты строки и столбца для текущей ячейки
    private final int row;
    private final int col;

    // Тип ячейки (по умолчанию может быть WALL, PASSAGE и т.д.)
    private CellType type;

    // Конструктор класса Cell, инициализирует строку, столбец и тип ячейки
    public Cell(int row, int col, CellType type) {
        this.row = row;   // Устанавливаем координату строки
        this.col = col;   // Устанавливаем координату столбца
        this.type = type; // Устанавливаем тип ячейки
    }

    // Метод для получения координаты строки
    public int getRow() {
        return row;
    }

    // Метод для получения координаты столбца
    public int getCol() {
        return col;
    }

    // Метод для получения типа ячейки
    public CellType getType() {
        return type;
    }

    // Метод для установки типа ячейки
    public void setType(CellType type) {
        this.type = type;
    }

    // Метод для проверки, является ли ячейка стеной
    public boolean isWall() {
        return this.type == CellType.WALL;
    }

    // Переопределение метода toString для вывода информации о ячейке
    @Override public String toString() {
        return String.format("Cell(%d, %d, %s)", row, col, type);
    }
}
