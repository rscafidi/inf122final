package Launcher;

import Battleship.DrawBattleshipGameBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

public class LauncherController {
	@FXML
    private TextField p1Name;
	
	@FXML
	private TextField p2Name;
	
	@FXML
	private Button confirmP1;
	
	@FXML
	private Button confirmP2;
	
	public String player1, player2;

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
	}
	
	@FXML
	protected void confirmP2Clicked(ActionEvent event) {
		Window p2Error = confirmP2.getScene().getWindow();
        if(getPlayer1Name().isEmpty()) {
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
	}
	
	@FXML
	protected void startTicTacToe(MouseEvent event) {
		//Dummy code
		System.out.println("Starting TicTacToe...");
		//Run game here
	}
	
	@FXML
	protected void startCheckers(MouseEvent event) {
		//Dummy code
		System.out.println("Starting Checkers...");
		//Run game here
	}
	
	@FXML
	protected void startMemory(MouseEvent event) {
		//Dummy code
		System.out.println("Starting Memory...");
		//Run game here
	}
	
	@FXML
	protected void startBattleship(MouseEvent event) throws Exception{
		System.out.println("Starting Battleship...");
		//to use abstracted classes, uncomment this line.
		//current issue:  model does not work because both players are attached
		//to the game board.
//		BattleshipDriver battleshipDriver = new BattleshipDriver(player1, player2, 10, 10, "Battleship");
		new newDrawBattleship(getPlayer1Name(),getPlayer2Name());
	}

}
