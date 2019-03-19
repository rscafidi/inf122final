package TicTacToe;

import javafx.scene.layout.GridPane;

import java.io.IOException;

public class DrawTicTacToeDriver {

    public static TicTacToeDriver tictactoe;
    public DrawTicTacToeDriver(String player1Name, String player2Name, int rows, int cols, String gameName) throws IOException {
        tictactoe = new TicTacToeDriver(player1Name, player2Name, rows, cols, gameName);
        tictactoe.runGame();
    }

    public static void handleMove(GridPane boardGame) { tictactoe.runTurn(boardGame); }
}
