package backend.academy;

import java.util.Random;

public enum Difficulty {
    EASY, MEDIUM, HARD;

    private static final Random random = new Random();

    public static Difficulty getRandomDifficulty() {
        return values()[random.nextInt(values().length)];
    }
}
