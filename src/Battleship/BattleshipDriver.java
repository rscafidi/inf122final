package Battleship;

import GameEnvironment.GameDriver;

import java.io.IOException;

public class BattleshipDriver extends GameDriver {

    public BattleshipDriver(String p1, String p2, int rows, int cols, String game) throws IOException {
        super(p1, p2, rows, cols, game);
        BattleshipPlayer player1 = new BattleshipPlayer(p1, 0);
        BattleshipPlayer player2 = new BattleshipPlayer(p2, 1);
        boardGUI = new BattleshipGameBoard(player1, player2, rows, cols, game);
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public void initializeBoardArray() {
        System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
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
}
