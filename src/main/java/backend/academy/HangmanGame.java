package backend.academy;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HangmanGame {
    private static final int MAX_ATTEMPTS = 6; // Максимальное количество попыток
    private static final int MAX_INPUT_ATTEMPTS = 10; // Максимальное количество попыток ввода
    private static final int TOTAL_DIFFICULTY_LEVELS = 3; // Общее количество уровней сложности
    private static final int TOTAL_CATEGORY_OPTIONS = 3; // Общее количество категорий
    private static final String HINT_LABEL = "Hint: "; // Константа для подсказки

    private final WordBank wordBank; // Источник слов
    private final InputReader inputReader; // Чтение пользовательского ввода
    private final OutputWriter outputWriter; // Вывод сообщений пользователю
    private final HangmanDisplay display = new HangmanDisplay(); // Отображение виселицы
    private String hint; // Подсказка для текущего слова

    public HangmanGame(WordBank wordBank, InputReader inputReader, OutputWriter outputWriter) {
        this.wordBank = wordBank;
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    public void startGame() {
        try {
            // Выбор категории и уровня сложности
            Category category = selectCategory();
            Difficulty difficulty = selectDifficulty();
            Map.Entry<String, String> wordAndHint = wordBank.selectWordAndHint(category, difficulty); // Выбор слова и
                                                                                                      // подсказки

            String word = wordAndHint.getKey(); // Загаданное слово
            this.hint = wordAndHint.getValue(); // Подсказка

            outputWriter.println(HINT_LABEL + hint); // Печать подсказки
            playGame(word); // Начало игры
        } catch (IOException e) {
            outputWriter.println("An error occurred: " + e.getMessage()); // Обработка ошибок
        }
    }

    private Category selectCategory() throws IOException {
        outputWriter.println("Select a category or press Enter for random:");
        outputWriter.println("1. Animals");
        outputWriter.println("2. Fruits");
        outputWriter.println("3. Countries");

        String input = inputReader.readLine();
        if (input.isEmpty()) {
            return Category.getRandomCategory();
        }

        int choice = Integer.parseInt(input);
        return switch (choice) {
        case 1 -> Category.ANIMALS;
        case 2 -> Category.FRUITS;
        case TOTAL_CATEGORY_OPTIONS -> Category.COUNTRIES; // Используем константу TOTAL_CATEGORY_OPTIONS
        default -> Category.getRandomCategory();
        };
    }

    private Difficulty selectDifficulty() throws IOException {
        outputWriter.println("Select a difficulty level or press Enter for random:");
        outputWriter.println("1. Easy");
        outputWriter.println("2. Medium");
        outputWriter.println("3. Hard");

        String input = inputReader.readLine();
        if (input.isEmpty()) {
            return Difficulty.getRandomDifficulty();
        }

        int choice = Integer.parseInt(input);
        return switch (choice) {
        case 1 -> Difficulty.EASY;
        case 2 -> Difficulty.MEDIUM;
        case TOTAL_DIFFICULTY_LEVELS -> Difficulty.HARD; // Используем константу TOTAL_DIFFICULTY_LEVELS
        default -> Difficulty.getRandomDifficulty();
        };
    }

    private void playGame(String word) throws IOException {
        int attempts = 0;
        Set<Character> guessedLetters = new HashSet<>();

        outputWriter.println("The word has " + word.length() + " letters. You have " + MAX_ATTEMPTS + " attempts.");

        while (attempts < MAX_ATTEMPTS && !isWordGuessed(word, guessedLetters)) {
            display.updateDisplay(word, guessedLetters, outputWriter); // Обновление отображения слова
            display.drawHangman(attempts, outputWriter); // Рисование виселицы

            char guessedLetter = getValidLetter(guessedLetters); // Получение валидной буквы

            if (!word.contains(String.valueOf(guessedLetter))) {
                attempts++;
                outputWriter.println("Incorrect guess! Attempts left: " + (MAX_ATTEMPTS - attempts));
            } else {
                outputWriter.println("Correct guess!");
            }
            guessedLetters.add(guessedLetter);
        }

        display.updateDisplay(word, guessedLetters, outputWriter);
        display.drawHangman(attempts, outputWriter);

        if (isWordGuessed(word, guessedLetters)) {
            outputWriter.println("Congratulations! You guessed the word: " + word);
        } else {
            outputWriter.println("Sorry, you've been hanged. The word was: " + word);
        }
    }

    private char getValidLetter(Set<Character> guessedLetters) throws IOException {
        int inputAttempts = 0;
        while (inputAttempts < MAX_INPUT_ATTEMPTS) {
            outputWriter.print("Enter a letter (or type 'hint' for a hint): ");
            String input = inputReader.readLine().toLowerCase();

            if (input.equals("hint")) {
                outputWriter.println(HINT_LABEL + hint);
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
                return guessedLetter;
            }
            inputAttempts++;
        }
        throw new IOException("Max input attempts reached");
    }

    private boolean isWordGuessed(String word, Set<Character> guessedLetters) {
        return word.chars().allMatch(c -> guessedLetters.contains((char) c));
    }
}
