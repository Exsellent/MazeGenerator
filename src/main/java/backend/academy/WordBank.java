package backend.academy;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WordBank {
    private static final Map<Category, Map<Difficulty, Map<String, String>>> WORDS = new EnumMap<>(Category.class);

    private static final String DESCRIPTION_NORTH_AMERICA = "A country in North America.";
    private static final String DESCRIPTION_SOUTH_AMERICA = "A country in South America.";

    static {
        for (Category category : Category.values()) {
            WORDS.put(category, new EnumMap<>(Difficulty.class));
            for (Difficulty difficulty : Difficulty.values()) {
                WORDS.get(category).put(difficulty, new HashMap<>());
            }
        }

        // Animals
        WORDS.get(Category.ANIMALS).get(Difficulty.EASY).put("cat", "A small domesticated carnivorous mammal.");
        WORDS.get(Category.ANIMALS).get(Difficulty.EASY).put("dog", "A domesticated carnivorous mammal.");
        WORDS.get(Category.ANIMALS).get(Difficulty.EASY).put("cow", "A large domesticated ungulate.");
        WORDS.get(Category.ANIMALS).get(Difficulty.MEDIUM).put("elephant", "A large mammal with a trunk.");
        WORDS.get(Category.ANIMALS).get(Difficulty.MEDIUM).put("giraffe", "An African animal with a long neck.");
        WORDS.get(Category.ANIMALS).get(Difficulty.MEDIUM).put("dolphin", "A marine mammal.");
        WORDS.get(Category.ANIMALS).get(Difficulty.HARD).put("hippopotamus", "A large thick-skinned mammal.");
        WORDS.get(Category.ANIMALS).get(Difficulty.HARD).put("chimpanzee", "A great ape.");
        WORDS.get(Category.ANIMALS).get(Difficulty.HARD).put("rhinoceros", "A large animal with a horn on its nose.");

        // Fruits
        WORDS.get(Category.FRUITS).get(Difficulty.EASY).put("apple", "A common fruit, often red or green.");
        WORDS.get(Category.FRUITS).get(Difficulty.EASY).put("pear", "A sweet fruit with a round base.");
        WORDS.get(Category.FRUITS).get(Difficulty.EASY).put("plum", "A small dark fruit.");
        WORDS.get(Category.FRUITS).get(Difficulty.MEDIUM).put("banana", "The most popular fruit is yellow.");
        WORDS.get(Category.FRUITS).get(Difficulty.MEDIUM).put("grape", "A small red or dark blue fruit.");
        WORDS.get(Category.FRUITS).get(Difficulty.MEDIUM).put("mango", "A large, yellow fruit.");
        WORDS.get(Category.FRUITS).get(Difficulty.HARD).put("pomegranate", "A fruit with many seeds.");
        WORDS.get(Category.FRUITS).get(Difficulty.HARD).put("watermelon",
                "A large fruit with a green rind and red flesh.");
        WORDS.get(Category.FRUITS).get(Difficulty.HARD).put("pineapple", "A tropical fruit with a spiky exterior.");

        // Countries
        WORDS.get(Category.COUNTRIES).get(Difficulty.EASY).put("usa", DESCRIPTION_NORTH_AMERICA);
        WORDS.get(Category.COUNTRIES).get(Difficulty.EASY).put("india", "A country in South Asia.");
        WORDS.get(Category.COUNTRIES).get(Difficulty.EASY).put("china", "A country in East Asia.");
        WORDS.get(Category.COUNTRIES).get(Difficulty.MEDIUM).put("germany", "A country in Central Europe.");
        WORDS.get(Category.COUNTRIES).get(Difficulty.MEDIUM).put("brazil", DESCRIPTION_SOUTH_AMERICA);
        WORDS.get(Category.COUNTRIES).get(Difficulty.MEDIUM).put("canada", DESCRIPTION_NORTH_AMERICA);
        WORDS.get(Category.COUNTRIES).get(Difficulty.HARD).put("australia",
                "A country and continent in the Southern Hemisphere.");
        WORDS.get(Category.COUNTRIES).get(Difficulty.HARD).put("argentina", DESCRIPTION_SOUTH_AMERICA);
        WORDS.get(Category.COUNTRIES).get(Difficulty.HARD).put("philippines", "A country in Southeast Asia.");
    }

    public Entry<String, String> selectWordAndHint(Category category, Difficulty difficulty) {
        Map<String, String> wordList = WORDS.get(category).get(difficulty);
        List<Entry<String, String>> entries = new ArrayList<>(wordList.entrySet());
        return entries.get(new Random().nextInt(entries.size()));
    }

    public String getRandomWord() {
        return null; // Implement as needed
    }

    public Entry<String, String> getRandomWordAndHint() {
        // Example implementation: you should replace this with your actual logic
        return selectWordAndHint(Category.ANIMALS, Difficulty.EASY); // Replace with actual category and difficulty
    }
}
