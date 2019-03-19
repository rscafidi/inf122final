package TicTacToe;

import GameEnvironment.Player;
import javafx.scene.image.Image;

public class  TicTacToePlayer extends Player
{
    public TicTacToePlayer(String userName, int turn) {
        super(userName, turn);
        if (turn == 0) {
            Image xTile = new Image("/resources/tttX.png");
            this.setGamePiece(new TicTacToePiece("X", xTile, 0, 0));
        } else {
            Image oTile = new Image("/resources/tttO.png");
            this.setGamePiece(new TicTacToePiece("O", oTile, 0, 0));
        }
    }
}
