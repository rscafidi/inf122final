package Battleship;

import GameEnvironment.Player;

public class BattleshipPlayer extends Player {

    int[][] playerShips = new int[DrawBattleshipGameBoard.BOARD_WIDTH][DrawBattleshipGameBoard.BOARD_HEIGHT];
    int[][] playerOceanHits = new int[DrawBattleshipGameBoard.BOARD_WIDTH][DrawBattleshipGameBoard.BOARD_HEIGHT];

    public BattleshipPlayer(String userName, int turn) {
        super(userName, turn);
    }


}
