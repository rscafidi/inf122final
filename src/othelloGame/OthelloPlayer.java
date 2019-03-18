package othelloGame;

import GameEnvironment.Player;
import javafx.scene.image.Image;

public class OthelloPlayer extends Player{
	public OthelloPlayer(String userName, int turn) {
		super(userName, turn);
		if (turn == 0) {
			Image redDisk = new Image("/resources/red-circle.jpg");
			this.setGamePiece(new OthelloPiece("R", redDisk, 0, 0));
		} else {
			Image blueDisk = new Image("/resources/blue-circle.png");
			this.setGamePiece(new OthelloPiece("B", blueDisk, 0, 0));
		}
	}

}
