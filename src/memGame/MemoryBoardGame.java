package memGame;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MemoryBoardGame {

    public Card[][] grid;
    public CardNames cardNames = new CardNames();
    public int numMatches;

    public void initializeGrid() {
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
                    String imgURL = "/resources/" + name + ".png";
                    grid[rowIndex][colIndex] = new Card(name,new Image("/resources/" + name + ".png",100,85,false,false), rowIndex,colIndex);
                    cardsSelected++;
                }
            }
            cardNamesChosen.remove(index);
            numSelected++;
        }

    }

    public void flipCards(int row, int col) {
        grid[row][col].flip();
    }

    public void updateCards(int row, int col) {
        grid[row][col].changeStatus();
    }



}
