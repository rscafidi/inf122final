/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameenvironmentplugin;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author hbradt
 */
public class CheckerPiece extends Piece {

    public CheckerPiece(GameTokenType checkerType) {
        super(checkerType);
    }

    @Override
    public void draw(Graphics g, int cx, int cy) {

        int x = cx - SIZE / 2;
        int y = cy - SIZE / 2;
        // Set checker color.

        g.setColor(gameTokenType == GameTokenType.BLACK_REGULAR
                || gameTokenType == GameTokenType.BLACK_KING ? Color.BLACK
                        : Color.RED);

        // Paint checker.
        g.fillOval(x, y, SIZE, SIZE);
        g.setColor(Color.WHITE);
        g.drawOval(x, y, SIZE, SIZE);

        if (gameTokenType == GameTokenType.RED_KING
                || gameTokenType == GameTokenType.BLACK_KING) {
            g.drawString("KING", cx, cy);
        }
    }

}
