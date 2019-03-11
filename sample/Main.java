package sample;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class Main extends Application {

    private static final int WINDOW_HEIGHT = 700;
    private static final int WINDOW_WIDTH = 10;
    private static final int gridSize = 10; //what is purpose of this???
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 10;
    private static final int CARRIER = 5;
    private static final int BATTLESHIP = 4;
    private static final int CRUISER = 3;
    private static final int SUBMARINE = 3;
    private static final int DESTROYER = 2;


    private static int Ships[][] = new int[BOARD_WIDTH][BOARD_HEIGHT];


    @Override
    public void start(Stage primaryStage) throws Exception {
        //random creation of a board called ships with some tiles marked as having a ship in them
        for (int i = 0; i < BOARD_WIDTH; ++i) {
            for (int j = 0; j < BOARD_HEIGHT; ++j) {
                Ships[i][j] = 0;
            }
        }

        Ships[9][5] = 1;
        Ships[9][4] = 1;
        Ships[9][3] = 1;
        ////////////////////////////////////////////////////////




        primaryStage.setTitle("BoardTests");

        Scene scene = new Scene(new VBox(), 1000, 1000);

        BorderPane borderPane = new BorderPane();

        borderPane.setMaxHeight(-Infinity);
        borderPane.setMaxWidth(-Infinity);
        borderPane.setPrefHeight(WINDOW_HEIGHT);
        borderPane.setPrefWidth(WINDOW_WIDTH);

        MenuBar menuBar = new MenuBar();
        menuBar.setMinWidth(300);
        Menu gameMenu = new Menu();
        gameMenu.setText("Game");
        Menu helpMenu = new Menu();
        helpMenu.setText("Help");

        menuBar.getMenus().addAll(gameMenu, helpMenu);


        Pane mainPane = new Pane();

        GridPane gridPaneMain = new GridPane();
        gridPaneMain.setGridLinesVisible(true);
        gridPaneMain.setMinHeight(1000);
        gridPaneMain.setMinWidth(1000);

        gridPaneMain.setHgap(1);
        gridPaneMain.setVgap(1);

        int xId = 0;
        int yId = 0;

        Pane[][] panes = new Pane[BOARD_WIDTH][BOARD_HEIGHT];

        for (int i = 0; i < BOARD_WIDTH; ++i) {
            for (int j = 0; j < BOARD_HEIGHT; ++j) {
                panes[i][j] = new Pane();
                panes[i][j].setId("_" + i + "_" + j);
//                panes[i][j].setStyle("-fx-background-color: aquamarine");
                panes[i][j].getStylesheets().add("styles.css");
                panes[i][j].getStyleClass().add("pane");
                panes[i][j].setMinWidth(50);
                panes[i][j].setMinHeight(50);
                panes[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, new PaneClickEventHandler());
                gridPaneMain.add(panes[i][j], i, j,1,1);
            }
        }

        mainPane.getChildren().add(gridPaneMain);

        borderPane.setCenter(mainPane);
        borderPane.setTop(menuBar);

        ((VBox) scene.getRoot()).getChildren().addAll(borderPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private class PaneClickEventHandler implements EventHandler<Event>{
        @Override
        public void handle(Event evt) {
            Pane source = (Pane)evt.getSource();
            onTileClick(source);
        }
    }

    private void onTileClick(Pane p) {
        p.getStyleClass().removeAll();
        int Col = Integer.parseInt(p.getId().substring(1,2));
        int Row = Integer.parseInt(p.getId().substring(3,4));
        if (Ships[Col][Row] == 0) {
            p.getStyleClass().add("clickedAndMiss");
        } else {
            p.getStyleClass().add("clickedAndHit");
        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}
