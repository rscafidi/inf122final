package MemoryGame;

import GameEnvironment.Player;

public class MemoryPlayer extends Player {

    // Fields
    private int playerScore;
    public Card[] flippedCards = new Card[2];


    // Constructors
    public MemoryPlayer(String userName, int turn) {
        // TODO
    }


    // Methods
    /**
     * Returns the score the player currently has
     * @return the score field
     */
    public int getScore() {
        return this.playerScore;
    }

    /**
     * Incrememts the score field by 1
     */
    public void incrementScore() {
        this.score++;
    }

    /**
     * Checks to see if the player has matching cards, if so increment score, if not, do nothing
     * @return true if cards matching and false if cards not matching
     */
    public boolean checkFlippedCards() {
        if (MemoryGameBoard.findMatch(flippedCards[0],flippedCards[1])) {
            this.incrementScore();
            return true;
        }
        return false;
    }

    /**
     * Reset the player's flipped cards to null, or no cards currently flipped, and reset selections to 2
     */
    public void resetState() {
        flippedCards[0] = null;
        flippedCards[1] = null;
    }


    /**
     * Gets the card the player selected to flip and puts it in the flippedCards array
     * If the selected card is the second selected in a pair, call checkFlippedCards
     * @param row the row of the card the player selected
     * @param col the col of the card the player selected
     * @return true if another move can be made by that player; else false
     */
    public boolean selectCard(int row, int col) {
        //Player selects row and col using GUI on gameGrid, where a valid Card must be there
        if (flippedCards[0] == null) {
            flippedCards[0] = MemoryGameBoard.gameGrid[row][col];
            MemoryGameBoard.gameGrid[row][col].flip();
            return true;
        }
        else if (flippedCards[1] == null) {
            flippedCards[1] = MemoryGameBoard.gameGrid[row][col];
            MemoryGameBoard.gameGrid[row][col].flip();
            this.checkFlippedCards();
            return false;
        }
        return false;
    }

}
