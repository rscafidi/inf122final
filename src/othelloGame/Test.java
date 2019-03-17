package othelloGame;

import java.io.IOException;

import javafx.scene.layout.GridPane;

public class Test {
	
	public static OthelloDriver othello;
	public Test(String player1Name, String player2Name, int rows, int cols, String gameName) throws IOException {
		othello = new OthelloDriver(player1Name, player2Name, rows, cols, gameName);
		othello.runGame();
	}
	
	public static void handleMove(GridPane boardGame) {
    }

}
