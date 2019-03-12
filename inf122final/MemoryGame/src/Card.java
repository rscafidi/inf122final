import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Card {

    // Fields

    private String name;
    private ImageView imageView;
    private int row;
    private int col;
    private boolean flipped = false;
    private boolean state = true;



    // Constructors

    public Card(String name, Image image, int row, int col) {
        this.name = name;
        this.imageView = new ImageView(image);
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
     * Returns the Image of the card
     * @return imageView field
     */
    public ImageView getImage() { return this.imageView; }

    /**
     * Returns the state of the card
     * @return state field
     */
    public boolean getState() { return this.state; }

    /**
     * Returns whether or not the card is flipped
     * @return flipped field
     */
    public boolean getFlipped() {
        return this.flipped;
    }

    /**
     * Changes the state of the Card to false
     */
    public void changeState() { this.state = false; }

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
        if (this.flipped) {
            this.flipped = false;
        }
        else {
            this.flipped = true;
        }
    }


}
