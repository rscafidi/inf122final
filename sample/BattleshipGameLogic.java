package sample;

import java.awt.*;
import java.util.ArrayList;

public class BattleshipGameLogic {
    String currentPlayer = "";
    int[][] currentBoard;

    BattleshipGameLogic(int[][] board, String player) {
        currentBoard = board;
        currentPlayer = player;
    }

    void updatedGameState(int[][] currentState) {
        currentBoard = currentState;
    }

    ArrayList<Point> validMoves() {
        ArrayList<Point> moves = new ArrayList<Point>();
        moves.add(new Point(10,10));
        for (int i = 0; i < currentBoard.length; ++i) {
            for (int j = 0; j < currentBoard[0].length; ++i) {
                if(currentBoard[i][j] == 0) { //make 0 = unclicked, 1 = hit, 2 = miss
                    moves.add(new Point(i,j));
                }
            }
        }
        return moves;
    }

}