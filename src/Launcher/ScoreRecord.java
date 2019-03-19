package Launcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class ScoreRecord {

	static HashMap<String, HashMap<String, Integer>> playersAndScores = new HashMap<String, HashMap<String, Integer>>();

	static String scoreRecordsFile = "scores.txt";
	static String json;


	static HashMap<String, ArrayList<Integer>> scores = new HashMap<String, ArrayList<Integer>>();
	static ArrayList<String> currentGames = new ArrayList<String>(Arrays.asList("TicTacToe", "Othello", "Memory", "Battleship"));
	static HashSet<String> currentPlayers = new HashSet<String>();
	private SimpleStringProperty playerName;
//	private SimpleIntegerProperty ticTacToe, othello, memory, battleship;
	public ScoreRecord(String playerName, int ticTacToe, int othello, int memory, int battleship) {
		this.playerName = new SimpleStringProperty(playerName);
//		this.ticTacToe = new SimpleIntegerProperty(ticTacToe);
//		this.othello = new SimpleIntegerProperty(othello);
//		this.memory = new SimpleIntegerProperty(memory);
//		this.battleship = new SimpleIntegerProperty(battleship);

//		playersAndScores.put("Richard", new HashMap<String, Integer>());
//		playersAndScores.get("Richard").put("Battleship", 50);
//
//		Gson gson = new GsonBuilder().create();
//		json = gson.toJson(playersAndScores);

		try {
			readJsonFromFile();
		}
		catch (Exception e) {
			System.out.println("error: " + e);
		}
		addPlayerToDatabase(playerName);
	}

	public void updateScoreFile() {
		generateJsonOnCurrentScores();
		try {
			writeScoresToFile();
		}
		catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

	public void generateJsonOnCurrentScores() {
		Gson gson = new GsonBuilder().create();
		json = gson.toJson(playersAndScores);
	}

	public void writeScoresToFile() throws IOException {
		File scoresFile = new File(scoreRecordsFile);
		scoresFile.createNewFile();
		FileOutputStream outputStream = new FileOutputStream(scoreRecordsFile);
		byte[] strToBytes = json.getBytes();
		outputStream.write(strToBytes);
		outputStream.close();
	}

	public void readJsonFromFile() throws IOException {
		StringBuilder jsonBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(scoreRecordsFile))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				jsonBuilder.append(sCurrentLine).append('\n');
			}
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
		}
		json = jsonBuilder.toString();
//		playersAndScores = new Gson().fromJson(json, LinkedTreeMap.class);
//		System.out.println(tmp);
		System.out.println(json);
	}
	
	public void addPlayerToDatabase(String player) {

		if (playersAndScores.containsKey(player)) {
			//do nothing
		}
		else
		{
			playersAndScores.put(player, new HashMap<String, Integer>());
			for (int i = 0; i < currentGames.size(); ++i) {
				playersAndScores.get(player).put(currentGames.get(i), 0);
			}
		}
		updateScoreFile();
//		currentPlayers.add(player);
//		ArrayList<Integer> initialScores = new ArrayList<Integer>();
//		for (int i = 0; i < currentGames.size(); i++) {
//			initialScores.add(0);
//		}
//		scores.put(player, initialScores);


//		playersAndScores.clear();
//		Gson gson = new Gson();
//		playersAndScores = gson.fromJson(json, HashMap.class);
//
//		System.out.println(playersAndScores);

	}

	public void incrementScoreForPlayer(String player, String game) {
		if (!playersAndScores.containsKey(player)) {
			System.out.println("Error: the player is not in the database!");
		}
		else {
			Integer tmp = playersAndScores.get(player).get(game);
			playersAndScores.get(player).put(game, tmp + 1);
		}
		updateScoreFile();
//		int indexOfGame = currentGames.indexOf(game);
//		ArrayList<Integer>scoresList = scores.get(player);
//		int newScore = scoresList.get(indexOfGame) + 1;
//		scoresList.set(indexOfGame, newScore);
	}
	
	public String getPlayerName() {
		return playerName.get();
	}

	public int getTicTacToe() {
		return playersAndScores.get(playerName.get()).get("TicTacToe");
	}
	
	public void setTicTacToe(int ticTacToe) {
		Integer tmp = playersAndScores.get(playerName.get()).get("TicTacToe");
		playersAndScores.get(playerName.get()).put("TicTacToe", tmp + 1);
//		this.ticTacToe.set(ticTacToe);
	}
	
	public int getOthello() {
		return playersAndScores.get(playerName.get()).get("Othello");
//		return othello.get();
	}

	public void setOthello(int othello) {
		Integer tmp = playersAndScores.get(playerName.get()).get("Othello");
		playersAndScores.get(playerName.get()).put("Othello", tmp + 1);

	}

	public int getMemory() {
		return playersAndScores.get(playerName.get()).get("Memory");
	}

	public void setMemory(int memory) {
		Integer tmp = playersAndScores.get(playerName.get()).get("Memory");
		playersAndScores.get(playerName.get()).put("Memory", tmp + 1);
	}

	public int getBattleship() {
		return playersAndScores.get(playerName.get()).get("Battleship");
	}

	public void setBattleship(int battleship) {
		Integer tmp = playersAndScores.get(playerName.get()).get("Battleship");
		playersAndScores.get(playerName.get()).put("Battleship", tmp + 1);
	}
}
