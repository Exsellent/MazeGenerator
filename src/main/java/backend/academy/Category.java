package backend.academy;

import java.util.Random;

public enum Category {
    ANIMALS, FRUITS, COUNTRIES;

    private static final Random RANDOM = new Random(); // Имя изменено на UPPER_CASE

    public static Category getRandomCategory() {
        return values()[RANDOM.nextInt(values().length)];
    }
}
