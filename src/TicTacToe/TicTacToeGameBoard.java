package TicTacToe;

import boardGameGUI.BoardGame;

import java.io.IOException;

public class TicTacToeGameBoard extends BoardGame {

    TicTacToeLogic Logic;

    public TicTacToeGameBoard(TicTacToePlayer player1, TicTacToePlayer player2,
                           int rows, int cols, String gameName) throws IOException {
        super(player1, player2, rows, cols, gameName);
        Logic = new TicTacToeLogic(player1, player2);
    }
}
