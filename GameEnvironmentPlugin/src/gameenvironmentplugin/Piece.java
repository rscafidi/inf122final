package gameenvironmentplugin;

import java.awt.Graphics;

// this should be the new game piece but keeping this for now.
public abstract class Piece {

    protected final static int SIZE = 50;
    public GameTokenType gameTokenType;

    public Piece(GameTokenType checkerType) {
        this.gameTokenType = checkerType;
    }

    //Method to draw the game piece
    public abstract void draw(Graphics g, int cx, int cy);

    //Contains the boolean
    public static boolean contains(int x, int y, int cx, int cy) {
        return (cx - x) * (cx - x) + (cy - y) * (cy - y) < SIZE / 2
                * SIZE / 2;
    }

    //Get the dimension
    public static int getDimension() {
        return SIZE;
    }
}
