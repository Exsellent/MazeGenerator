package backend.academy;

import java.util.Random;

public enum Difficulty {
    EASY, MEDIUM, HARD;

    private static final Random RANDOM = new Random(); // The name of the constant using UPPER_CASE

    public static Difficulty getRandomDifficulty() {
        return values()[RANDOM.nextInt(values().length)];
    }
}
