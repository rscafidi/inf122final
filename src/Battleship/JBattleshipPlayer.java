package Battleship;

import java.util.Random;

public class JBattleshipPlayer {
    private String playerName;
    int playerScore = 0;
    int[][] playerShips = new int[DrawBattleshipGameBoard.BOARD_WIDTH][DrawBattleshipGameBoard.BOARD_HEIGHT];
    int[][] playerOceanHits = new int[DrawBattleshipGameBoard.BOARD_WIDTH][DrawBattleshipGameBoard.BOARD_HEIGHT];

    JBattleshipPlayer(String userName) {
        playerName = userName;
        setUpOceans();
        randomPlaceShips();
        printPlayerShips();
        printPlayerOceanHits();
    }

    void setUpOceans() {
        for (int i = 0; i < playerShips.length; i++ ) {
            for (int j = 0; j < playerShips[i].length; j++ ) {
                playerShips[i][j] = 0;
                playerOceanHits[i][j] = 0;
            }
        }
    }

    void randomPlaceShips() {
        Random rand = new Random();
        int colLoc = rand.nextInt(10);

        //placing Carrier
        for (int i = 0; i <  5; i++ ) {
                playerShips[colLoc][i] = DrawBattleshipGameBoard.CARRIER;
        }

        //placing Battleship
        colLoc = rand.nextInt(10);
        for (int i = 0; i < 4;) {
            if (playerShips[colLoc][i] != 0) {
                colLoc = rand.nextInt(10);
                continue;
            }
            playerShips[colLoc][i] = DrawBattleshipGameBoard.BATTLESHIP;
            i++;
        }

        //placing Cruiser
        colLoc = rand.nextInt(10);
        for (int i = 0; i < 3;) {
            if (playerShips[colLoc][i] != 0) {
                colLoc = rand.nextInt(10);
                continue;
            }
            playerShips[colLoc][i] = DrawBattleshipGameBoard.CRUISER;
            i++;
        }

        //placing Submarine
        for (int i = 0; i < 3;) {
                if (playerShips[colLoc][i] != 0) {
                    colLoc = rand.nextInt(10);
                    continue;
                }
            playerShips[colLoc][i] = DrawBattleshipGameBoard.SUBMARINE;
            i++;
        }

        //placing destroyer
        for(int i = 0; i < 2;) {
            if (playerShips[colLoc][i] != 0) {
                colLoc = rand.nextInt(10);
                continue;
            }
            playerShips[colLoc][i] = DrawBattleshipGameBoard.DESTROYER;
            i++;
        }
    }

    void printPlayerShips() {
        System.out.print("playerShips: \n");
        for (int i = 0; i < DrawBattleshipGameBoard.BOARD_WIDTH; i++) {
            for (int j = 0; j < DrawBattleshipGameBoard.BOARD_HEIGHT; j++) {
                System.out.print(" " + playerShips[i][j] + " ");
            }
            System.out.println();
        }
    }

    void printPlayerOceanHits() {
        System.out.print("playerOceanHits: \n");
        for (int i = 0; i < DrawBattleshipGameBoard.BOARD_HEIGHT; i++) {
            //System.out.print("ROWS: ");
            for (int j = 0; j < DrawBattleshipGameBoard.BOARD_WIDTH; j++) {
                //System.out.print(" ["+i+"]["+j+"]" + playerOceanHits[i][j] + " ");
                System.out.print(" " + playerOceanHits[i][j] + " ");
            }
            System.out.println();
        }
    }

    public String getUserName() {
        return playerName;
    }

    public int getScore() {
        return playerScore;
    }
}
