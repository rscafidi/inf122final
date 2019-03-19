package Launcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ScoreRecord {
	static HashMap<String, ArrayList<Integer>> scores = new HashMap<String, ArrayList<Integer>>();
	static ArrayList<String> currentGames = new ArrayList<String>(Arrays.asList("TicTacToe", "Othello", "Memory", "Battleship"));
	static HashSet<String> currentPlayers = new HashSet<String>();
	private SimpleStringProperty playerName;
	private SimpleIntegerProperty ticTacToe, othello, memory, battleship;
	public ScoreRecord(String playerName, int ticTacToe, int othello, int memory, int battleship) {
		this.playerName = new SimpleStringProperty(playerName);
		this.ticTacToe = new SimpleIntegerProperty(ticTacToe);
		this.othello = new SimpleIntegerProperty(othello);
		this.memory = new SimpleIntegerProperty(memory);
		this.battleship = new SimpleIntegerProperty(battleship);
		addPlayerToDatabase(playerName);
	}
	
	public void addPlayerToDatabase(String player) {
		currentPlayers.add(player);
		ArrayList<Integer> initialScores = new ArrayList<Integer>();
		for (int i = 0; i < currentGames.size(); i++) {
			initialScores.add(0);
		}
		scores.put(player, initialScores);
	}

	public void incrementScoreForPlayer(String player, String game) {
		int indexOfGame = currentGames.indexOf(game);
		ArrayList<Integer>scoresList = scores.get(player);
		int newScore = scoresList.get(indexOfGame) + 1;
		scoresList.set(indexOfGame, newScore);
	}
	
	public String getPlayerName() {
		return playerName.get();
	}
	
	public int getTicTacToe() {
		return ticTacToe.get();
	}
	
	public void setTicTacToe(int ticTacToe) {
		this.ticTacToe.set(ticTacToe);
	}
	
	public int getOthello() {
		return othello.get();
	}
	
	public void setOthello(int othello) {
		this.othello.set(othello);
	}

	public int getMemory() {
		return memory.get();
	}
	
	public void setMemory(int memory) {
		this.memory.set(memory);
	}
	
	public int getBattleship() {
		return battleship.get();
	}
	
	public void setBattleship(int battleship) {
		this.battleship.set(battleship);
	}
}
