import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MemoryBoardGame {

    public static Card[][] grid;
    public static CardNames cardNames = new CardNames();
    public static int numMatches;

    public static void initializeGrid() {
        grid = new Card[6][6];
        numMatches = 18;

        ArrayList<String> cardNamesChosen = new ArrayList<String>(Arrays.asList(cardNames.selectCardNames(numMatches)));

        int numSelected = 0;
        while (numSelected < numMatches) {
            int index = (int)(Math.random() * cardNamesChosen.size());
            String name = cardNamesChosen.get(index);

            int cardsSelected = 0;
            while (cardsSelected < 2) {
                int rowIndex = (int)(Math.random() * 6);
                int colIndex = (int)(Math.random() * 6);
                if (grid[rowIndex][colIndex] == null) {
                    String imgURL = "Resources/" + name + ".png";
                    grid[rowIndex][colIndex] = new Card(name,new Image(new File(imgURL).toURI().toString(),75,75,true,false), rowIndex,colIndex);
                    cardsSelected++;
                }
            }
            cardNamesChosen.remove(index);
            numSelected++;
        }

    }

    public static void flipCards(int row, int col) {
        grid[row][col].flip();
    }

    public static void updateCards(int row, int col) {
        grid[row][col].changeStatus();
    }

}
