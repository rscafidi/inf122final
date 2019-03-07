public class Card {

    private String name;
    private int row;
    private int col;
    private boolean flipped = false;

    public Card(String name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
    }

    public String getName() {
        return this.name;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void flip() {
        this.flipped = true;
    }

}
