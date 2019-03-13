import java.io.IOException;

public class MemoryDriver {

    // Fields
    public static MemoryPlayer[] players = new MemoryPlayer[2];
    public static int currentTurn = 0;  //player one goes first
    public static BoardGame boardGUI;


    // Methods
    /**
     * Changes the player's turns
     * 0 - playerOne's turn
     * 1 - playerTwo's turn
     */
    public static void switchTurn() {
        if (currentTurn == 0) {
            currentTurn = 1;
        }
        else {
            currentTurn = 0;
        }
    }

    public static MemoryPlayer getPlayer(int playerIndex) {
        return players[playerIndex];
    }

    public static void addPlayer() {}

    public static void drawGameMenu() {

    }


    public static boolean checkValidMove(int row, int col) {
        if (MemoryBoardGame.grid[row][col].getStatus()) {
            return true;
        }
        return false;
    }

    public static void makeMove(int row, int col) {
        Card card = MemoryBoardGame.grid[row][col];
        MemoryPlayer player = getPlayer(currentTurn);

        MemoryBoardGame.flipCards(row,col);

        if (player.getPlayerCard() == null) {
            player.addPlayerCard(card);
        }
        else {
            Card otherCard = player.getPlayerCard();

            if (otherCard.getName().equals(card.getName())) {
                MemoryBoardGame.updateCards(row,col);
                MemoryBoardGame.updateCards(otherCard.getRow(),otherCard.getCol());
                MemoryBoardGame.numMatches--;
                player.incrementScore();

                if (MemoryBoardGame.numMatches == 0) {
                    getWinner();
                }
            }
            else {
                MemoryBoardGame.updateCards(row,col);
                MemoryBoardGame.updateCards(otherCard.getRow(),otherCard.getCol());
            }

            player.resetPlayerCard();
            switchTurn();
        }
    }

    /**
     * Determines the winner of the game based on the players' scores
     * @return a string stating the winner of the match, or if it's a tie
     */
    public static String getWinner() {
        if (players[0].getScore() > players[1].getScore()) {
            return "PLAYER ONE";
        }
        else if (players[0].getScore() > players[1].getScore()) {
            return "PLAYER TWO";
        }
        else {
            return "TIE";
        }
    }

    public static void runGame() {
        MemoryBoardGame.initializeGrid();
//        try {
//            boardGUI = new BoardGame(6,6,"Memory");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}
