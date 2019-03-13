import java.util.ArrayList;
import java.util.Arrays;

public class CardNames {

    // Fields
    private String[] allPossibleCardNames =
            {"dog","cat","fish","bird","mouse","bunny","cow","horse","panda","camel",
                    "elephant","koala","giraffe","hippo","rhino","monkey","gorilla","wolf","Tiger","Lion",
                    "deer","squirrel","chipmunk","dolphin","shark","whale","crab","bear","polar bear","Seal",
                    "penguin","owl"};  //32 different combinations of card values;


    // Methods

    /**
     * Selects from the allPossibleCardNames field numCards amount of random card names
     * @param numCards the number of unique card names needed for the game
     * @return an array of card names with size numCards
     */
    public String[] selectCardNames(int numCards) {
        String[] cardNames = new String[numCards];

        ArrayList<String> cardNamesCopy = new ArrayList<String>(Arrays.asList(allPossibleCardNames));

        int numSelected = 0;
        while (numSelected < numCards) {
            int index = (int)(Math.random() * cardNamesCopy.size());
            String name = cardNamesCopy.get(index);
            cardNamesCopy.remove(index);
            cardNames[numSelected] = name;
            numSelected++;
        }

        return cardNames;
    }
}
