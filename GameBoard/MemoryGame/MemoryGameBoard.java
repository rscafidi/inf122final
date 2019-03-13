package MemoryGame;

import GameEnvironment.GameDriver;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import GameEnvironment.*;

public class MemoryGameBoard {

    // Fields
    public static MemoryPlayer playerOne = new MemoryPlayer();
    public static MemoryPlayer playerTwo = new MemoryPlayer();

    public static Card[][] gameGrid;

    public static CardNames cardNames = new CardNames();

    public static int currentTurn = 0;  //player one goes first

    public static final int rows = 6;
    public static final int cols = 6;
    public static int numCards = (rows * cols) / 2;


    // Constructors
    public MemoryGameBoard() { }


    // Methods
    /**
     * Takes the row and col fields and sets up the game board with Card objects for play
     */
    public static void setGameGrid() {
        gameGrid = new Card[rows][cols];

        ArrayList<String> cardNamesChosen = new ArrayList<String>(Arrays.asList(cardNames.getAllPossibleCardNames()));

        int numSelected = 0;
        while (numSelected < numCards) {
            int index = (int)(Math.random() * cardNamesChosen.size());
            String name = cardNamesChosen.get(index);

            int cardsSelected = 0;
            while (cardsSelected < 2) {
                int rowIndex = (int)(Math.random() * rows);
                int colIndex = (int)(Math.random() * cols);
                if (gameGrid[rowIndex][colIndex] == null) {
                    String imgURL = "MemoryGame/Resources/" + name + ".png";
                    gameGrid[rowIndex][colIndex] = new Card(name,new Image(new File(imgURL).toURI().toString(),75,75,true,false),rowIndex,colIndex);
                    String file = new File(imgURL).toURI().toString();
                    System.out.println(file);
                    cardsSelected++;
                }
            }
            cardNamesChosen.remove(index);
            numSelected++;
        }
    }

    /**
     * Changes the player's turns
     * 0 - playerOne's turn
     * 1 - playerTwo's turn
     */
    public static void changeTurn() {
        if (currentTurn == 0) {
            currentTurn = 1;
        }
        else {
            currentTurn = 0;
        }
    }

    /**
     * Returns the current player
     * @return playerOne if currentTurn is 0, playerTwo if currentTurn is 1
     */
    public static MemoryPlayer getCurrentPlayer() {
        if (currentTurn == 0) {
            return playerOne;
        }
        else {
            return playerTwo;
        }
    }

    /**
     * Gets the two card's locations and sets those cards as null to represent that they've already been matched and
     * are now unselectable
     * @param card1 the first card to be set as null
     * @param card2 the second card to be set as null
     */
    public static void removeCards(Card card1, Card card2) {
        //setting element to null indicates the card no longer exists, should no longer be displayed in GUI
        gameGrid[card1.getRow()][card1.getCol()].changeState();
        gameGrid[card2.getRow()][card2.getCol()].changeState();
        numCards--;
    }

    /**
     * Checks to see if the two cards are matching
     * @param card1 the first card the player selected
     * @param card2 the second card the player selected
     * @return true if cards match and false if cards don't match
     */
    public static boolean findMatch(Card card1, Card card2) {
        changeTurn();
        if (card1.getName().equals(card2.getName())) {
            removeCards(card1,card2);
            return true;
        }
        card1.flip();
        card2.flip();
        return false;
    }

    /**
     * Checks to see if there are any more cards left in the board.
     * @return true if there are no cards left, false if there are still cards left
     */
    public static boolean gameFinished() {
        if (numCards == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Determines the winner of the game based on the players' scores
     * @return a string stating the winner of the match, or if it's a tie
     */
    public static String getWinner() {
        if (playerOne.getScore() > playerTwo.getScore()) {
            return "PLAYER ONE";
        }
        else if (playerTwo.getScore() > playerOne.getScore()) {
            return "PLAYER TWO";
        }
        else {
            return "TIE";
        }
    }

}
