import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Card {

    // Fields

    private String name;
    private String orientation;
    private ImageView image;
    private int row;
    private int col;
    private boolean flipped;
    private boolean alive;



    // Constructors

    public Card(String name, Image image, int row, int col) {
        this.name = name;
        this.image = new ImageView(image);
        this.flipped = false;
        this.alive = true;
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

    public ImageView getImage() { return this.image; }

    public boolean getStatus() { return this.alive; }

    public boolean getFlipped() { return this.flipped; }

    public void changeStatus() { this.alive = false; }

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



}
