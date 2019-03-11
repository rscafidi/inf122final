package gameenvironmentplugin;
import java.awt.EventQueue;
import java.util.ArrayList;
import javax.swing.JFrame;

//Public Class
public class GameEnvironmentPlugin extends JFrame
{
   public GameEnvironmentPlugin(String title)
   {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Add pieces to the board
        GameBoard myBoard = new GameBoard();
        myBoard.add(new Piece(GameTokenType.RED_REGULAR), 1, 2);
        myBoard.add(new Piece(GameTokenType.RED_REGULAR), 1, 4);
        myBoard.add(new Piece(GameTokenType.RED_REGULAR), 1, 6);
        myBoard.add(new Piece(GameTokenType.RED_REGULAR), 1, 8);
        myBoard.add(new Piece(GameTokenType.RED_REGULAR), 2, 1);
        myBoard.add(new Piece(GameTokenType.RED_REGULAR), 2, 3);
        myBoard.add(new Piece(GameTokenType.RED_REGULAR), 2, 5);
        myBoard.add(new Piece(GameTokenType.RED_REGULAR), 2, 7);
        myBoard.add(new Piece(GameTokenType.RED_REGULAR), 3, 2);
        myBoard.add(new Piece(GameTokenType.RED_REGULAR), 3, 4);
        myBoard.add(new Piece(GameTokenType.RED_REGULAR), 3, 6);
        myBoard.add(new Piece(GameTokenType.RED_REGULAR), 3, 8);
        myBoard.add(new Piece(GameTokenType.BLACK_REGULAR), 8, 1);
        myBoard.add(new Piece(GameTokenType.BLACK_REGULAR), 8, 3);
        myBoard.add(new Piece(GameTokenType.BLACK_REGULAR), 8, 5);
        myBoard.add(new Piece(GameTokenType.BLACK_REGULAR), 8, 7);
        myBoard.add(new Piece(GameTokenType.BLACK_REGULAR), 7, 2);
        myBoard.add(new Piece(GameTokenType.BLACK_REGULAR), 7, 4);
        myBoard.add(new Piece(GameTokenType.BLACK_REGULAR), 7, 6);
        myBoard.add(new Piece(GameTokenType.BLACK_REGULAR), 7, 8);
        myBoard.add(new Piece(GameTokenType.BLACK_REGULAR), 6, 1);
        myBoard.add(new Piece(GameTokenType.BLACK_REGULAR), 6, 3);
        myBoard.add(new Piece(GameTokenType.BLACK_REGULAR), 6, 5);
        myBoard.add(new Piece(GameTokenType.BLACK_REGULAR), 6, 7);
        setContentPane(myBoard);
        pack();
        setVisible(true);
   }

   //Use this to Create the new game (Should be added to the buttonClick Attribute)
   public static void main(String[] args)
   {
        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                new GameEnvironmentPlugin("Checkers");
            }
        };
        EventQueue.invokeLater(r);
   }
}

