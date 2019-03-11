package gameenvironmentplugin;
import java.awt.Color;
import java.awt.Graphics;

public final class Piece
{
   private final static int DIMENSION = 50;
   public GameTokenType checkerType;

   public Piece(GameTokenType checkerType)
   {
      this.checkerType = checkerType;
   }

   //Method specific to the checkers game
   public void draw(Graphics g, int cx, int cy)
   {
      int x = cx - DIMENSION / 2;
      int y = cy - DIMENSION / 2;

      // Set checker color.

      g.setColor(checkerType == GameTokenType.BLACK_REGULAR ||
                 checkerType == GameTokenType.BLACK_KING ? Color.BLACK : 
                 Color.RED);

      // Paint checker.

      g.fillOval(x, y, DIMENSION, DIMENSION);
      g.setColor(Color.WHITE);
      g.drawOval(x, y, DIMENSION, DIMENSION);

      if (checkerType == GameTokenType.RED_KING || 
          checkerType == GameTokenType.BLACK_KING)
         g.drawString("KING", cx, cy);
   }

   //Contains the boolean
   public static boolean contains(int x, int y, int cx, int cy)
   {
      return (cx - x) * (cx - x) + (cy - y) * (cy - y) < DIMENSION / 2 * 
             DIMENSION / 2;
   }

   //Get the dimension
   public static int getDimension()
   {
      return DIMENSION;
   }
}