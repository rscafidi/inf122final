package TicTacToe;

import GameEnvironment.GameDriver;
import boardGameGUI.BoardGame;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TicTacToeDriver extends GameDriver
{
    final Image X = new Image("/resources/tttX.png");
    final Image O = new Image("/resources/tttO.png");
    final Image BLANK = new Image("/boardGameGUI/white-square-cell.jpg");
    public final int rows = 3;
    public final int cols = 3;

    public TicTacToeDriver(String p1, String p2, int rows, int cols, String game) throws IOException
    {
        super(p1, p2, rows, cols, game);
        //TicTacToePlayer player1 = new TicTacToePlayer(p1, 0);
        //TicTacToePlayer player2 = new TicTacToePlayer(p2, 1);
        this.players = new TicTacToePlayer[]{new TicTacToePlayer(p1, 0), new TicTacToePlayer(p2, 1)};
        boardGUI = new BoardGame(players[0], players[1], rows, cols, game);
        this.currentPlayer = 0;
    }


    @Override
    public boolean isGameOver()
    {
        return false;
    }
    @Override
    public String getWinner() {
        return "yeet";
    }

    @Override
    public void initializeBoardArray() {
        //System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
        Image defaultCell = new Image("/boardGameGUI/white-square-cell.jpg");
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++)
            {
                boardArray[row][col] = new TicTacToePiece("-", defaultCell, col, row);
            }
        }
    }

    @Override
    public void runGame() {
        System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
    }

    @Override
    public void updateScore(int playerIndex, int value) {
        System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
    }

    @Override
    public boolean isLegalMove(int x, int y) {
        System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
        return false;
    }

    @Override
    public void makeMove(int x, int y) {
    }

    public void runTurn(GridPane boardGame) {

    }
}
