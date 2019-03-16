package GameEnvironment;

import java.util.ArrayList;

public class Player {


	private String userName;
	private int playerTurn;
	private int playerScore;
	private GamePiece gamePiece;


	public Player(String userName, int turn) {
		this.userName = userName;
		playerTurn = turn;
		playerScore = 0;
	}


	public void setPlayerScore(int score) {
		playerScore = score;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public int getScore() {
		return playerScore;
	}

	public GamePiece getGamePiece() {
		return this.gamePiece;
	}

	public void setGamePiece(GamePiece gamePiece) {
		this.gamePiece = gamePiece;
	}
}
