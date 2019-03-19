package TicTacToe;

import java.io.IOException;

public class TicTacToeLogic {

    int currentPlayer = 1;
    TicTacToePlayer player1;
    TicTacToePlayer player2;
    TicTacToePlayer winner = null;

    public TicTacToeLogic(TicTacToePlayer p1, TicTacToePlayer p2)
    {
        this.player1 = p1;
        this.player2 = p2;
    }



}
