package memGame;

import GameEnvironment.GamePiece;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends GamePiece{

    // Fields

    //private String name;
    private ImageView imageView;
    private int row;
    private int col;
    private boolean flipped;
    private boolean alive;



    // Constructors

    public Card(String name, Image image, int row, int col) {
        super(name,image);
        //this.name = name;
        //this.image = new ImageView(image);
        this.imageView = new ImageView(image);
        this.flipped = false;
        this.alive = true;
        this.row = row;
        this.col = col;
    }




    // Methods

//    /**
//     * Returns the name field
//     * @return the name field
//     */
//    public String getName() {
//        return this.name;
//    }
//
    public ImageView getImageView() { return this.imageView; }

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
