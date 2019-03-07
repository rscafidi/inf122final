import java.util.ArrayList;
import java.util.Arrays;

public class CardNames {

    // Fields
    private String[] allPossibleCardNames =
            {"Dog", "Cat", "Fish", "Bird", "Mouse", "Bunny", "Cow", "Horse",
             "Tree", "Rock", "Dinosaur", "Tiger", "Lion", "Jaguar", "Giraffe", "Rhino",
             "Cookie", "Donut", "Burger", "Hot Dog", "Soda", "Popcorn", "Sandwich", "Rice",
             "Phone", "Computer", "Laptop", "Watch", "Clock", "Poster", "Basketball", "Baseball" };  //should be 32 different values but got lazy


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
