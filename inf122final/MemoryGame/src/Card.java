public class Card {

    // Fields

    private String name;
    private int row;
    private int col;
    private boolean flipped = false;



    // Constructors

    public Card(String name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
    }




    // Methods

    /**
     * Returns the name field
     * @return the name field
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the row this card is located at
     * @return row field
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns the column this card is located at
     * @return col field
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Sets the flipped field to true to show that this card is flipped
     */
    public void flip() {
        this.flipped = true;
    }

}
