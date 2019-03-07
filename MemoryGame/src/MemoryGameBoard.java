import java.util.ArrayList;
import java.util.Arrays;

public class MemoryGameBoard {

    public static Player playerOne = new Player();
    public static Player playerTwo = new Player();

    public static Card[][] gameGrid;

    private static GridSizes gridSizes = new GridSizes();
    private static CardNames cardNames = new CardNames();

    private static int currentTurn = 0;  //player one goes first
    private static int rows;
    private static int cols;
    private static int numCards;

    public MemoryGameBoard() { }

    public static void chooseGridSize() {
        //these should be selected using the GUI, 0 is a filler value
        int rowDim = 0;
        int colDim = 0;

        rows = gridSizes.selectRowSize(rowDim);
        cols = gridSizes.selectColSize(colDim);
        numCards = rows * cols / 2;
    }

    public static void setGameGrid() {
        gameGrid = new Card[rows][cols];

        ArrayList<String> cardNamesChosen = new ArrayList<String>(Arrays.asList(cardNames.selectCardNames(numCards)));

        int numSelected = 0;
        while (numSelected < numCards) {
            int index = (int)(Math.random() * cardNamesChosen.size());
            String name = cardNamesChosen.get(index);

            int cardsSelected = 0;
            while (cardsSelected < 2) {
                int rowIndex = (int)(Math.random() * rows);
                int colIndex = (int)(Math.random() * cols);
                if (gameGrid[rowIndex][colIndex] == null) {
                    gameGrid[rowIndex][colIndex] = new Card(name,rowIndex,colIndex);
                    cardsSelected++;
                }
            }
            cardNamesChosen.remove(index);
            numSelected++;
        }
    }

    public static void changeTurn() {
        if (currentTurn == 0) {
            currentTurn = 1;
        }
        else {
            currentTurn = 0;
        }
    }

    public static void removeCards(Card card1, Card card2) {
        //setting element to null indicates the card no longer exists, should no longer be displayed in GUI
        gameGrid[card1.getRow()][card1.getCol()] = null;
        gameGrid[card2.getRow()][card2.getRow()] = null;
        numCards -= 2;
    }

    public static boolean findMatch(Card card1, Card card2) {
        if (card1.getName().equals(card2.getName())) {
            removeCards(card1,card2);
            return true;
        }
        return false;
    }

    public static String getWinner() {
        if (playerOne.getScore() > playerTwo.getScore()) {
            return "PLAYER ONE";
        }
        else if (playerTwo.getScore() > playerOne.getScore()) {
            return "PLAYER TWO";
        }
        else {
            return "TIE";
        }
    }


}
