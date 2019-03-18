package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class Main extends Application {

    private static final int WINDOW_HEIGHT = 700;
    private static final int WINDOW_WIDTH = 10;
    private static final int gridSize = 10;
    private static final int BOARD_WDITH = 10;
    private static final int BOARD_HEIGHT = 10;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("bla");

        Scene scene = new Scene(new VBox(), 1300, 1300);

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

        Pane[][] panes = new Pane[BOARD_WDITH][BOARD_HEIGHT];

        for (int i = 0; i < BOARD_WDITH; ++i) {
            for (int j = 0; j < BOARD_HEIGHT; ++j) {
                panes[i][j] = new Pane();
                panes[i][j].setId("_" + i + "_" + j);
//                panes[i][j].setStyle("-fx-background-color: aquamarine");
                panes[i][j].getStylesheets().add("styles.css");
                panes[i][j].getStyleClass().add("pane");
                panes[i][j].setMinWidth(100);
                panes[i][j].setMinHeight(100);
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

    public static void main(String[] args) {

        launch(args);
    }
}
