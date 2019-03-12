package sample;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class BattleshipGameLogic {
    int currPlayer = 1;
    BattleshipPlayer player1 = new BattleshipPlayer("p1");
    BattleshipPlayer player2 = new BattleshipPlayer("p2");
    DrawBattleshipGameBoard board = new DrawBattleshipGameBoard();
    ArrayList<Point> moves = new ArrayList<Point>();
    Image hitBoardCell = new Image("/boardGameGUI/ocean-hit-cell.png");
    Image missBoardCell = new Image("/boardGameGUI/ocean-miss-cell.png");

    BattleshipGameLogic() {
            generateValidMovesList(); //generating valid moves for first player [starting player]
    }

    void checkForWinner() { //currently just to see if it works or not
        if (player1.playerScore >= 17) { //17 because if every single ship is hit it totals to 17
            System.out.println("Player 1 wins");
        } else if (player2.playerScore >= 17) {
            System.out.println("Player 2 wins");
        }
    }

    void makeMove(int i, int j) {
        checkForWinner();
        if (isValidMove(i, j)) {
            if (currPlayer == 1) {
                if (player2.playerShips[i][j] != 0) { //if second player has a ship at that position add hit to current playerOceanHits and increment their score by 1
                    player1.playerOceanHits[i][j] = 1; //hit
                    player1.playerScore += 1;
                } else {
                    player1.playerOceanHits[i][j] = 2; //miss
                }
            } else {
                if (player1.playerShips[i][j] != 0) { //if first player has a ship at that position add hit to current playerOceanHits and increment their score by 1
                    player2.playerOceanHits[i][j] = 1; //hit
                    player2.playerScore += 1;
                } else {
                    player2.playerOceanHits[i][j] = 2; //miss
                }
            }
            changeTurn(); //change players
            updateGameBoard();
        } else {
            System.out.println("Invalid move try again.");
        }
    }

    void updateGameBoard() { //updates so GUI shows current player's ocean of hits
        for (int i = 0; i < currentPlayer().playerOceanHits.length; i++ ) {
            for (int j = 0; j < currentPlayer().playerOceanHits[i].length; j++ ) {
                if (currentPlayer().playerOceanHits[i][j] == 1) {
                    board.modifyCell(i, j, hitBoardCell);
                } else if (currentPlayer().playerOceanHits[i][j] == 2) {
                    board.modifyCell(i, j, missBoardCell);
                } else {
                    board.modifyCell(i,j, DrawBattleshipGameBoard.defaultBoardCell);
                }
            }
        }
    }

    BattleshipPlayer currentPlayer() {
        if (currPlayer == 1) {
            return player1;
        } else if (currPlayer == 2) {
            return player2;
        }
        return null;
    }

    void generateValidMovesList() {
        moves.add(new Point(10,10));
        for (int i = 0; i < currentPlayer().playerOceanHits.length; ++i) {
            for (int j = 0; j < currentPlayer().playerOceanHits[0].length; ++i) {
                if(currentPlayer().playerOceanHits[i][j] == 0) { //make 0 = unclicked, 1 = hit, 2 = miss
                    moves.add(new Point(i,j));
                }
            }
        }
    }

    boolean isValidMove(int i, int j) {
        Point move = new Point(i, j);
        return moves.contains(move);
//        for (Point move: moves) {
//            if (move.x == i && move.y == j) {
//                return true;
//            }
//        }
//        return false;
    }

    void changeTurn() {
        if (currPlayer == 1) {
            currPlayer = 2;
        } else {
            currPlayer = 1;
        }
    }






}