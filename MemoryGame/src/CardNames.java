import java.util.ArrayList;
import java.util.Arrays;

public class CardNames {

    // Fields
    private String[] allPossibleCardNames =
            {"dog","cat","fish","bird","mouse","bunny","cow","horse","panda","camel",
                    "elephant","koala","giraffe","hippo","rhino","monkey","gorilla","wolf","Tiger","Lion",
                    "deer","squirrel","chipmunk","dolphin","shark","whale","crab","bear","polar bear","Seal",
                    "penguin","owl"};  //32 different combinations of card values;  //should be 32 different values but got lazy


    // Constructors
    public CardNames() {}


    // Methods
    /**
     * Returns the allPossibleCardNames array field
     * @return allPossibleCardNames
     */
    public String[] getAllPossibleCardNames() {
        return allPossibleCardNames;
    }
}
