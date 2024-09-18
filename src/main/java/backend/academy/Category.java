package backend.academy;

import java.util.Random;

public enum Category {
    ANIMALS, FRUITS, COUNTRIES;

    private static final Random RANDOM = new Random();

    public static Category getRandomCategory() {
        return values()[RANDOM.nextInt(values().length)];
    }
}
