public class MemoryPlayer {

    // Fields
    private int score = 0;
    //public Card[] flippedCards = new Card[2];
    private Card playerCard;


    // Methods

    /**
     * Returns the score the player currently has
     * @return the score field
     */
    public int getScore() {
        return this.score;
    }

    public Card getPlayerCard() { return this.playerCard; }

    /**
     * Incrememts the score field by 1
     */
    public void incrementScore() {
        this.score++;
    }


    /**
     * Reset the player's flipped cards to null, or no cards currently flipped, and reset selections to 2
     */
    public void resetPlayerCard() {
        this.playerCard = null;
    }

    public void addPlayerCard(Card card) {
        this.playerCard = card;
    }

}
