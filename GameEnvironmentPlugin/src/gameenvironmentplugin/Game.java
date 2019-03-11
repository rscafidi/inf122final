package gameenvironmentplugin;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;

public class Game
{
    public String winner;
    void myRules()
    {
        //Add dummy code and abstract from here
    }
    
    boolean switchTurn(boolean moved)
    {
        return moved;
    }
    
    String getWinner()
    {
        return winner;
    }
}