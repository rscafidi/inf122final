package boardGameGUI;

import java.io.IOException;
import GameEnvironment.Player;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import memGame.DrawMemoryDriver;

public class BoardGame {
	private BorderPane layout;
	private GridPane boardGame;
	private String turn;
	private Player player1, player2;
	private VBox infoPanel, nameScore1, nameScore2;
	private HBox nameScoreHolder;
	private Text currentTurn, p1Name, p2Name, score1, score2;
	private Stage primaryStage;

	public int rowClicked = 0, colClicked = 0;
	//Make sure to reset the boardClicked to false after you made the move
	public boolean boardClicked = false;


	public BoardGame(Player player1, Player player2, int rows, int cols, String gameName) throws IOException {
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
				initializeBoard(rows, cols, "path/to/tictactoe/cell");
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
		Pane cell = new Pane(new ImageView(image));
		cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				rowClicked = rowIndex;
				colClicked = colIndex;
				boardClicked = true;
				DrawMemoryDriver.handleMove(boardGame);
				boardClicked = false;
			}
		});
		boardGame.add(cell, colIndex, rowIndex);
	}

	public void modifyCell(int colIndex, int rowIndex, Image image) {
		ObservableList<Node> childrens = boardGame.getChildren();
		for (Node node : childrens) {
			if(GridPane.getRowIndex(node) == rowIndex && GridPane.getColumnIndex(node) == colIndex) {
				Pane cell = new Pane(new ImageView(image));
				cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						rowClicked = rowIndex;
						colClicked = colIndex;
						boardClicked = true;
						DrawMemoryDriver.handleMove(boardGame);
						boardClicked = false;
					}
				});
				boardGame.add(cell,colIndex,rowIndex);
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
		if (!winner.equals("TIE")) {
			winnerDialog.setHeaderText("Congratulation! " + winner + " has won!");
		}
		else {
			winnerDialog.setHeaderText("The game has ended in a TIE!");
		}
		winnerDialog.setContentText("The window will exit after...");
		winnerDialog.showAndWait();
		primaryStage.close();
	}
}


