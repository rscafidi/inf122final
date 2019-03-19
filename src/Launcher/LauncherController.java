package Launcher;

import Battleship.BattleshipDriver;
import TicTacToe.TicTacToeDriver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import memGame.DrawMemoryDriver;
import othelloGame.DrawOthelloDriver;
import TicTacToe.DrawTicTacToeDriver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LauncherController implements Initializable{
    @FXML
    private TextField p1Name;

	@FXML
	private TextField p2Name;

	@FXML
	private Button confirmP1;

	@FXML
	private Button confirmP2;
	
	@FXML
	private TableView<ScoreRecord> leaderboard;
	
	@FXML
	private TableColumn<ScoreRecord, String> playerNames;
	
	@FXML
	private TableColumn<ScoreRecord, Integer> ticTacToeScores;
	
	@FXML
	private TableColumn<ScoreRecord, Integer> othelloScores;
	
	@FXML
	private TableColumn<ScoreRecord, Integer> memoryScores;
	
	@FXML
	private TableColumn<ScoreRecord, Integer> battleshipScores;

	public String player1, player2;
	public static ScoreRecord p1Score, p2Score;
	@FXML
	public String getPlayer1Name() {
		player1 = p1Name.getText();
		return player1;
	}

	@FXML
	public String getPlayer2Name() {
		player2 = p2Name.getText();
		return player2;
	}

	public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public boolean duplicatedName() {
        return p1Name.getText().equals(p2Name.getText());
    }
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		playerNames.setCellValueFactory(new PropertyValueFactory<ScoreRecord, String>("playerName"));
		ticTacToeScores.setCellValueFactory(new PropertyValueFactory<ScoreRecord, Integer>("ticTacToe"));
		othelloScores.setCellValueFactory(new PropertyValueFactory<ScoreRecord, Integer>("othello"));
		memoryScores.setCellValueFactory(new PropertyValueFactory<ScoreRecord, Integer>("memory"));
		battleshipScores.setCellValueFactory(new PropertyValueFactory<ScoreRecord, Integer>("battleship"));
	}
    @FXML
    protected void addPlayer1ToTable() {
    	p1Score = new ScoreRecord(player1, 0, 0, 0, 0);
    	leaderboard.getItems().add(p1Score);
    }
    @FXML
    protected void confirmP1Clicked(ActionEvent event) {
        Window p1Error = confirmP1.getScene().getWindow();
        if(getPlayer1Name().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, p1Error, "Empty Player1's Name!",
                    "Please enter the player1's name to proceed :)");
            return;
        }
        if(duplicatedName()) {
            showAlert(Alert.AlertType.ERROR, p1Error, "Duplicated Names!",
                    "Please change the names so they can be unique :)");
            return;
        }
        p1Name.setDisable(true);
        confirmP1.setDisable(true);
        addPlayer1ToTable();
        
    }
    @FXML
    protected void addPlayer2ToTable() {
    	p2Score = new ScoreRecord(player2, 0, 0, 0, 0);
    	leaderboard.getItems().add(p2Score);
    }
    
    @FXML
    protected void confirmP2Clicked(ActionEvent event) {
        Window p2Error = confirmP2.getScene().getWindow();
        if(getPlayer2Name().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, p2Error, "Empty Player2's Name!",
                    "Please enter the player2's name to proceed :)");
            return;
        }
        if(duplicatedName()) {
            showAlert(Alert.AlertType.ERROR, p2Error, "Duplicated Names!",
                    "Please change the names so they can be unique :)");
            return;
        }
        p2Name.setDisable(true);
        confirmP2.setDisable(true);
        addPlayer2ToTable();
	}
    
    public static void incrementTicTacToeForP1() {
    	p1Score.setTicTacToe(p1Score.getTicTacToe() + 1);
    	Launcher.controller.leaderboard.refresh();
    }
    
    public static void incrementOthelloForP1() {
    	p1Score.setOthello(p1Score.getOthello() + 1);
    	Launcher.controller.leaderboard.refresh();
    }
    
    public static void incrementMemoryForP1() {
    	p1Score.setMemory(p1Score.getMemory() + 1);
    	Launcher.controller.leaderboard.refresh();
    }
    
    public static void incrementBattleshipForP1() {
    	p1Score.setBattleship(p1Score.getBattleship() + 1);
    	Launcher.controller.leaderboard.refresh();
    }
    
    public static void incrementTicTacToeForP2() {
    	p2Score.setTicTacToe(p2Score.getTicTacToe() + 1);
    	Launcher.controller.leaderboard.refresh();
    }
    
    public static void incrementOthelloForP2() {
    	p2Score.setOthello(p2Score.getOthello() + 1);
    	Launcher.controller.leaderboard.refresh();
    }
    
    public static void incrementMemoryForP2() {
    	p2Score.setMemory(p2Score.getMemory() + 1);
    	Launcher.controller.leaderboard.refresh();
    }
    
    public static void incrementBattleshipForP2() {
    	p2Score.setBattleship(p2Score.getBattleship() + 1);
    	Launcher.controller.leaderboard.refresh();
    }
	@FXML
	protected void startTicTacToe(MouseEvent event) throws Exception {
		//Dummy code
		System.out.println("Starting TicTacToe...");
		//Run game here
        DrawTicTacToeDriver drawtictactoeDriver = new DrawTicTacToeDriver(p1Name.getText(), p2Name.getText(), 3, 3, "TicTacToe");
	}

	@FXML
	protected void startOthello(MouseEvent event) throws IOException {
		//Dummy code
		System.out.println("Starting Othello...");
		//Run game here
		DrawOthelloDriver drawOthelloDriver = new DrawOthelloDriver(p1Name.getText(), p2Name.getText(), 8, 8, "Othello");
	}

	@FXML
	protected void startMemory(MouseEvent event) {
		//Dummy code
		System.out.println("Starting Memory...");
		//Run game here
		DrawMemoryDriver drawMemoryDriver = new DrawMemoryDriver(p1Name.getText(), p2Name.getText(), 6, 6, "Memory");
	}

	@FXML
	protected void startBattleship(MouseEvent event) throws Exception{
		System.out.println("Starting Battleship...");
		//to use abstracted classes, uncomment this line.
		//current issue:  model does not work because both players are attached
		//to the game board.
//		BattleshipDriver battleshipDriver = new BattleshipDriver(player1, player2, 10, 10, "Battleship");
//		new newDrawBattleship(getPlayer1Name(),getPlayer2Name());
		BattleshipDriver battleshipDriver = new BattleshipDriver(getPlayer1Name(), getPlayer2Name(), 10, 10, "Battleship");
	}

	
	
	

}
