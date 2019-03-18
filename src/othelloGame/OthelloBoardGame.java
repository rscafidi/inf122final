package othelloGame;

import java.io.IOException;

import GameEnvironment.Player;
import boardGameGUI.BoardGame;

public class OthelloBoardGame extends BoardGame{

	public OthelloBoardGame(Player player1, Player player2, int rows, int cols, String gameName) throws IOException {
		super(player1, player2, rows, cols, gameName);
		
	}

}
