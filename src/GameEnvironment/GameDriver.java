package GameEnvironment;
import boardGameGUI.BoardGame;

import java.io.IOException;
public abstract class GameDriver {
	
	public BoardGame boardGUI;
	public GamePiece[][] boardArray;
	public int currentPlayer;
	public Player[] players;
	public String winner;
	public GameDriver(String player1Name, String player2Name, int rows, int cols, String gameName) throws IOException {
		boardArray = new GamePiece[rows][cols];
	}
	public abstract void initializeBoardArray();
	//Run your game logic in here
	public abstract void runGame();
	//Return winner
	public String getWinner() {
		return winner;
	}
	//Will add current turn on GUI soon so you can update it along with this
	public void swichTurn() {
		if (currentPlayer == 0) {
			currentPlayer = 1;
		}
		else {
			currentPlayer = 0;
		}
	}

	public void switchTurn() {
		if (currentPlayer == 0) {
			currentPlayer = 1;
		}
		else {
			currentPlayer = 0;
		}
	}

	//Will add score on GUI soon so you can update it along with this
	public abstract void updateScore(int playerIndex, int value);
	public abstract boolean isLegalMove(int x, int y);
	public abstract void makeMove(int x, int y);
}
