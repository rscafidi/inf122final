package Launcher;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import boardGameGUI.BoardGame;
import gameenvironmentplugin.GameEnvironmentPlugin;
import java.awt.EventQueue;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

	@FXML
	protected String getPlayer1Name() {
		return p1Name.getText();
	}
	
	@FXML
	protected String getPlayer2Name() {
		return p2Name.getText();
	}
	
	public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
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
	}
	
	@FXML
	protected void startCheckers(MouseEvent event) {
		//Dummy code
		System.out.println("Starting Checkers...");
        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                new gameenvironmentplugin.GameEnvironmentPlugin("Checkers");
            }
        };
        EventQueue.invokeLater(r);
	}
	
	@FXML
	protected void startMemory(MouseEvent event) {
		//Dummy code
		System.out.println("Starting Memory...");
	}
	
	@FXML
	protected void startBattleship(MouseEvent event) throws Exception{
		//Dummy code
		System.out.println("Starting Battleship...");
		new BoardGame(10, 10);
	}

}
