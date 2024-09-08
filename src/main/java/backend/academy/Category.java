package backend.academy;

import java.util.Random;

public enum Category {
    ANIMALS, FRUITS, COUNTRIES;

    private static final Random random = new Random();

    public static Category getRandomCategory() {
        return values()[random.nextInt(values().length)];
    }
}
