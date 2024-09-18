package backend.academy;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WordBank {
    private static final String NORTH_AMERICA = "A country in North America.";
    private static final String SOUTH_AMERICA = "A country in South America.";
    private final Map<Category, CategoryWords> categoryWords;

    public WordBank() {
        categoryWords = new EnumMap<>(Category.class);
        initializeWords();
    }

    private void initializeWords() {
        CategoryWords animals = new CategoryWords();
        animals.addWord(new Word("cat", "A small domesticated carnivorous mammal.", Difficulty.EASY));
        animals.addWord(new Word("dog", "A domesticated carnivorous mammal.", Difficulty.EASY));
        animals.addWord(new Word("cow", "A large domesticated ungulate.", Difficulty.EASY));
        animals.addWord(new Word("elephant", "A large mammal with a trunk.", Difficulty.MEDIUM));
        animals.addWord(new Word("giraffe", "An African animal with a long neck.", Difficulty.MEDIUM));
        animals.addWord(new Word("dolphin", "A marine mammal.", Difficulty.MEDIUM));
        animals.addWord(new Word("hippopotamus", "A large thick-skinned mammal.", Difficulty.HARD));
        animals.addWord(new Word("chimpanzee", "A great ape.", Difficulty.HARD));
        animals.addWord(new Word("rhinoceros", "A large animal with a horn on its nose.", Difficulty.HARD));
        categoryWords.put(Category.ANIMALS, animals);

        CategoryWords fruits = new CategoryWords();
        fruits.addWord(new Word("apple", "A common fruit, often red or green.", Difficulty.EASY));
        fruits.addWord(new Word("pear", "A sweet fruit with a round base.", Difficulty.EASY));
        fruits.addWord(new Word("plum", "A small dark fruit.", Difficulty.EASY));
        fruits.addWord(new Word("banana", "The most popular fruit is yellow.", Difficulty.MEDIUM));
        fruits.addWord(new Word("grape", "A small red or dark blue fruit.", Difficulty.MEDIUM));
        fruits.addWord(new Word("mango", "A large, yellow fruit.", Difficulty.MEDIUM));
        fruits.addWord(new Word("pomegranate", "A fruit with many seeds.", Difficulty.HARD));
        fruits.addWord(new Word("watermelon", "A large fruit with a green rind and red flesh.", Difficulty.HARD));
        fruits.addWord(new Word("pineapple", "A tropical fruit with a spiky exterior.", Difficulty.HARD));
        categoryWords.put(Category.FRUITS, fruits);

        CategoryWords countries = new CategoryWords();
        countries.addWord(new Word("usa", NORTH_AMERICA, Difficulty.EASY));
        countries.addWord(new Word("india", "A country in South Asia.", Difficulty.EASY));
        countries.addWord(new Word("china", "A country in East Asia.", Difficulty.EASY));
        countries.addWord(new Word("germany", "A country in Central Europe.", Difficulty.MEDIUM));
        countries.addWord(new Word("brazil", SOUTH_AMERICA, Difficulty.MEDIUM));
        countries.addWord(new Word("canada", NORTH_AMERICA, Difficulty.MEDIUM));
        countries
                .addWord(new Word("australia", "A country and continent in the Southern Hemisphere.", Difficulty.HARD));
        countries.addWord(new Word("argentina", SOUTH_AMERICA, Difficulty.HARD));
        countries.addWord(new Word("philippines", "A country in Southeast Asia.", Difficulty.HARD));
        categoryWords.put(Category.COUNTRIES, countries);
    }

    public Word selectWord(Category category, Difficulty difficulty) {
        return categoryWords.get(category).getRandomWord(difficulty);
    }

    public Word getRandomWord() {
        List<Category> categories = new ArrayList<>(categoryWords.keySet());
        Category randomCategory = categories.get(new Random().nextInt(categories.size()));
        return categoryWords.get(randomCategory).getRandomWord();
    }
}
