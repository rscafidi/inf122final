package Battleship;

import javafx.scene.control.Alert;

import java.awt.*;
import java.util.ArrayList;

public class JBattleshipGameLogic {
    int currPlayer = 1;
    JBattleshipPlayer player1;
    JBattleshipPlayer player2;
    //DrawBattleshipGameBoard board = new DrawBattleshipGameBoard();
    //DrawBattleshipGameBoard board;
    ArrayList<Point> moves = new ArrayList<Point>();
    JBattleshipPlayer winner = null;


    public JBattleshipGameLogic(String p1, String p2) {
        System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
        player1 = new JBattleshipPlayer(p1);
        player2 = new JBattleshipPlayer(p2);
        generateValidMovesList(); //generating valid moves for first player [starting player]
    }

    void checkForWinner() {
        System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());//currently just to see if it works or not
        if (player1.playerScore >= 17) { //17 because if every single ship is hit it totals to 17
            System.out.println("Player 1 wins");
            this.winner = player1;
        } else if (player2.playerScore >= 17) {
            System.out.println("Player 2 wins");
            this.winner = player2;
        }
    }

    void makeMove(int i, int j) { //i = row; j = col
        System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
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
            //updateGameBoard();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Move");
            alert.setHeaderText(null);
            alert.setContentText("Invalid move");
            alert.showAndWait();
//            System.out.println("Invalid move try again.");
        }
    }

//    void updateGameBoard(JBattleshipGameLogic logic) { //updates so GUI shows current player's ocean of hits
//        for (int i = 0; i < logic.currentPlayer().playerOceanHits.length; i++ ) {
//            for (int j = 0; j < logic.currentPlayer().playerOceanHits[i].length; j++ ) {
//                if (logic.currentPlayer().playerOceanHits[i][j] == 1) {
//                    board.modifyCell(i, j, logic.hitBoardCell);
//                } else if (logic.currentPlayer().playerOceanHits[i][j] == 2) {
//                    board.modifyCell(i, j, logic.missBoardCell);
//                } else {
//                    board.modifyCell(i,j, DrawBattleshipGameBoard.defaultBoardCell);
//                }
//            }
//        }
//    }

    JBattleshipPlayer currentPlayer() {
        System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
        if (currPlayer == 1) {
            return player1;
        } else if (currPlayer == 2) {
            return player2;
        }
        return null;
    }

    void generateValidMovesList() {
        System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
        moves.clear();
        for (int i = 0; i < currentPlayer().playerOceanHits.length; ++i) {
            for (int j = 0; j < currentPlayer().playerOceanHits[i].length; ++j) {
                if(currentPlayer().playerOceanHits[i][j] == 0) { //make 0 = unclicked, 1 = hit, 2 = miss
                    moves.add(new Point(i,j));
                }
            }
        }
    }

    boolean isValidMove(int i, int j) {
        System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
        generateValidMovesList();
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
        System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
        if (currPlayer == 1) {
            currPlayer = 2;
        } else {
            currPlayer = 1;
        }
    }






}