package memGame;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.application.Application.launch;


public class DrawMemoryDriver {// extends Application{

//    public static Stage window;
//    public static Scene gameScene;  //startScene,
//    public static Button button;
//    public static ComboBox<Integer> rows;
//    public static ComboBox<Integer> cols;

//    public static GridPane memoryGrid = new GridPane();

//    public static Pane scorePane = new Pane();
//    public static Pane turnPane = new Pane();
//    public static Pane winnerPane = new Pane();

   // MemoryDriver MemoryDriver = new MemoryDriver();

//    Label playerOneLabel = new Label("Player One -- Score: " + MemoryDriver.playerOne.getScore());
//    Label playerTwoLabel = new Label("Player Two -- Score: " + MemoryDriver.playerTwo.getScore());
//    Label currentPlayerTurn = new Label();
//    Label winningPlayer = new Label();

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        window = primaryStage;
//        window.setTitle("Memory Game");
//
//        MemoryBoardGame.initializeGrid();
//
//        memoryGrid.setPadding(new Insets(10,10,10,10));
//        memoryGrid.setGridLinesVisible(true);
//        memoryGrid.setAlignment(Pos.CENTER);
////        memoryGrid.setHgap(10);
////        memoryGrid.setVgap(10);
//        //button = new Button("Finish");
//
////        rows = new ComboBox<>();
////        rows.getItems().addAll(2,4,6,8);
////        rows.setPromptText("Select Number of Rows");
////
////        cols = new ComboBox<>();
////        cols.getItems().addAll(2,4,6,8);
////        cols.setPromptText("Select Number of Columns");
////
////        button.setOnAction(e -> startGame());
////
////        VBox startLayout = new VBox(20);
////        startLayout.setPadding(new Insets(20,20,20,20));
////        startLayout.getChildren().addAll(rows, cols, button);
//        drawGrid();
//
//        gameScene = new Scene(memoryGrid,800,800);
//        window.setScene(gameScene);
//        window.show();
//
//    }

//    public void startGame() {
//        if (rows.getValue() != null && cols.getValue() != null) {
//            //MemoryDriver.chooseGridSize(rows.getValue(), cols.getValue());
//
//
////            scorePane.setTranslateY(75+rows.getValue()*75);
////            turnPane.setTranslateX(75+cols.getValue()*75);
////
////            winnerPane.setTranslateX(75+cols.getValue()*75);
////            winnerPane.setTranslateY(75);
//            //drawMenu();
//
////            scorePane.getChildren().addAll(playerOneLabel,playerTwoLabel);
////            turnPane.getChildren().add(currentPlayerTurn);
////            winnerPane.getChildren().add(winningPlayer);
////
////            HBox hBox = new HBox();
////            hBox.getChildren().addAll(memoryGrid,scorePane,turnPane,winnerPane);
//
//            //StackPane layout2 = new StackPane();
//
//
//        }
//    }

//    public void drawGrid() {
//        for (int r = 0; r < MemoryDriver.rows; r++) {
//            for (int c = 0; c < MemoryDriver.cols; c++) {
//                Pane pane = new Pane();
//                Card card = MemoryDriver.gameGrid[r][c];
//                pane.getChildren().add(card.getImage());
//
//                int row = r;
//                int col = c;
//
//                pane.setOnMouseClicked(event -> {
//                    MemoryPlayer currentPlayer = MemoryDriver.getCurrentPlayer();
//                    Card chosenCard = MemoryDriver.gameGrid[row][col];
//                    if (chosenCard.getState() && !chosenCard.getFlipped()) {  //card chosen has not been matched yet nor has it been flipped yet
//                        drawMoves(row, col);
//                    }
//                });
//
//                memoryGrid.add(pane,c,r);
//                flipDown(card.getImage(), true);
//            }
//        }
//    }

    public void flipUp(ImageView image, Runnable action) {
        System.out.println("flip up called");
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), image);
        ft.setToValue(1);
        ft.setOnFinished(event -> action.run());
        ft.play();
    }

    public void flipDown(ImageView image) {
        System.out.println("flip down called");
//        double delay;
//        if (start) {  //minimize the delay so players cannot see card images yet
//            delay = 0.01;
//        }
//        else {
//            delay = 0.5;
//        }
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), image);
        ft.setToValue(0);
        ft.play();
    }


//    public void drawMenu() {
////        if (MemoryDriver.getCurrentPlayer() == MemoryDriver.playerOne) {
////            currentPlayerTurn.setText("Currently Player One's Turn");
////        }
////        else {
////            currentPlayerTurn.setText("Currently Player Two's Turn");
////        }
////
////        if (MemoryDriver.gameFinished()) {
////            winningPlayer.setText(MemoryDriver.getWinner());
////        }
////
////
////        menuPane.set(playerOneLabel,0,rows.getValue());
////        menuGrid.add(playerTwoLabel,0,rows.getValue()+1);
////        menuGrid.add(currentPlayerTurn,cols.getValue(),0);
//
//    }

//    public void drawMoves(int row, int col) {
//        MemoryPlayer currentPlayer = MemoryDriver.getCurrentPlayer();
//
//        Card card = MemoryDriver.gameGrid[row][col];
//        if (currentPlayer.selectCard(row,col)) {  //player still has moves left in turn
//            flipUp(card.getImage(),() -> {});
//        }
//        else {
//            flipUp(card.getImage(), () -> {
//                if (card.getState()) {  //card is still valid, match was not valid
//                    flipDown(currentPlayer.flippedCards[0].getImage(), false);  //flip both of the selected cards back down
//                    flipDown(currentPlayer.flippedCards[1].getImage(), false);
//                }
//                currentPlayer.resetState();
//            });
//        }
//
//        drawMenu();
//    }

}
