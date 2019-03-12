public class Player {

    private int score = 0;
    private Card[] flippedCards = new Card[2];

    public Player() { }

    public int getScore() {
        return this.score;
    }

    public void incrementScore() {
        this.score++;
    }

    public boolean checkFlippedCards() {
        if (MemoryGameBoard.findMatch(flippedCards[0],flippedCards[1])) {
            this.incrementScore();
            return true;
        }
        return false;
    }

    public void resetFlippedCards() {
        flippedCards[0] = null;
        flippedCards[1] = null;
    }

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
