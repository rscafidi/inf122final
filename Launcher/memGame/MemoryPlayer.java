package memGame;

import GameEnvironment.Player;

public class MemoryPlayer extends Player{

    // Fields
    private int gameScore = 0;
    //public Card[] flippedCards = new Card[2];
    private Card playerCard;

    public MemoryPlayer(String userName, int turn) {
        super(userName,turn);
    }

    // Methods

    /**
     * Returns the score the player currently has
     * @return the score field
     */
    public int getScore() {
        return this.gameScore;
    }

    public Card getPlayerCard() { return this.playerCard; }

    /**
     * Incrememts the score field by 1
     */
    public void incrementScore() {
        this.gameScore++;
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
