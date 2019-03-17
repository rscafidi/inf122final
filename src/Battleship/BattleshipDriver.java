package Battleship;

import GameEnvironment.GameDriver;
import GameEnvironment.Player;

import java.io.IOException;

public class BattleshipDriver extends GameDriver {

    public BattleshipDriver(String player1Name, String player2Name, int rows, int cols, String gameName) throws IOException {
        super(player1Name, player2Name, rows, cols, gameName);
        BattleshipPlayer player1 = new BattleshipPlayer(player1Name, 0);
        Player player2 = new Player(player2Name, 1);
        initializeBoardArray();
    }

    public void initializeBoardArray() {

    }

    public void runGame() {

    }

    public void updateScore(int playerIndex, int value) {

    }

    public boolean isLegalMove(int x, int y) {

        return false;
    }

    public void makeMove(int x, int y) {

    }
}
