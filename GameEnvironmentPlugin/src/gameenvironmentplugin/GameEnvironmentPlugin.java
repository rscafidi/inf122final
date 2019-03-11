package gameenvironmentplugin;
import java.awt.EventQueue;
import java.util.ArrayList;
import javax.swing.JFrame;

//Public Class
public class GameEnvironmentPlugin extends JFrame
{
    
    // Place holder for the game
    Game myGame = new CheckersGame();
    
   public GameEnvironmentPlugin(String title)
   {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Add pieces to the board
        GameBoard myBoard = myGame.createGameBoard();
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

