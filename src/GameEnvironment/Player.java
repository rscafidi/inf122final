package GameEnvironment;

import java.util.ArrayList;

public class Player {
	private String userName;
	private int playerTurn;
	private int playerScore;
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
}
