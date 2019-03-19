package memGame;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;

import static javafx.application.Application.launch;


public class DrawMemoryDriver {// extends Application{

    public static MemoryDriver memoryDriver;

    public DrawMemoryDriver(String player1Name, String player2Name, int rows, int cols, String gameName) {
        try {
            memoryDriver = new MemoryDriver(player1Name, player2Name, rows, cols, gameName);
            memoryDriver.runGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleMove(GridPane boardGame) {
        memoryDriver.runTurn(boardGame);
    }

    public static void flipUp(GridPane boardGame, int colIndex, int rowIndex, Image image, Runnable action) {
        ObservableList<Node> childrens = boardGame.getChildren();
        for (Node node : childrens) {
            if (GridPane.getRowIndex(node) == rowIndex && GridPane.getColumnIndex(node) == colIndex) {
                Pane cell = new Pane(new ImageView(image));
                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), cell);
                ft.setToValue(1);
                ft.setOnFinished(event -> action.run());
                boardGame.add(cell,colIndex,rowIndex);
                ft.play();
                break;
            }
        }
    }


}
