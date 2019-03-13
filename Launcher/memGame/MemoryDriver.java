package memGame;

import GameEnvironment.*;

import java.io.IOException;

public class MemoryDriver extends GameDriver{

    // Fields

    public MemoryPlayer[] memoryPlayers;
    public MemoryBoardGame memoryBoardGame;
    public DrawMemoryDriver drawMemoryDriver;
   // public int currentTurn = 0;  //player one goes first


    public MemoryDriver(String player1Name, String player2Name, int rows, int cols, String gameName) throws IOException {
        super(player1Name, player2Name, rows, cols, gameName);
        this.memoryPlayers = new MemoryPlayer[]{new MemoryPlayer(player1Name, 0), new MemoryPlayer(player2Name, 1)};
        this.currentPlayer = 0;
        //players = new Player[]{new Player(player1Name, 0), new Player(player2Name, 1)};
        //boardArray = new GamePiece[rows][cols];
    }
    // Methods

    public void initializeBoardArray() {
        memoryBoardGame = new MemoryBoardGame();
        memoryBoardGame.initializeGrid();
        drawMemoryDriver = new DrawMemoryDriver();
    }

    /**
//     * Changes the player's turns
//     * 0 - playerOne's turn
//     * 1 - playerTwo's turn
//     */
//    public static void switchTurn() {
//        if (currentTurn == 0) {
//            currentTurn = 1;
//        }
//        else {
//            currentTurn = 0;
//        }
//    }


    public MemoryPlayer getPlayer() {
        return memoryPlayers[currentPlayer];
    }

    public void drawGameMenu() {

    }


    public boolean isLegalMove(int row, int col) {
        if (memoryBoardGame.grid[row][col].getStatus() && !memoryBoardGame.grid[row][col].getFlipped()) {
            return true;
        }
        return false;
    }

    public boolean firstMove() {
        if (getPlayer().getPlayerCard() == null) {
            return true;
        }
        return false;
    }

    public void makeMove(int row, int col) {
        Card card = memoryBoardGame.grid[row][col];
        MemoryPlayer player = getPlayer();

        memoryBoardGame.flipCards(row,col);

        if (firstMove()) {
            player.addPlayerCard(card);
        }
        else {
            Card otherCard = player.getPlayerCard();

            if (otherCard.getName().equals(card.getName())) {
                memoryBoardGame.updateCards(row,col);
                memoryBoardGame.updateCards(otherCard.getRow(),otherCard.getCol());
                memoryBoardGame.numMatches--;
                updateScore();
            }
            else {
                memoryBoardGame.updateCards(row,col);
                memoryBoardGame.updateCards(otherCard.getRow(),otherCard.getCol());
            }

            player.resetPlayerCard();
            switchTurn();
        }
    }

    /**
     * Determines the winner of the game based on the players' scores
     * @return a string stating the winner of the match, or if it's a tie
     */
    public String getWinner() {
        if (memoryPlayers[0].getScore() > memoryPlayers[1].getScore()) {
            return "PLAYER ONE";
        }
        else if (memoryPlayers[0].getScore() > memoryPlayers[1].getScore()) {
            return "PLAYER TWO";
        }
        else {
            return "TIE";
        }
    }

    public void updateScore() {
        memoryPlayers[currentPlayer].incrementScore();
    }

    public boolean isGameOver() {
        if (memoryBoardGame.numMatches == 0) {
            return true;
        }
        return false;
    }

    public void runGame() {
        initializeBoardArray();
        if (boardGUI.boardClicked) {
            Card clickedCard = memoryBoardGame.grid[boardGUI.rowClicked][boardGUI.colClicked];
            if (isLegalMove(boardGUI.rowClicked,boardGUI.colClicked)) {
                if (firstMove()) {
                    makeMove(boardGUI.rowClicked,boardGUI.colClicked);
                    drawMemoryDriver.flipUp(clickedCard.getImageView(), () -> {});
                }
                else {
                    Card otherCard = getPlayer().getPlayerCard();
                    makeMove(boardGUI.rowClicked,boardGUI.colClicked);
                    drawMemoryDriver.flipUp(clickedCard.getImageView(), () -> {
                        if (clickedCard.getStatus()) {
                            drawMemoryDriver.flipDown(clickedCard.getImageView());
                            drawMemoryDriver.flipDown(otherCard.getImageView());
                        }
                    });
                    if (isGameOver()) {
                        System.out.println(getWinner());   //should put this on the menu somehow but not sure if there's a menu yet :(
                    }
                }
            }
        }
    }


}
