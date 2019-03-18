package Battleship;

import java.awt.*;
import java.util.ArrayList;

public class BattleshipGameLogic {
    int currPlayer = 1;
    BattleshipPlayer player1;
    BattleshipPlayer player2;
    ArrayList<Point> moves = new ArrayList<Point>();
    BattleshipPlayer winner = null;

    public BattleshipGameLogic(BattleshipPlayer p1, BattleshipPlayer p2) {
        this.player1 = p1;
        this.player2 = p2;

        generateValidMovesList(); //generating valid moves for first player [starting player]
    }

    void checkForWinner() { //currently just to see if it works or not

        if (player1.getScore() >= 17) { //17 because if every single ship is hit it totals to 17
            System.out.println("Player 1 wins");
            this.winner = player1;
        } else if (player2.getScore() >= 17) {
            System.out.println("Player 2 wins");
            this.winner = player2;
        }
    }

    void makeMove(int i, int j) { //i = row; j = col
        checkForWinner();
        if (isValidMove(i, j)) {
            if (currPlayer == 1) {
                if (player2.playerShips[i][j] != 0) { //if second player has a ship at that position add hit to current playerOceanHits and increment their score by 1
                    player1.playerOceanHits[i][j] = 1; //hit
                    player1.setPlayerScore(player1.getScore() + 1);
                } else {
                    player1.playerOceanHits[i][j] = 2; //miss
                }
            } else {
                if (player1.playerShips[i][j] != 0) { //if first player has a ship at that position add hit to current playerOceanHits and increment their score by 1
                    player2.playerOceanHits[i][j] = 1; //hit
                    player2.setPlayerScore(player2.getScore() + 1);
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

        generateValidMovesList();
        Point move = new Point(i, j);
        return moves.contains(move);
    }

    void changeTurn() {
        if (currPlayer == 1) {
            currPlayer = 2;
        } else {
            currPlayer = 1;
        }
    }






}
