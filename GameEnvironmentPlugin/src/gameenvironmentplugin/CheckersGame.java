/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameenvironmentplugin;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author hbradt
 */
public class CheckersGame extends Game {

    public int numRed = 12;   // Number of red left
    public int numBlack = 12; // Number of black left

    @Override
    public GameBoard createGameBoard() {
        //Add pieces to the board
        GameBoard myBoard = new GameBoard();

        // place the red pieces
        myBoard.add(new CheckerPiece(GameTokenType.RED_REGULAR), 1, 2);
        myBoard.add(new CheckerPiece(GameTokenType.RED_REGULAR), 1, 4);
        myBoard.add(new CheckerPiece(GameTokenType.RED_REGULAR), 1, 6);
        myBoard.add(new CheckerPiece(GameTokenType.RED_REGULAR), 1, 8);
        myBoard.add(new CheckerPiece(GameTokenType.RED_REGULAR), 2, 1);
        myBoard.add(new CheckerPiece(GameTokenType.RED_REGULAR), 2, 3);
        myBoard.add(new CheckerPiece(GameTokenType.RED_REGULAR), 2, 5);
        myBoard.add(new CheckerPiece(GameTokenType.RED_REGULAR), 2, 7);
        myBoard.add(new CheckerPiece(GameTokenType.RED_REGULAR), 3, 2);
        myBoard.add(new CheckerPiece(GameTokenType.RED_REGULAR), 3, 4);
        myBoard.add(new CheckerPiece(GameTokenType.RED_REGULAR), 3, 6);
        myBoard.add(new CheckerPiece(GameTokenType.RED_REGULAR), 3, 8);

        // place the black pieces
        myBoard.add(new CheckerPiece(GameTokenType.BLACK_REGULAR), 8, 1);
        myBoard.add(new CheckerPiece(GameTokenType.BLACK_REGULAR), 8, 3);
        myBoard.add(new CheckerPiece(GameTokenType.BLACK_REGULAR), 8, 5);
        myBoard.add(new CheckerPiece(GameTokenType.BLACK_REGULAR), 8, 7);
        myBoard.add(new CheckerPiece(GameTokenType.BLACK_REGULAR), 7, 2);
        myBoard.add(new CheckerPiece(GameTokenType.BLACK_REGULAR), 7, 4);
        myBoard.add(new CheckerPiece(GameTokenType.BLACK_REGULAR), 7, 6);
        myBoard.add(new CheckerPiece(GameTokenType.BLACK_REGULAR), 7, 8);
        myBoard.add(new CheckerPiece(GameTokenType.BLACK_REGULAR), 6, 1);
        myBoard.add(new CheckerPiece(GameTokenType.BLACK_REGULAR), 6, 3);
        myBoard.add(new CheckerPiece(GameTokenType.BLACK_REGULAR), 6, 5);
        myBoard.add(new CheckerPiece(GameTokenType.BLACK_REGULAR), 6, 7);

        return myBoard;
    }

    public boolean isDone() {
        if (numRed == 0) {
            this.winner = "black";
            showMessageDialog(null, "Black Wins!");
            return true;
        }
        if (numBlack == 0) {
            this.winner = "red";
            showMessageDialog(null, "Red Wins!");
            return true;
        }
        return false;
    }
    
    boolean switchTurn(boolean moved, String color) {
        if(moved && color == "red")
        {
            currentMove = "black";
            return true;
        }
        else if(moved && color == "black")
        {
            currentMove = "red";
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean isLegalMove(String currentMove, GameTokenType gameToken) {
        if (currentMove == "black") {
            if (gameToken == GameTokenType.BLACK_KING
                    || gameToken == GameTokenType.BLACK_REGULAR) {
                //do nothing and continue
                System.out.println("Continuing past skip!\n");
            } else {
                showMessageDialog(null, "Black's turn!");
                return false;
            }
        } else if (gameToken == GameTokenType.RED_KING
                || gameToken == GameTokenType.RED_REGULAR) {
            //do nothing and continue
            System.out.println("Continuing past skip!\n");
        } else {
            showMessageDialog(null, "Red's turn!");
            return false;
        }
        return true;
    }
}
