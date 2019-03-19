package othelloGame;

import GameEnvironment.GamePiece;
import javafx.scene.image.Image;

public class OthelloPiece extends GamePiece{
	public int x, y;
	public OthelloPiece(String pieceName, Image image, int x, int y) {
		super(pieceName, image);
		this.x = x;
		this.y = y;
	}
	
	public void flip() {
		if (this.getName().equals("B")) {
			this.setPieceName("R");
			Image redDisk = new Image("/resources/red-circle.jpg");
			this.setImage(redDisk);
			
		} else {
			this.setPieceName("B");
			Image blueDisk = new Image("/resources/blue-circle.png");
			this.setImage(blueDisk);
		}
	}
	
	public void placeDisk(String disk) {
		this.setPieceName(disk);
		if (disk.equals("B")) {
			Image blueDisk = new Image("/resources/blue-circle.png");
			this.setImage(blueDisk);
		} else {
			Image redDisk = new Image("/resources/red-circle.jpg");
			this.setImage(redDisk);
		}
	}
}
