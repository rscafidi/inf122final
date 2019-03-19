package TicTacToe;

import GameEnvironment.GamePiece;
import javafx.scene.image.Image;

public class TicTacToePiece extends GamePiece {

    public int x, y;
    public TicTacToePiece(String pieceName, Image image, int x, int y)
    {
        super(pieceName, image);
        this.x = x;
        this.y = y;
    }

    public void setPiece(String piece)
    {
        this.setPieceName(piece);
        if(piece.equals("X"))
        {
            Image xTile = new Image("/resources/tttX.png");
            this.setImage(xTile);
        }
        else {
            Image oTile = new Image("/resources/tttO.png");
            this.setImage(oTile);
        }
    }
}
