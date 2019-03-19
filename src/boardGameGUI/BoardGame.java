package boardGameGUI;

import GameEnvironment.Player;
import Launcher.LauncherController;
import TicTacToe.DrawTicTacToeDriver;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import memGame.DrawMemoryDriver;
import othelloGame.DrawOthelloDriver;

import java.io.IOException;

public class BoardGame {
	public String gameType;
	public BorderPane layout;
	public GridPane boardGame;
	public String turn;
	public Player player1, player2;
	public VBox infoPanel, nameScore1, nameScore2;
	public HBox nameScoreHolder;
	public Text currentTurn, p1Name, p2Name, score1, score2;
	public int rowClicked = 0, colClicked = 0;
    //Make sure to reset the boardClicked to false after you made the move
    public boolean boardClicked = false;
	public Stage primaryStage;

	public BoardGame(Player player1, Player player2, int rows, int cols, String gameName) throws IOException {
		gameType = gameName;
		primaryStage = new Stage();
		boardGame = new GridPane();
		layout = new BorderPane();
		this.player1 = player1;
		this.player2 = player2;
		turn = player1.getUserName();
		infoPanel = new VBox();
		infoPanel.setAlignment(Pos.CENTER);
		infoPanel.setSpacing(5);
		setupTurn();
		setupPlayer1Panel();
		setupPlayer2Panel();
		switch (gameName) {
			case "Battleship":
				initializeBoard(rows, cols, "/boardGameGUI/ocean-cell.png");
				break;
			case "Memory":
				initializeBoard(rows, cols, "/boardGameGUI/memory-cell.jpg");
				break;
			case "TicTacToe":
				initializeBoard(rows, cols, "/boardGameGUI/white-square-cell.jpg");
				break;
			case "Othello":
				initializeBoard(rows, cols, "/boardGameGUI/white-square-cell.jpg");
				break;
		}
		nameScoreHolder = new HBox(nameScore1, nameScore2);
		nameScoreHolder.setAlignment(Pos.CENTER);
		nameScoreHolder.setSpacing(15);
		infoPanel.getChildren().add(nameScoreHolder);
		layout.setTop(infoPanel);
		primaryStage.setTitle(gameName);
		primaryStage.initModality(Modality.WINDOW_MODAL);
		boardGame.setGridLinesVisible(true);
		primaryStage.setScene(new Scene(layout, 600, 600));
		primaryStage.show();
	}

	public void setupTurn() {

		currentTurn = new Text("Current Turn: " + turn);
		currentTurn.setFill(Color.RED);
		currentTurn.setStyle("-fx-font: 24 arial;");
		infoPanel.getChildren().add(currentTurn);
	}

	public void setupPlayer1Panel() {

		p1Name = new Text(player1.getUserName());
		p1Name.setFill(Color.RED);
		p1Name.setStyle("-fx-font: 24 arial;");
		score1 = new Text("0");
		score1.setFill(Color.RED);
		score1.setStyle("-fx-font: 24 arial;");
		nameScore1 = new VBox(p1Name, score1);
		nameScore1.setAlignment(Pos.CENTER);
		nameScore1.setSpacing(5);
	}

	public void setupPlayer2Panel() {

		p2Name = new Text(player2.getUserName());
		p2Name.setFill(Color.BLUE);
		p2Name.setStyle("-fx-font: 24 arial;");
		score2= new Text("0");
		score2.setFill(Color.BLUE);
		score2.setStyle("-fx-font: 24 arial;");
		nameScore2 = new VBox(p2Name, score2);
		nameScore2.setAlignment(Pos.CENTER);
		nameScore2.setSpacing(5);
	}

	public void initializeBoard(int rows, int cols, String imageDirectory) {

		Image defaultBoardCell = new Image(imageDirectory);
	    for (int i = 0 ; i < cols ; i++) {
	    	ColumnConstraints colConstraints = new ColumnConstraints();
			colConstraints.setPercentWidth(100.0 / cols);
			boardGame.getColumnConstraints().add(colConstraints);
	    }
	    for (int j = 0; j < rows; j++) {
	    	RowConstraints rowConstraints = new RowConstraints();
		    rowConstraints.setPercentHeight(100.0 / rows);
		    boardGame.getRowConstraints().add(rowConstraints);
	    }
	    for (int i = 0; i < cols; i++) {
	    	for (int j = 0; j < rows; j++) {
	    		addCell(i, j, defaultBoardCell);
	    	}
	    }
	    layout.setCenter(boardGame);
	}


	public void addCell(int rowIndex, int colIndex, Image image) {
	    Pane cell = new Pane();
	    ImageView img = new ImageView(image);
	    img.fitWidthProperty().bind(cell.widthProperty());
	    img.fitHeightProperty().bind(cell.heightProperty());
	    cell.getChildren().add(img);
	    cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				rowClicked = rowIndex;
				colClicked = colIndex;
				boardClicked = true;

				// For Memory game
				if (gameType.equals("Memory")) {
					DrawMemoryDriver.handleMove(boardGame);
					boardClicked = false;
				} else if (gameType.equals("Othello")) {
					DrawOthelloDriver.handleMove(boardGame);
					boardClicked = false;
				} else if (gameType.equals("TicTacToe")) {
					DrawTicTacToeDriver.handleMove(boardGame);
					boardClicked = false;
				}
			}
	    });
	    boardGame.add(cell, colIndex, rowIndex);
	}

	public void modifyCell(int colIndex, int rowIndex, Image image) {
		for (Node node : boardGame.getChildren()) {
	        if(GridPane.getRowIndex(node) == rowIndex && GridPane.getColumnIndex(node) == colIndex) {
	        	boardGame.getChildren().remove(node);
	    	    Pane cell = new Pane();
	    	    ImageView img = new ImageView(image);
	    	    img.fitWidthProperty().bind(cell.widthProperty());
	    	    img.fitHeightProperty().bind(cell.heightProperty());
	    	    cell.getChildren().add(img);
	    	    cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    			@Override
	    			public void handle(MouseEvent arg0) {
	    				rowClicked = rowIndex;
	    				colClicked = colIndex;
	    				boardClicked = true;
	    				// For Memory game
						if (gameType.equals("Memory")) {
							DrawMemoryDriver.handleMove(boardGame);
							boardClicked = false;
						} else if (gameType.equals("Othello")){
							DrawOthelloDriver.handleMove(boardGame);
							boardClicked = false;
						} else if (gameType.equals("TicTacToe")) {
							DrawTicTacToeDriver.handleMove(boardGame);
							boardClicked = false;
						}
	    			}
	    	    });
				boardGame.add(cell, colIndex, rowIndex);
				boardGame.setGridLinesVisible(false);
				boardGame.setGridLinesVisible(true);
				break;
	        }
		}
	}

	public void switchTurnGUI() {

		if (turn.equals(player1.getUserName())) {
			turn = player2.getUserName();
			currentTurn.setText("Current Turn: " + turn);
			currentTurn.setFill(Color.BLUE);
		}
		else {
			turn = player1.getUserName();
			currentTurn.setText("Current Turn: " + turn);
			currentTurn.setFill(Color.RED);
		}
	}

	public void updatePlayer1Score() {

		score1.setText(Integer.toString(player1.getScore()));
	}

	public void updatePlayer2Score() {

		score2.setText(Integer.toString(player2.getScore()));
	}

	public void displayWinner(String winner) {

		Alert winnerDialog = new Alert(Alert.AlertType.INFORMATION);
		winnerDialog.setTitle("Game Over");
		if (winner.equals("TIE")) {
			winnerDialog.setHeaderText("It's a TIE!");
		} else {
			winnerDialog.setHeaderText("Congratulation! " + winner + " has won!");
		}
		if (winner.equals(player1.getUserName())){
			switch(gameType) {
			case "TicTacToe":
				LauncherController.incrementTicTacToeForP1();
				break;
//			case "Othello":
//				LauncherController.incrementOthelloForP1();
////				System.out.println(LauncherController.p1Score.getOthello());
//				break;
//			case "Memory":
//				LauncherController.incrementMemoryForP1();
//				break;
//			case "Battleship":
//				LauncherController.incrementBattleshipForP1();
//				break;
//			}
//		} else if (winner.equals(player2.getUserName())) {
//			switch(gameType) {
//			case "TicTacToe":
//				LauncherController.incrementTicTacToeForP2();
//				break;
//			case "Othello":
//				LauncherController.incrementOthelloForP2();
////				System.out.println(LauncherController.p2Score.getOthello());
//				break;
//			case "Memory":
//				LauncherController.incrementMemoryForP2();
//				break;
//			case "Battleship":
//				LauncherController.incrementBattleshipForP2();
//				break;
		}
		}
//		LauncherController.leaderboard.refresh();
		winnerDialog.setContentText("The window will exit after...");
		winnerDialog.showAndWait();
		primaryStage.close();
	}
}
