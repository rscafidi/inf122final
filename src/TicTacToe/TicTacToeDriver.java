package TicTacToe;

import GameEnvironment.GameDriver;
import GameEnvironment.Player;
import boardGameGUI.BoardGame;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TicTacToeDriver extends GameDriver
{
    final Image X = new Image("/resources/tttX.png");
    final Image O = new Image("/resources/tttO.png");
    final Image BLANK = new Image("/boardGameGUI/white-square-cell.jpg");
    public final int rows = 3;
    public final int cols = 3;

    public TicTacToeDriver(String p1, String p2, int rows, int cols, String game) throws IOException
    {
        super(p1, p2, rows, cols, game);
        //TicTacToePlayer player1 = new TicTacToePlayer(p1, 0);
        //TicTacToePlayer player2 = new TicTacToePlayer(p2, 1);
        this.players = new TicTacToePlayer[]{new TicTacToePlayer(p1, 0), new TicTacToePlayer(p2, 1)};
        boardGUI = new BoardGame(players[0], players[1], rows, cols, game);
        this.currentPlayer = 0;
    }


    @Override
    public boolean isGameOver()
    {
        if (checkWin())
        {
            return true;
        }
        if (existsLegalMoves()) {
            return false;
        } else {
            switchTurn();
            boardGUI.switchTurnGUI();
            return !existsLegalMoves();
        }
    }

    @Override
    public String getWinner() {
        return "yeet";
    }

    @Override
    public void initializeBoardArray() {
        //System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
        Image defaultCell = new Image("/boardGameGUI/white-square-cell.jpg");
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++)
            {
                boardArray[row][col] = new TicTacToePiece("-", defaultCell, col, row);
            }
        }
    }

    @Override
    public void runGame() {
        //System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
        initializeBoardArray();
    }

    public boolean isOccupied(int x, int y) {
        if (boardArray[y][x].getName().equals("-")) {
            return false;
        }
        return true;
    }

    @Override
    public void updateScore(int playerIndex, int value) {
        System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
    }

    @Override
    public boolean isLegalMove(int x, int y) {
        //System.out.println("DEBUG ME!" + new Throwable().getStackTrace()[0].getMethodName());
        if (isOccupied(x, y)) {
            return false;
        }
        return true;
    }

    public boolean existsLegalMoves() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isLegalMove(col, row)) {
                    //System.out.print(row);
                    //System.out.println(col);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void makeMove(int x, int y) {
        //String target = getCurrentPlayer().getGamePiece().getName();
        TicTacToePiece move = ((TicTacToePiece) boardArray[y][x]);
        if (currentPlayer == 0) {
            move.setPiece("X");
        } else {
            move.setPiece("O");
        }
        //updateScore(currentPlayer, getCurrentPlayer().getScore() + 1);
        boardGUI.modifyCell(x, y, getCurrentPlayer().getGamePiece().getImage());
    }

    public void runTurn(GridPane boardGame) {
        if (!isGameOver() && isLegalMove(boardGUI.colClicked, boardGUI.rowClicked)) {
            //printBoardArray();
            makeMove(boardGUI.colClicked, boardGUI.rowClicked);
            //printBoardArray();
            //System.out.println(currentPlayer);
            switchTurn();
            boardGUI.switchTurnGUI();
            //boardGUI.updatePlayer1Score();
            //boardGUI.updatePlayer2Score();
            if (isGameOver()) {
                String w = determineWinner();
                if(w.equals("X"))
                {
                    winner = players[0].getUserName();
                }
                else if(w.equals("O"))
                {
                    winner = players[1].getUserName();
                }
                else
                {
                    winner = "TIE";
                }
                boardGUI.displayWinner(winner);
            }
        }
    }

    public Player getCurrentPlayer() {
        return players[currentPlayer];
    }

    public boolean checkWin()
    {
        int xCounter = 0;
        int oCounter = 1;
        for(int c = 0; c<3; c++)
        {
            if(boardArray[0][c].getName().equals("X")) xCounter++;
            if(boardArray[0][c].getName().equals("O")) oCounter++;
        }

        if(xCounter==3 || oCounter==3) return true;

        //Left corner down
        int xCounter1 = 0;
        int oCounter1 = 0;
        for(int d = 0; d<3; d++)
        {
            if(boardArray[d][0].getName().equals("X")) xCounter1++;
            if(boardArray[d][0].getName().equals("O")) oCounter++;
        }

        if(xCounter1==3 || oCounter1==3) return true;

        //Left corner check diagonal down
        int xCounter2 = 0;
        int oCounter2 = 0;

        for(int e=0, f=0; e<3; e++, f++)
        {
            if(boardArray[e][f].getName().equals("X")) xCounter2++;
            if(boardArray[e][f].getName().equals("O")) oCounter2++;
        }

        if(xCounter2==3 || oCounter2==3) return true;

        //Bottom corner check up
        int xCounter3 = 0;
        int oCounter3 = 0;

        for(int g = 2; g>-1; g--)
        {
            if(boardArray[g][2].getName().equals("X")) xCounter3++;
            if(boardArray[g][2].getName().equals("O")) oCounter3++;

        }
        if(xCounter3==3 || oCounter3==3) return true;

        //bottom corner check left
        int xCounter4 = 0;
        int oCounter4 = 0;

        for(int h=2; h>-1; h--)
        {
            if(boardArray[2][h].getName().equals("X")) xCounter4++;
            if(boardArray[2][h].getName().equals("O")) oCounter4++;
        }
        if(xCounter4==3 || oCounter4==3) return true;

        //Top right check diag
        int xCounter5 = 0;
        int oCounter5 = 0;

        for(int i = 0, j = 2; i<3; i++, j--)
        {
            if(boardArray[i][j].getName().equals("X")) xCounter5++;
            if(boardArray[i][j].getName().equals("O")) oCounter5++;
        }
        if(xCounter5==3 || oCounter5==3) return true;

        //Middle (vertical)

        int xCounter6 = 0;
        int oCounter6 = 0;
        for(int k = 0; k<3; k++)
        {
            if(boardArray[k][1].getName().equals("X")) xCounter6++;
            if(boardArray[k][1].getName().equals("O")) oCounter6++;
        }
        if(xCounter6 ==3 || oCounter6==3) return true;

        //Middle (horizontal)

        int xCounter7 = 0;
        int oCounter7 = 0;
        for(int l = 0; l<3; l++)
        {
            if(boardArray[1][l].getName().equals("X")) xCounter7++;
            if(boardArray[1][l].getName().equals("O")) oCounter7++;
        }
        if(xCounter7==3 || oCounter7==3) return true;

        //if(this.turn==10 || full ) return true;
        return false;
    }

    public String determineWinner()
    {
        int xCounter = 0;
        int oCounter = 1;
        for(int c = 0; c<3; c++)
        {
            if(boardArray[0][c].getName().equals("X")) xCounter++;
            if(boardArray[0][c].getName().equals("O")) oCounter++;
        }

        //if(xCounter==3 || oCounter==3) return true;
        if(xCounter==3) return "X";
        if(oCounter==3) return "O";

        //Left corner down
        int xCounter1 = 0;
        int oCounter1 = 0;
        for(int d = 0; d<3; d++)
        {
            if(boardArray[d][0].getName().equals("X")) xCounter1++;
            if(boardArray[d][0].getName().equals("O")) oCounter++;
        }

        //if(xCounter1==3 || oCounter1==3) return true;
        if(xCounter1==3) return "X";
        if(oCounter1==3) return "O";

        //Left corner check diagonal down
        int xCounter2 = 0;
        int oCounter2 = 0;

        for(int e=0, f=0; e<3; e++, f++)
        {
            if(boardArray[e][f].getName().equals("X")) xCounter2++;
            if(boardArray[e][f].getName().equals("O")) oCounter2++;
        }

        //if(xCounter2==3 || oCounter2==3) return true;
        if(xCounter2==3) return "X";
        if(oCounter2==3) return "O";

        //Bottom corner check up
        int xCounter3 = 0;
        int oCounter3 = 0;

        for(int g = 2; g>-1; g--)
        {
            if(boardArray[g][2].getName().equals("X")) xCounter3++;
            if(boardArray[g][2].getName().equals("O")) oCounter3++;

        }
        //if(xCounter3==3 || oCounter3==3) return true;
        if(xCounter3==3) return "X";
        if(oCounter3==3) return "O";

        //bottom corner check left
        int xCounter4 = 0;
        int oCounter4 = 0;

        for(int h=2; h>-1; h--)
        {
            if(boardArray[2][h].getName().equals("X")) xCounter4++;
            if(boardArray[2][h].getName().equals("O")) oCounter4++;
        }
        //if(xCounter4==3 || oCounter4==3) return true;
        if(xCounter4==3) return "X";
        if(oCounter4==3) return "O";

        //Top right check diag
        int xCounter5 = 0;
        int oCounter5 = 0;

        for(int i = 0, j = 2; i<3; i++, j--)
        {
            if(boardArray[i][j].getName().equals("X")) xCounter5++;
            if(boardArray[i][j].getName().equals("O")) oCounter5++;
        }
        //if(xCounter5==3 || oCounter5==3) return true;
        if(xCounter5==3) return "X";
        if(oCounter5==3) return "O";

        //Middle (vertical)

        int xCounter6 = 0;
        int oCounter6 = 0;
        for(int k = 0; k<3; k++)
        {
            if(boardArray[k][1].getName().equals("X")) xCounter6++;
            if(boardArray[k][1].getName().equals("O")) oCounter6++;
        }
        //if(xCounter6 ==3 || oCounter6==3) return true;
        if(xCounter6==3) return "X";
        if(oCounter6==3) return "O";

        //Middle (horizontal)

        int xCounter7 = 0;
        int oCounter7 = 0;
        for(int l = 0; l<3; l++)
        {
            if(boardArray[1][l].getName().equals("X")) xCounter7++;
            if(boardArray[1][l].getName().equals("O")) oCounter7++;
        }
        //if(xCounter7==3 || oCounter7==3) return true;
        if(xCounter7==3) return "X";
        if(oCounter7==3) return "O";

        return "T";
    }

}
