package memGame;

import GameEnvironment.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MemoryDriver extends GameDriver{

    // Fields

    public MemoryPlayer[] memoryPlayers;
    public MemoryBoardGame memoryBoardGame;
   // public int currentTurn = 0;  //player one goes first


    public MemoryDriver(String player1Name, String player2Name, int rows, int cols, String gameName) throws IOException {
        super(player1Name, player2Name, rows, cols, gameName);
        this.memoryPlayers = new MemoryPlayer[]{new MemoryPlayer(player1Name, 0), new MemoryPlayer(player2Name, 1)};
        this.currentPlayer = 0;
    }
    // Methods

    public void initializeBoardArray() {
        memoryBoardGame = new MemoryBoardGame();
        memoryBoardGame.initializeGrid();
        //drawMemoryDriver = new DrawMemoryDriver();
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
                memoryBoardGame.flipCards(row,col);
                memoryBoardGame.flipCards(otherCard.getRow(),otherCard.getCol());
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
        System.out.println(memoryBoardGame.numMatches);
        if (memoryBoardGame.numMatches == 0) {
            return true;
        }
        return false;
    }

    public void runGame() {
        initializeBoardArray();
    }

    public void runTurn() {
        Card clickedCard = memoryBoardGame.grid[boardGUI.rowClicked][boardGUI.colClicked];
        if (isLegalMove(boardGUI.rowClicked, boardGUI.colClicked)) {
            boardGUI.modifyCell(boardGUI.colClicked,boardGUI.rowClicked,clickedCard.getImage());
            if (firstMove()) {
                makeMove(boardGUI.rowClicked, boardGUI.colClicked);
                //DrawMemoryDriver.flipUp(boardGame, boardGUI.colClicked, boardGUI.rowClicked, clickedCard.getImageView(), () -> {});
            }
            else {
                Card otherCard = getPlayer().getPlayerCard();
                makeMove(boardGUI.rowClicked, boardGUI.colClicked);
                if (clickedCard.getStatus()) {
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    boardGUI.modifyCell(boardGUI.colClicked,boardGUI.rowClicked,new Image("/boardGameGUI/memory-cell.jpg", 100,100,true,false));
                    boardGUI.modifyCell(otherCard.getCol(),otherCard.getRow(),new Image("/boardGameGUI/memory-cell.jpg", 100,100,true,false));
//                }
//                    DrawMemoryDriver.flipUp(boardGame, boardGUI.colClicked, boardGUI.rowClicked, clickedCard.getImageView(), () -> {
//                        if (clickedCard.getStatus()) {
//                            DrawMemoryDriver.flipDown(boardGame, boardGUI.colClicked, boardGUI.rowClicked, clickedCard.getImageView());
//                            DrawMemoryDriver.flipDown(boardGame, boardGUI.colClicked, boardGUI.rowClicked, clickedCard.getImageView());
//                        }
//                    });
                }
            }
        }
        if (isGameOver()) {
            System.out.println(getWinner());
        }
    }

//    public void runGame() {
//        initializeBoardArray();
//        int index = 0;
// //       while (!isGameOver()) {
// //           System.out.println(index);
//            if (boardGUI.boardClicked) {
//                Card clickedCard = memoryBoardGame.grid[boardGUI.rowClicked][boardGUI.colClicked];
//                if (isLegalMove(boardGUI.rowClicked, boardGUI.colClicked)) {
//                    boardGUI.modifyCell(boardGUI.colClicked,boardGUI.rowClicked,clickedCard.getImage());
//                    if (firstMove()) {
//                        makeMove(boardGUI.rowClicked, boardGUI.colClicked);
////                        drawMemoryDriver.flipUp(clickedCard.getImageView(), () -> {
////                        });
//                    } else {
//                        Card otherCard = getPlayer().getPlayerCard();
//                        makeMove(boardGUI.rowClicked, boardGUI.colClicked);
//                        if (clickedCard.getStatus()) {
//                            boardGUI.modifyCell(boardGUI.colClicked,boardGUI.rowClicked,new Image("/boardGameGUI/memory-cell.jpg"));
//                            boardGUI.modifyCell(otherCard.getCol(),otherCard.getCol(),new Image("/boardGameGUI/memory-cell.jpg"));
//                        }
////                        drawMemoryDriver.flipUp(clickedCard.getImageView(), () -> {
////                            if (clickedCard.getStatus()) {
////                                drawMemoryDriver.flipDown(clickedCard.getImageView());
////                                drawMemoryDriver.flipDown(otherCard.getImageView());
////                            }
////                        });
//                    }
//                }
//                boardGUI.boardClicked = false;
//                if (isGameOver()) {
//                    System.out.println(getWinner());
//                }
//            }
//  //          System.out.println(++index);
//   //     }
//   //     System.out.println(getWinner());   //should put this on the menu somehow but not sure if there's a menu yet :(
//  //      System.out.println("Ends");
//    }


}
