package backend.academy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@ToString
@RequiredArgsConstructor
public class HangmanGame {
    private static final int MAX_ATTEMPTS = 6;  // Максимальное количество попыток
    private static final int MAX_INPUT_ATTEMPTS = 10;  // Максимальное количество попыток ввода
    private final WordBank wordBank;  // Источник слов
    private final InputReader inputReader;  // Чтение пользовательского ввода
    private final OutputWriter outputWriter;  // Вывод сообщений пользователю
    private final HangmanDisplay display = new HangmanDisplay();  // Отображение виселицы
    private String hint;  // Подсказка для текущего слова

    /**
     * Метод запуска игры: выбор категории, сложности и начало процесса игры.
     */
    public void startGame() {
        try {
            Category category = selectCategory();  // Выбор категории
            Difficulty difficulty = selectDifficulty();  // Выбор сложности
            Map.Entry<String, String> wordAndHint = wordBank.selectWordAndHint(category, difficulty);  // Выбор слова и подсказки
            String word = wordAndHint.getKey();  // Загаданное слово
            this.hint = wordAndHint.getValue();  // Подсказка

            outputWriter.println("Hint: " + hint);  // Печать подсказки
            playGame(word);  // Начало игры
        } catch (IOException e) {
            outputWriter.println("An error occurred: " + e.getMessage());  // Обработка ошибок
        }
    }

    /**
     * Метод выбора категории с пользовательским вводом.
     */
    private Category selectCategory() throws IOException {
        outputWriter.println("Select a category or press Enter for random:");
        outputWriter.println("1. Animals");
        outputWriter.println("2. Fruits");
        outputWriter.println("3. Countries");

        String input = inputReader.readLine();  // Чтение ввода пользователя
        if (input.isEmpty()) {
            return Category.getRandomCategory();  // Если ввод пустой, выбирается случайная категория
        }

        int choice = Integer.parseInt(input);
        return switch (choice) {
            case 1 -> Category.ANIMALS;
            case 2 -> Category.FRUITS;
            case 3 -> Category.COUNTRIES;
            default -> Category.getRandomCategory();
        };
    }

    /**
     * Метод выбора уровня сложности с пользовательским вводом.
     */
    private Difficulty selectDifficulty() throws IOException {
        outputWriter.println("Select a difficulty level or press Enter for random:");
        outputWriter.println("1. Easy");
        outputWriter.println("2. Medium");
        outputWriter.println("3. Hard");

        String input = inputReader.readLine();  // Чтение ввода пользователя
        if (input.isEmpty()) {
            return Difficulty.getRandomDifficulty();  // Если ввод пустой, выбирается случайный уровень
        }

        int choice = Integer.parseInt(input);
        return switch (choice) {
            case 1 -> Difficulty.EASY;
            case 2 -> Difficulty.MEDIUM;
            case 3 -> Difficulty.HARD;
            default -> Difficulty.getRandomDifficulty();
        };
    }

    /**
     * Основной метод игры, который обрабатывает процесс угадывания слова.
     */
    private void playGame(String word) throws IOException {
        int attempts = 0;  // Количество попыток
        Set<Character> guessedLetters = new HashSet<>();  // Угаданные буквы

        outputWriter.println("The word has " + word.length() + " letters. You have " + MAX_ATTEMPTS + " attempts.");

        while (attempts < MAX_ATTEMPTS && !isWordGuessed(word, guessedLetters)) {
            display.updateDisplay(word, guessedLetters, outputWriter);  // Обновление отображения слова
            display.drawHangman(attempts, outputWriter);  // Рисование виселицы

            char guessedLetter = getValidLetter(guessedLetters);  // Получение валидной буквы

            if (!word.contains(String.valueOf(guessedLetter))) {
                attempts++;
                outputWriter.println("Incorrect guess! Attempts left: " + (MAX_ATTEMPTS - attempts));
            } else {
                outputWriter.println("Correct guess!");
            }
            guessedLetters.add(guessedLetter);  // Добавление буквы в список угаданных
        }

        display.updateDisplay(word, guessedLetters, outputWriter);
        display.drawHangman(attempts, outputWriter);

        if (isWordGuessed(word, guessedLetters)) {
            outputWriter.println("Congratulations! You guessed the word: " + word);
        } else {
            outputWriter.println("Sorry, you've been hanged. The word was: " + word);
        }
    }

    /**
     * Метод для получения валидной буквы от пользователя с ограничением на количество попыток ввода.
     */
    private char getValidLetter(Set<Character> guessedLetters) throws IOException {
        int inputAttempts = 0;
        while (inputAttempts < MAX_INPUT_ATTEMPTS) {
            outputWriter.print("Enter a letter (or type 'hint' for a hint): ");
            String input = inputReader.readLine().toLowerCase();

            if (input.equals("hint")) {
                outputWriter.println("Hint: " + hint);
                continue;
            }

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                outputWriter.println("Invalid input. Please enter a single letter.");
                continue;
            }

            char guessedLetter = input.charAt(0);
            if (guessedLetters.contains(guessedLetter)) {
                outputWriter.println("You've already guessed this letter. Try again.");
            } else {
                return guessedLetter;  // Возврат валидной буквы
            }
            inputAttempts++;
        }
        throw new IOException("Max input attempts reached");
    }

    /**
     * Проверка, угадано ли слово.
     */
    private boolean isWordGuessed(String word, Set<Character> guessedLetters) {
        return word.chars().allMatch(c -> guessedLetters.contains((char) c));  // Проверка всех символов в слове
    }
}
