package backend.academy.Maze;

import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MazeApp {
    private final MazeGenerator generator; // Генератор лабиринтов
    private final ConsoleRenderer renderer; // Рендерер для отображения лабиринта в консоли

    // Конструктор принимает объект MazeGenerator и создает новый ConsoleRenderer
    public MazeApp(MazeGenerator generator) {
        this.generator = generator;
        this.renderer = new ConsoleRenderer();
    }

    // Основной метод для запуска генерации лабиринта
    public void run() {
        // Блок try-with-resources автоматически закрывает Scanner после завершения работы
        try (Scanner scanner = new Scanner(System.in)) {
            // Получаем ширину и высоту лабиринта через метод promptForDimension
            int width = promptForDimension(scanner, "Enter the width of the maze:"); // Ввод ширины лабиринта
            int height = promptForDimension(scanner, "Enter the height of the maze:"); // Ввод высоты лабиринта

            // Генерируем лабиринт заданных размеров
            Maze maze = generator.generate(width, height);

            // Устанавливаем вход (0,0) и выход (в нижнем правом углу)
            maze.setEntrance(new Coordinate(0, 0));
            maze.setExit(new Coordinate(width - 1, height - 1));

            // Логируем и отображаем сгенерированный лабиринт
            log.info("Generated maze:");
            log.info(renderer.render(maze));

        } catch (Exception e) {
            // Логируем ошибку, если что-то пошло не так
            log.error("An error occurred during the maze generation.", e);
        } // Scanner закрывается автоматически при выходе из блока try
    }

    // Метод для запроса ввода числа у пользователя (ширина или высота лабиринта)
    private int promptForDimension(Scanner scanner, String message) {
        log.info(message); // Логируем сообщение с просьбой ввести данные
        while (!scanner.hasNextInt()) { // Проверяем, является ли введенное значение целым числом
            log.warn("Please enter a valid number."); // Если нет, выводим предупреждение
            scanner.next(); // Пропускаем некорректный ввод
        }
        return scanner.nextInt(); // Возвращаем корректное значение
    }
}
