/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameenvironmentplugin;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class GameEnvironmentPlugin extends JFrame
{
   public GameEnvironmentPlugin(String title)
   {
      super(title);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      Board board = new Board();
       
        board.add(new Checker(CheckerType.RED_REGULAR), 1, 2);
        board.add(new Checker(CheckerType.RED_REGULAR), 1, 4);
        board.add(new Checker(CheckerType.RED_REGULAR), 1, 6);
        board.add(new Checker(CheckerType.RED_REGULAR), 1, 8);
        board.add(new Checker(CheckerType.RED_REGULAR), 2, 1);
        board.add(new Checker(CheckerType.RED_REGULAR), 2, 3);
        board.add(new Checker(CheckerType.RED_REGULAR), 2, 5);
        board.add(new Checker(CheckerType.RED_REGULAR), 2, 7);
        board.add(new Checker(CheckerType.RED_REGULAR), 3, 2);
        board.add(new Checker(CheckerType.RED_REGULAR), 3, 4);
        board.add(new Checker(CheckerType.RED_REGULAR), 3, 6);
        board.add(new Checker(CheckerType.RED_REGULAR), 3, 8);
      
      

        board.add(new Checker(CheckerType.BLACK_REGULAR), 8, 1);
        board.add(new Checker(CheckerType.BLACK_REGULAR), 8, 3);
        board.add(new Checker(CheckerType.BLACK_REGULAR), 8, 5);
        board.add(new Checker(CheckerType.BLACK_REGULAR), 8, 7);
        board.add(new Checker(CheckerType.BLACK_REGULAR), 7, 2);
        board.add(new Checker(CheckerType.BLACK_REGULAR), 7, 4);
        board.add(new Checker(CheckerType.BLACK_REGULAR), 7, 6);
        board.add(new Checker(CheckerType.BLACK_REGULAR), 7, 8);
        board.add(new Checker(CheckerType.BLACK_REGULAR), 6, 1);
        board.add(new Checker(CheckerType.BLACK_REGULAR), 6, 3);
        board.add(new Checker(CheckerType.BLACK_REGULAR), 6, 5);
        board.add(new Checker(CheckerType.BLACK_REGULAR), 6, 7);

     
      setContentPane(board);

      pack();
      setVisible(true);
   }

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