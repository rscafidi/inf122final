package TicTacToe;

import boardGameGUI.BoardGame;

import java.io.IOException;

public class TicTacToeGameBoard extends BoardGame {


    public TicTacToeGameBoard(TicTacToePlayer player1, TicTacToePlayer player2,
                           int rows, int cols, String gameName) throws IOException {
        super(player1, player2, rows, cols, gameName);
    }
}
