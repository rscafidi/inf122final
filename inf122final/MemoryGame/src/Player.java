public class Player {

    // Fields
    private int score = 0;
    private Card[] flippedCards = new Card[2];


    // Constructors
    public Player() { }


    // Methods

    /**
     * Returns the score the player currently has
     * @return the score field
     */
    public int getScore() {
        return this.score;
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
     * Reset the player's flipped cards to null, or no cards currently flipped
     */
    public void resetFlippedCards() {
        flippedCards[0] = null;
        flippedCards[1] = null;
    }

    /**
     * Gets the card the player selected to flip and puts it in the flippedCards array
     * If the selected card is the second selected in a pair, call checkFlippedCards and resetFlippedCards
     * @param row the row of the card the player selected
     * @param col the col of the card the player selected
     */
    public void selectCard(int row, int col) {
        //Player selects row and col using GUI on gameGrid, where a valid Card must be there
        if (MemoryGameBoard.gameGrid[row][col] != null) {
            if (flippedCards[0] == null) {
                flippedCards[0] = MemoryGameBoard.gameGrid[row][col];
                MemoryGameBoard.gameGrid[row][col].flip();
            }
            else if (flippedCards[1] == null) {
                flippedCards[1] = MemoryGameBoard.gameGrid[row][col];
                MemoryGameBoard.gameGrid[row][col].flip();
                this.checkFlippedCards();
                this.resetFlippedCards();
            }
        }
    }

}
