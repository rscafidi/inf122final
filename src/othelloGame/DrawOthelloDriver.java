package othelloGame;

import java.io.IOException;

import javafx.scene.layout.GridPane;

public class DrawOthelloDriver {
	
	public static OthelloDriver othello;
	public DrawOthelloDriver(String player1Name, String player2Name, int rows, int cols, String gameName) throws IOException {
		othello = new OthelloDriver(player1Name, player2Name, rows, cols, gameName);
		othello.runGame();
	}
	
	public static void handleMove(GridPane boardGame) {
		othello.runTurn(boardGame);
    }

}
