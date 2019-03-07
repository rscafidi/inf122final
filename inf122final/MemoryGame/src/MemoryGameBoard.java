import java.util.ArrayList;
import java.util.Arrays;

public class MemoryGameBoard {

    // Fields
    public static Player playerOne = new Player();
    public static Player playerTwo = new Player();

    public static Card[][] gameGrid;

    private static GridSizes gridSizes = new GridSizes();
    private static CardNames cardNames = new CardNames();

    private static int currentTurn = 0;  //player one goes first
    private static int rows;
    private static int cols;
    private static int numCards;


    // Constructors
    public MemoryGameBoard() { }


    // Methods

    /**
     * Gets the user to select the grid size and sets it as the game board size
     */
    public static void chooseGridSize() {
        //these should be selected using the GUI, 0 is a filler value
        int rowDim = 0;
        int colDim = 0;

        rows = gridSizes.selectRowSize(rowDim);
        cols = gridSizes.selectColSize(colDim);
        numCards = rows * cols / 2;
    }

    /**
     * Takes the row and col fields and sets up the game board with Card objects for play
     */
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

    /**
     * Changes the player's turns
     * 0 - playerOne's turn
     * 1 - playerTwo's turn
     */
    public static void changeTurn() {
        if (currentTurn == 0) {
            currentTurn = 1;
        }
        else {
            currentTurn = 0;
        }
    }

    /**
     * Gets the two card's locations and sets those cards as null to represent that they've already been matched and
     * are now unselectable
     * @param card1 the first card to be set as null
     * @param card2 the second card to be set as null
     */
    public static void removeCards(Card card1, Card card2) {
        //setting element to null indicates the card no longer exists, should no longer be displayed in GUI
        gameGrid[card1.getRow()][card1.getCol()] = null;
        gameGrid[card2.getRow()][card2.getRow()] = null;
        numCards -= 2;
    }

    /**
     * Checks to see if the two cards are matching
     * @param card1 the first card the player selected
     * @param card2 the second card the player selected
     * @return true if cards match and false if cards don't match
     */
    public static boolean findMatch(Card card1, Card card2) {
        if (card1.getName().equals(card2.getName())) {
            removeCards(card1,card2);
            return true;
        }
        return false;
    }

    /**
     * Determines the winner of the game based on the players' scores
     * @return a string stating the winner of the match, or if it's a tie
     */
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
