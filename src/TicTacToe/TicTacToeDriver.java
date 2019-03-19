package TicTacToe;

import GameEnvironment.GameDriver;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TicTacToeDriver extends GameDriver
{
    final Image X = new Image("/resources/tttX.png");
    final Image O = new Image("/resources/tttO.png");
    final Image BLANK = new Image("/resources/tttBlank.png");
    final int rows = 3;
    final int cols = 3;

    public TicTacToeDriver(String p1, String p2, int rows, int cols, String game) throws IOException
    {
        super(p1, p2, rows, cols, game);
        TicTacToePlayer player1 = new TicTacToePlayer(p1, 0);
        TicTacToePlayer player2 = new TicTacToePlayer(p2, 1);
        boardGUI = new TicTacToeGameBoard(player1, player2, rows, cols, game);
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
