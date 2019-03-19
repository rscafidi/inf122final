package memGame;

import GameEnvironment.Player;

public class MemoryPlayer extends Player{

    // Fields
    //public Card[] flippedCards = new Card[2];
    private Card playerCard;

    public MemoryPlayer(String userName, int turn) {
        super(userName,turn);
    }

    public Card getPlayerCard() { return this.playerCard; }

    /**
     * Incrememts the score field by 1
     */


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
