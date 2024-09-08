package backend.academy;

import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
public class WordBank {
    private static final Map<Category, Map<Difficulty, Map<String, String>>> words = new EnumMap<>(Category.class);

    static {
        for (Category category : Category.values()) {
            words.put(category, new EnumMap<>(Difficulty.class));
            for (Difficulty difficulty : Difficulty.values()) {
                words.get(category).put(difficulty, new HashMap<>());
            }
        }

        // Добавление слов с подсказками для категории "Животные"
        words.get(Category.ANIMALS).get(Difficulty.EASY).put("cat", "A small domesticated carnivorous mammal.");
        words.get(Category.ANIMALS).get(Difficulty.EASY).put("dog", "A domesticated carnivorous mammal.");
        words.get(Category.ANIMALS).get(Difficulty.EASY).put("cow", "A large domesticated ungulate.");
        words.get(Category.ANIMALS).get(Difficulty.MEDIUM).put("elephant", "A large mammal with a trunk.");
        words.get(Category.ANIMALS).get(Difficulty.MEDIUM).put("giraffe", "An African animal with a long neck.");
        words.get(Category.ANIMALS).get(Difficulty.MEDIUM).put("dolphin", "A marine mammal.");
        words.get(Category.ANIMALS).get(Difficulty.HARD).put("hippopotamus", "A large thick-skinned mammal.");
        words.get(Category.ANIMALS).get(Difficulty.HARD).put("chimpanzee", "A great ape.");
        words.get(Category.ANIMALS).get(Difficulty.HARD).put("rhinoceros", "A large animal with a horn on its nose.");

        // Добавление слов с подсказками для категории "Фрукты"
        words.get(Category.FRUITS).get(Difficulty.EASY).put("apple", "A common fruit, often red or green.");
        words.get(Category.FRUITS).get(Difficulty.EASY).put("pear", "A sweet fruit with a round base.");
        words.get(Category.FRUITS).get(Difficulty.EASY).put("plum", "A small dark fruit.");
        words.get(Category.FRUITS).get(Difficulty.MEDIUM).put("banana", "The most popular fruit is yellow.");
        words.get(Category.FRUITS).get(Difficulty.MEDIUM).put("grape", "A small red or dark blue fruit.");
        words.get(Category.FRUITS).get(Difficulty.MEDIUM).put("mango", "A large, yellow fruit.");
        words.get(Category.FRUITS).get(Difficulty.HARD).put("pomegranate", "A fruit with many seeds.");
        words.get(Category.FRUITS).get(Difficulty.HARD).put("watermelon", "A large fruit with a green rind and red flesh.");
        words.get(Category.FRUITS).get(Difficulty.HARD).put("pineapple", "A tropical fruit with a spiky exterior.");

        // Добавление слов с подсказками для категории "Страны"
        words.get(Category.COUNTRIES).get(Difficulty.EASY).put("usa", "A country in North America.");
        words.get(Category.COUNTRIES).get(Difficulty.EASY).put("india", "A country in South Asia.");
        words.get(Category.COUNTRIES).get(Difficulty.EASY).put("china", "A country in East Asia.");
        words.get(Category.COUNTRIES).get(Difficulty.MEDIUM).put("germany", "A country in Central Europe.");
        words.get(Category.COUNTRIES).get(Difficulty.MEDIUM).put("brazil", "A country in South America.");
        words.get(Category.COUNTRIES).get(Difficulty.MEDIUM).put("canada", "A country in North America.");
        words.get(Category.COUNTRIES).get(Difficulty.HARD).put("australia", "A country and continent in the Southern Hemisphere.");
        words.get(Category.COUNTRIES).get(Difficulty.HARD).put("argentina", "A country in South America.");
        words.get(Category.COUNTRIES).get(Difficulty.HARD).put("philippines", "A country in Southeast Asia.");
    }

    // Метод выбора слова и возвращения слова и подсказки как пары
    public Map.Entry<String, String> selectWordAndHint(Category category, Difficulty difficulty) {
        Map<String, String> wordList = words.get(category).get(difficulty);
        List<Map.Entry<String, String>> entries = new ArrayList<>(wordList.entrySet());
        return entries.get(new Random().nextInt(entries.size()));  // Возвращает случайную пару (слово, подсказка)
    }
}
