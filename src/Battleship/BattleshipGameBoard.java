package Battleship;

import boardGameGUI.BoardGame;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;

public class BattleshipGameBoard extends BoardGame  {

    static final int BOARD_WIDTH = 10;
    static final int BOARD_HEIGHT = 10;
    static final int CARRIER = 5;
    static final int BATTLESHIP = 4;
    static final int CRUISER = 3;
    static final int SUBMARINE = 2;
    static final int DESTROYER = 1;

    static Image defaultBoardCell = new Image("/Battleship/ocean-cell.png");
    static Image hitBoardCell = new Image("/Battleship/ocean-hit-cell.png");
    static Image missBoardCell = new Image("/Battleship/ocean-miss-cell.png");
    static Image hoverCell = new Image("/Battleship/ocean-cell-hover.png");

    BattleshipGameLogic Logic;

    public BattleshipGameBoard (BattleshipPlayer player1, BattleshipPlayer player2,
                                int rows, int cols, String gameName) throws IOException {
        super(player1, player2, rows, cols, gameName);
        Logic = new BattleshipGameLogic(player1, player2);
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
                        Logic.makeMove(rowClicked, colClicked);
                        if (Logic.winner != null) {
                            displayWinner(Logic.winner.getUserName());
                        }
                        updateGameBoard(Logic);
                        switchTurnGUI();
                        updatePlayer1Score();
                        updatePlayer2Score();
                    }

                });

                if (image.equals(defaultBoardCell)) {
                    cell.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent e) {
                                    cell.getChildren().set(0, new ImageView(hoverCell));
                                }
                            });

                    cell.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent e) {
                                    cell.getChildren().set(0, new ImageView(image));
                                }
                            });
                }

                boardGame.add(cell,colIndex,rowIndex);
                boardGame.setGridLinesVisible(false);
                boardGame.setGridLinesVisible(true);
                break;
            }
        }
    }

    void updateGameBoard(BattleshipGameLogic logic) { //updates so GUI shows current player's ocean of hits
        
        for (int i = 0; i < logic.currentPlayer().playerOceanHits.length; i++ ) {
            for (int j = 0; j < logic.currentPlayer().playerOceanHits[i].length; j++ ) {
                if (logic.currentPlayer().playerOceanHits[i][j] == 1) {
                    modifyCell(j, i, hitBoardCell);
                } else if (logic.currentPlayer().playerOceanHits[i][j] == 2) {
                    modifyCell(j, i, missBoardCell);
                } else {
                    modifyCell(j, i, defaultBoardCell);
                }
            }
        }
    }

    public void switchTurnGUI() {
        
        if (Logic.currPlayer == 1) {
            currentTurn.setText("Current Turn: " + player1.getUserName());
            currentTurn.setFill(Color.BLUE);
        } else if (Logic.currPlayer == 2) {
            currentTurn.setText("Current Turn: " + player2.getUserName());
            currentTurn.setFill(Color.RED);
        }
    }

    public void updatePlayer1Score() {
        
        score1.setText(Integer.toString(player1.getScore()));
    }

    public void updatePlayer2Score() {
        
        score2.setText(Integer.toString(player2.getScore()));
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

    public void addCell(int rowIndex, int colIndex, Image image) {
        
        Pane cell = new Pane(new ImageView(image));
        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                rowClicked = rowIndex;
                colClicked = colIndex;
                boardClicked = true;
                Logic.makeMove(rowClicked, colClicked);
                if (Logic.winner != null) {
                    displayWinner(Logic.winner.getUserName());
                }
                updateGameBoard(Logic);
                switchTurnGUI();
                updatePlayer1Score();
                updatePlayer2Score();
            }
        });

        cell.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        cell.getChildren().set(0, new ImageView(hoverCell));
                    }
                });

        cell.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        cell.getChildren().set(0, new ImageView(image));
                    }
                });



        boardGame.add(cell, colIndex, rowIndex);
    }


}
