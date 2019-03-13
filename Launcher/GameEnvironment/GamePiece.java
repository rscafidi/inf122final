package GameEnvironment;

import javafx.scene.image.Image;

public abstract class GamePiece {
	//Piece Name can be anything that represents the token ( -, O, X) for tic tac toe as example
	private String pieceName;
	private Image image;
	//Initialize an image like this 
	//Image image = new Image("path/to/image/here");
	public GamePiece(String pieceName, Image image) {
		this.pieceName = pieceName;
		this.image = image;
	}

	public String getName() {
		return this.pieceName;
	}

	public Image  getImage() { return this.image; }

}
