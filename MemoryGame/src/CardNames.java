import java.util.ArrayList;
import java.util.Arrays;

public class CardNames {

    private String[] allPossibleCardNames =
            {"Dog","Cat","Fish","Bird","Mouse","Bunny","Cow","Horse"};  //should be 32 different values but got lazy

    public CardNames() {}

    public String[] getAllPossibleCardNames() {
        return allPossibleCardNames;
    }

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
