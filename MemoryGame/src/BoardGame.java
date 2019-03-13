import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BoardGame {
    private GridPane boardGame;
    private int rowClicked = 0, colClicked = 0;
    //Make sure to reset the boardClicked to false after you made the move
    private boolean boardClicked = false;

    public BoardGame(int rows, int cols, String gameName) throws IOException {
        Stage primaryStage = new Stage();
        boardGame = new GridPane();
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
        primaryStage.setTitle(gameName);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        boardGame.setGridLinesVisible(true);
        primaryStage.setScene(new Scene(boardGame, 600, 600));
        primaryStage.show();
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
    }


    public void addCell(int rowIndex, int colIndex, Image image) {
        Pane cell = new Pane(new ImageView(image));
        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                rowClicked = rowIndex;
                colClicked = colIndex;
                boardClicked = true;
                handleMove();
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
                        handleMove();
                    }

                });
            }
        }
    }

    public void handleMove() {
        if (MemoryDriver.checkValidMove(rowClicked,colClicked)) {
//            Card card = MemoryBoardGame.grid[rowClicked][colClicked];
//            MemoryPlayer player = MemoryDriver.getPlayer(M)
//
//            flipUp(card.getImage(),);
        }

    }

    public void flipUp(ImageView image, Runnable action) {
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), image);
        ft.setToValue(1);
        ft.setOnFinished(event -> action.run());
        ft.play();
    }


    public void flipDown(ImageView image, boolean start) {
        double delay;
        if (start) {  //minimize the delay so players cannot see card images yet
            delay = 0.01;
        }
        else {
            delay = 0.5;
        }
        FadeTransition ft = new FadeTransition(Duration.seconds(delay), image);
        ft.setToValue(0);
        ft.play();
    }

}



