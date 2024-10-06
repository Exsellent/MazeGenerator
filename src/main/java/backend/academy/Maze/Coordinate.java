package backend.academy.Maze;

public class Coordinate {
    private final int row; // Координата строки
    private final int col; // Координата столбца

    // Конструктор класса Coordinate, инициализирует строку и столбец
    public Coordinate(int row, int col) {
        this.row = row; // Устанавливаем значение строки
        this.col = col; // Устанавливаем значение столбца
    }

    // Метод для получения значения строки
    public int getRow() {
        return row;
    }

    // Метод для получения значения столбца
    public int getCol() {
        return col;
    }

    // Переопределенный метод equals для сравнения координат
    @Override public boolean equals(Object o) {
        // Проверяем, если объекты одинаковы (сравниваем ссылки)
        if (this == o) {
            return true;
        }
        // Если объект равен null или класс объектов не совпадает, возвращаем false
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        // Приводим объект o к типу Coordinate и сравниваем координаты строки и столбца
        Coordinate that = (Coordinate) o;
        return row == that.row && col == that.col;
    }

    // Переопределенный метод hashCode для использования в структурах данных (например, HashMap)
    @Override public int hashCode() {
        // Генерация хэш-кода на основе строки и столбца
        return 31 * row + col;
    }
}
