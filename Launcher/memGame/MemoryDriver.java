package memGame;

import GameEnvironment.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MemoryDriver extends GameDriver{

    // Fields

    //public MemoryPlayer[] memoryPlayers;
    public MemoryBoardGame memoryBoardGame;
   // public int currentTurn = 0;  //player one goes first


    public MemoryDriver(String player1Name, String player2Name, int rows, int cols, String gameName) throws IOException {
        super(player1Name, player2Name, rows, cols, gameName);
        //this.memoryPlayers = new MemoryPlayer[]{new MemoryPlayer(player1Name, 0), new MemoryPlayer(player2Name, 1)};
        this.currentPlayer = 0;
    }
    // Methods

    public void initializeBoardArray() {
        memoryBoardGame = new MemoryBoardGame();
        memoryBoardGame.initializeGrid();
        //drawMemoryDriver = new DrawMemoryDriver();
    }


    public Player getPlayer() {
        return players[currentPlayer];
    }


    public boolean isLegalMove(int row, int col) {
        if (memoryBoardGame.grid[row][col].getStatus() && !memoryBoardGame.grid[row][col].getFlipped()) {
            return true;
        }
        return false;
    }

    public boolean firstMove() {
        if (getPlayer().getGamePiece() == null) {
            return true;
        }
        return false;
    }

    public void makeMove(int row, int col) {
        Card card = memoryBoardGame.grid[row][col];
        Player player = getPlayer();

        memoryBoardGame.flipCards(row,col);

        if (firstMove()) {
            player.setGamePiece(card);
            //player.addPlayerCard(card);
        }
        else {
            Card otherCard = (Card)player.getGamePiece();
            if (otherCard.getName().equals(card.getName())) {
                memoryBoardGame.updateCards(row,col);
                memoryBoardGame.updateCards(otherCard.getRow(),otherCard.getCol());
                memoryBoardGame.numMatches--;
                System.out.println(otherCard.getName());

                int score = getPlayer().getScore();
                updateScore(currentPlayer,++score);
                System.out.println(getPlayer().getScore());
                //updateScore();
            }
            else {
                memoryBoardGame.flipCards(row,col);
                memoryBoardGame.flipCards(otherCard.getRow(),otherCard.getCol());
            }

            player.setGamePiece(null);
        }
    }

    /**
     * Determines the winner of the game based on the players' scores
     * @return a string stating the winner of the match, or if it's a tie
     */
    public String getWinner() {
        if (players[0].getScore() > players[1].getScore()) {
            return players[0].getUserName();
        }
        else if (players[0].getScore() < players[1].getScore()) {
            return players[1].getUserName();
        }
        else {
            return "TIE";
        }
    }

    public void updateScore() {
        Player current = players[currentPlayer];
        int score = current.getScore();
        current.setPlayerScore(++score);
    }

    public boolean isGameOver() {
        if (memoryBoardGame.numMatches == 0) {
            return true;
        }
        return false;
    }

    public void runGame() {
        initializeBoardArray();
    }


    public void runTurn(GridPane boardGame) {
        Card clickedCard = memoryBoardGame.grid[boardGUI.rowClicked][boardGUI.colClicked];
        if (isLegalMove(boardGUI.rowClicked, boardGUI.colClicked)) {
            //boardGUI.modifyCell(boardGUI.colClicked,boardGUI.rowClicked,clickedCard.getImage());
            if (firstMove()) {
                makeMove(boardGUI.rowClicked, boardGUI.colClicked);
                DrawMemoryDriver.flipUp(boardGame, boardGUI.colClicked, boardGUI.rowClicked, clickedCard.getImage(), () -> {
                });
            } else {
                Player player = getPlayer();
                Card otherCard = (Card)player.getGamePiece();
                makeMove(boardGUI.rowClicked, boardGUI.colClicked);
                DrawMemoryDriver.flipUp(boardGame, boardGUI.colClicked, boardGUI.rowClicked, clickedCard.getImage(), () -> {
                    if (clickedCard.getStatus()) {
                        boardGUI.modifyCell(boardGUI.colClicked, boardGUI.rowClicked, new Image("/boardGameGUI/memory-cell.jpg", 100, 85, false, false));
                        boardGUI.modifyCell(otherCard.getCol(), otherCard.getRow(), new Image("/boardGameGUI/memory-cell.jpg", 100, 85, false, false));
                    }
                });
                if (!clickedCard.getStatus()) {
                    if (currentPlayer == 0) {
                        boardGUI.updatePlayer1Score();
                    } else {
                        boardGUI.updatePlayer2Score();
                    }
                }

                switchTurn();
                boardGUI.switchTurnGUI();
            }

            if (isGameOver()) {
                boardGUI.displayWinner(getWinner());
            }
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
