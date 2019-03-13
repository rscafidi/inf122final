package GameEnvironment;

import java.util.ArrayList;

public class Player {

	// Fields
	private String userName;
	private int playerTurn;
	private int playerScore;

	// Constructors
	public Player(String userName, int turn) {
		this.userName = userName;
		playerTurn = turn;
		playerScore = 0;
	}

	// Methods
	public void setPlayerScore(int score) {
		playerScore = score;
	}

}
