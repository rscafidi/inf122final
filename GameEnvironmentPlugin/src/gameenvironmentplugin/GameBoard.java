package gameenvironmentplugin;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import static javax.swing.JOptionPane.showMessageDialog;

public class GameBoard extends JComponent
{
   public final static int TILESIZE = (int) (Piece.getDimension() * 1.25);
   public final int BOARDSIZE = 8 * TILESIZE;
   public Dimension boardSize;                 // To get the size of the board
   public boolean isCurrentlyMoving = false;   // Check if it is currently moving
   public int deltax, deltay;
   public GamePiece gameIndex;                 // The index location
   public int oldcx, oldcy;                    // Old pixel position
   public int oldx, oldy;                      // Old x and y coordinate position
   public List<GamePiece> gameIndexes;         // The list of game pieces and locations
   public String currentMove;                  // Whose move it is
   public int numRed;                          // Number of red left
   public int numBlack;                        // Number of black left
   public Game myGame;

   //Call the rules function in the constructor
   public GameBoard()
   {
    myGame = new Game();
    //Initialize arrays
    gameIndexes = new ArrayList<>();
    boardSize = new Dimension(BOARDSIZE, BOARDSIZE);
    currentMove = "black";
    numRed = 12;
    numBlack = 12;
    myRules();
   }
   
   //Switch the turn
   public void switchTurn(boolean moved)
   {
        if(myGame.switchTurn(moved) && currentMove == "red")
        {
            currentMove = "black";
        }
        else if(myGame.switchTurn(moved) && currentMove == "black")
        {
            currentMove = "red";
        }
   }
   
   public String getWinner()
   {
       return myGame.getWinner();
   }
   
   //Rules to move game pieces
   public void myRules()
   {      
      addMouseListener(new MouseAdapter()
      {
        @Override
        public void mousePressed(MouseEvent me)
        {
             if(numRed == 0)
             {
                 showMessageDialog(null, "Black wins!");
                 myGame.winner = "black";
                 return;
             }
             if(numBlack == 0)
             {
                 showMessageDialog(null, "Red Wins!");
                 myGame.winner = "red";
                 return;
             }
            // Obtain mouse coordinates at time of press.
            int x = me.getX();
            int y = me.getY();

            // Locate positioned checker under mouse press.
            for (GameBoard.GamePiece gameIndex: gameIndexes)
            if (Piece.contains(x, y, gameIndex.cx, gameIndex.cy))
            {
                GameBoard.this.gameIndex = gameIndex;
                oldcx = gameIndex.cx;
                oldcy = gameIndex.cy;
                oldx = ((oldcx - TILESIZE/2)/TILESIZE + 1);
                oldy = ((oldcy - TILESIZE/2)/TILESIZE + 1);
                deltax = x - gameIndex.cx;
                deltay = y - gameIndex.cy;
                isCurrentlyMoving = true;
                return;
            }
         }

       @Override
       public void mouseReleased(MouseEvent me)
       {
            //Check here
            if (isCurrentlyMoving)
            {
                isCurrentlyMoving = false;
            }
            else
            {
                return;
            }
            
            if(numRed == 0)
             {
                 showMessageDialog(null, "Black wins!");
                 myGame.winner = "black";
                 return;
             }
             if(numBlack == 0)
             {
                 showMessageDialog(null, "Red Wins!");
                 myGame.winner = "red";
                 return;
             }
             
            //Check that a black checker is moving
            if(currentMove == "black")
            {
                if(GameBoard.this.gameIndex.checker.checkerType == GameTokenType.BLACK_KING || 
                        GameBoard.this.gameIndex.checker.checkerType == GameTokenType.BLACK_REGULAR)
                {
                    //do nothing and continue
                    System.out.println("Continuing past skip!\n");
                }
                else
                {
                    showMessageDialog(null, "Black's turn!");
                    GameBoard.this.gameIndex.cx = oldcx;
                    GameBoard.this.gameIndex.cy = oldcy;
                    GameBoard.this.gameIndex.x = oldx;
                    GameBoard.this.gameIndex.y = oldy;
                    repaint();
                    return;
                }
            }
            else
            {
                if(GameBoard.this.gameIndex.checker.checkerType == GameTokenType.RED_KING || 
                        GameBoard.this.gameIndex.checker.checkerType == GameTokenType.RED_REGULAR)
                {
                    //do nothing and continue
                    System.out.println("Continuing past skip!\n");
                }
                else
                {
                    showMessageDialog(null, "Red's turn!");
                    GameBoard.this.gameIndex.cx = oldcx;
                    GameBoard.this.gameIndex.cy = oldcy;
                    GameBoard.this.gameIndex.x = oldx;
                    GameBoard.this.gameIndex.y = oldy;
                    repaint();
                    return;
                }
            }
                       
            //Stuff the checker into the position
            boolean moved = true;
            int x = me.getX();
            int y = me.getY();
            gameIndex.cx = (x - deltax) / TILESIZE * TILESIZE + TILESIZE / 2;
            gameIndex.cy = (y - deltay) / TILESIZE * TILESIZE + TILESIZE / 2;
            GameBoard.this.gameIndex.x = ((GameBoard.this.gameIndex.cx - TILESIZE/2)/TILESIZE + 1);
            GameBoard.this.gameIndex.y = ((GameBoard.this.gameIndex.cy - TILESIZE/2)/TILESIZE + 1);
            
            //Do not move checker onto an occupied square.
            for (GameBoard.GamePiece gameIndex: gameIndexes)
                if (gameIndex != GameBoard.this.gameIndex && 
                gameIndex.cx == GameBoard.this.gameIndex.cx &&
                gameIndex.cy == GameBoard.this.gameIndex.cy)
                {
                    System.out.println("Could not add to occupied square!\n");
                    GameBoard.this.gameIndex.cx = oldcx;
                    GameBoard.this.gameIndex.cy = oldcy;
                    GameBoard.this.gameIndex.x = oldx;
                    GameBoard.this.gameIndex.y = oldy;
                    moved = false;
                }
            
            System.out.println("Continuing past onto the rules!\n"); 
            System.out.println("X = " + GameBoard.this.gameIndex.x + " oldx = " + oldx);
            System.out.println("Y = " + GameBoard.this.gameIndex.y + " oldy = " + oldy);
            
            if((GameBoard.this.gameIndex.x - oldx) == 0 && ((GameBoard.this.gameIndex.y - oldy) == 4 || (GameBoard.this.gameIndex.y - oldy) == -4))
            {
                //Check if x+ or -2 and y == 1/2 is clear
                boolean needCheck = false;
                int halfx = GameBoard.this.gameIndex.x + 2;
                int halfy = (GameBoard.this.gameIndex.y + oldy)/2;
                
                for (GameBoard.GamePiece gameIndex: gameIndexes)
                {
                    if (gameIndex != GameBoard.this.gameIndex && gameIndex.x == GameBoard.this.gameIndex.x + 2
                        && gameIndex.y == (GameBoard.this.gameIndex.y + oldy)/2)
                    {
                        halfx = 100;
                        halfy = 100;
                        needCheck = true;
                        System.out.println("It's not clear");
                        break;
                    }
                }
                
                if(needCheck)
                {
                halfx = GameBoard.this.gameIndex.x - 2;
                halfy = (GameBoard.this.gameIndex.y + oldy)/2;
                for (GameBoard.GamePiece gameIndex: gameIndexes)
                {
                    if (gameIndex != GameBoard.this.gameIndex && gameIndex.x == GameBoard.this.gameIndex.x - 2
                        && gameIndex.y == (GameBoard.this.gameIndex.y + oldy)/2)
                    {
                        halfx = 100;
                        halfy = 100;
                        System.out.println("It's not clear");
                        break;
                    }
                }
                } 
                                  
                System.out.println("Made it to two squares - double jump rare!\n");
                
                //Check the piece jumped across
                GameTokenType myChecker = GameTokenType.UNDEFINED;
                GameBoard.GamePiece toRemove = null;
                int tempx = 100;
                int tempy = 100;
                
                GameTokenType myChecker2 = GameTokenType.UNDEFINED;
                GameBoard.GamePiece toRemove2 = null;
                int tempx2 = 100;
                int tempy2 = 100;
                
                System.out.println("X:= " + GameBoard.this.gameIndex.x + "  Y:= " + GameBoard.this.gameIndex.y);
                System.out.println("oldX:= " + oldx + " oldY:= " + oldy);
                
                for (GameBoard.GamePiece gameIndex: gameIndexes)
                {
                    if (gameIndex != GameBoard.this.gameIndex 
                        && gameIndex.x == (halfx + oldx)/2 
                        && gameIndex.y == (halfy + oldy)/2)
                    {
                        myChecker = gameIndex.checker.checkerType;
                        toRemove = gameIndex;
                        tempx = gameIndex.x;
                        tempy = gameIndex.y;
                        System.out.println("Remove checker X = " + gameIndex.x + "Y = " + gameIndex.y);
                        break;
                    }
                }
                System.out.println("tempx = " + tempx + " tempy = " + tempy);
                for (GameBoard.GamePiece gameIndex2: gameIndexes)
                {
                    if (gameIndex2 != GameBoard.this.gameIndex 
                        && gameIndex2.x == (GameBoard.this.gameIndex.x + halfx)/2 
                        && gameIndex2.y == (GameBoard.this.gameIndex.y + halfy)/2)
                    {
                        myChecker2 = gameIndex2.checker.checkerType;
                        toRemove2 = gameIndex2;
                        tempx2 = gameIndex2.x;
                        tempy2 = gameIndex2.y;
                        System.out.println("Remove checker X = " + gameIndex2.x + "Y = " + gameIndex2.y);
                        break;
                    }
                }
                System.out.println("tempx2= " + tempx2 + " tempy2 = " + tempy2);
                System.out.println(myChecker);
                System.out.println(myChecker2);
                
                //Find the checker located at that region
		if (toRemove != null && toRemove2 != null && GameBoard.this.gameIndex.checker.checkerType == GameTokenType.RED_REGULAR
                        && (GameBoard.this.gameIndex.y - oldy == 4) && 
		    (myChecker == GameTokenType.BLACK_REGULAR || myChecker == GameTokenType.BLACK_KING)
                        && (myChecker2 == GameTokenType.BLACK_REGULAR || myChecker2 == GameTokenType.BLACK_KING))
                {
                    System.out.println("CAME IN HERE!!!!!");
                    moved = true;
                    numBlack--;
                    numBlack--;
                    //Delete the black checker
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx 
                        && GameBoard.this.gameIndexes.get(index).y == tempy)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx2 
                        && GameBoard.this.gameIndexes.get(index).y == tempy2)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                    System.out.println("Red jumped black: Numblack is now = " + numBlack + "\n");
                }
                else if (toRemove != null && toRemove2 != null && GameBoard.this.gameIndex.checker.checkerType == GameTokenType.RED_KING
                        && (Math.abs(GameBoard.this.gameIndex.y - oldy) == 4) && 
		    (myChecker == GameTokenType.BLACK_REGULAR || myChecker == GameTokenType.BLACK_KING)
                        && (myChecker2 == GameTokenType.BLACK_REGULAR || myChecker2 == GameTokenType.BLACK_KING))
                {
                    moved = true;
                    numBlack--;
                    numBlack--;
                    //Delete the black checker
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx 
                        && GameBoard.this.gameIndexes.get(index).y == tempy)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx2 
                        && GameBoard.this.gameIndexes.get(index).y == tempy2)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                    System.out.println("Red jumped black: Numblack is now = " + numBlack + "\n");
                }
                else if(toRemove != null && toRemove2 != null && GameBoard.this.gameIndex.checker.checkerType == GameTokenType.BLACK_REGULAR
                        && (GameBoard.this.gameIndex.y - oldy == -4) && 
		   (myChecker == GameTokenType.RED_REGULAR || myChecker == GameTokenType.RED_KING)
                        && (myChecker2 == GameTokenType.RED_REGULAR || myChecker2 == GameTokenType.RED_KING))
                {
                      moved = true;
                      numRed--;
                      numRed--;
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx 
                        && GameBoard.this.gameIndexes.get(index).y == tempy)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx2 
                        && GameBoard.this.gameIndexes.get(index).y == tempy2)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                      System.out.println("Black jumped red: NumRed is now = " + numRed + "\n");        
                }
                else if(toRemove != null && toRemove2 != null && GameBoard.this.gameIndex.checker.checkerType == GameTokenType.BLACK_KING
                        && (Math.abs(GameBoard.this.gameIndex.y - oldy) == 4) && 
		   (myChecker == GameTokenType.RED_REGULAR || myChecker == GameTokenType.RED_KING)
                        && (myChecker2 == GameTokenType.RED_REGULAR || myChecker2 == GameTokenType.RED_KING))
                {
                      moved = true;
                      numRed--;
                      numRed--;
                      //Delete the red checker
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx 
                        && GameBoard.this.gameIndexes.get(index).y == tempy)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx2 
                        && GameBoard.this.gameIndexes.get(index).y == tempy2)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                      System.out.println("Black jumped red: NumRed is now = " + numRed + "\n");        
                }
                else
                {
                    System.out.println("could not remove!");
                    GameBoard.this.gameIndex.cx = oldcx;
                    GameBoard.this.gameIndex.cy = oldcy;
                    GameBoard.this.gameIndex.x = oldx;
                    GameBoard.this.gameIndex.y = oldy;
                    moved = false;
                } 
            }
            else if(Math.abs(GameBoard.this.gameIndex.x - oldx) == 1) 
            {
                System.out.println("X IS ON THE NEXT LINE!\n");
		if((GameBoard.this.gameIndex.checker.checkerType == GameTokenType.RED_REGULAR) 
                        && (GameBoard.this.gameIndex.y - oldy == 1))
                {
                   System.out.println("Red: X could be moved!\n");
                   moved = true;
                }
                else if((GameBoard.this.gameIndex.checker.checkerType == GameTokenType.RED_KING) 
                        && (Math.abs(GameBoard.this.gameIndex.y - oldy) == 1))
                {
                   System.out.println("Red: X could be moved!\n");
                   moved = true;
                }
                else if((GameBoard.this.gameIndex.checker.checkerType == GameTokenType.BLACK_REGULAR) 
                        && (GameBoard.this.gameIndex.y - oldy == -1))
                {
                    System.out.println("Black: X could be moved!\n");
                    moved = true; 
                }
                else if((GameBoard.this.gameIndex.checker.checkerType == GameTokenType.BLACK_KING)
                        && (Math.abs(GameBoard.this.gameIndex.y - oldy) == 1))
                {
                    System.out.println("Black: X could be moved!\n");
                    moved = true; 
                }
                else
                {
                    GameBoard.this.gameIndex.x = oldx;
                    GameBoard.this.gameIndex.y = oldy;
                    GameBoard.this.gameIndex.cx = oldcx;
                    GameBoard.this.gameIndex.cy = oldcy;
                    moved = false;
                }


	    }
	    // Checks case of a jump
	    else if(Math.abs(GameBoard.this.gameIndex.x - oldx) == 2) 
            {
                System.out.println("Made it to two squares!\n");
                //Check the piece jumped across
                GameTokenType myChecker = GameTokenType.UNDEFINED;
                GameBoard.GamePiece toRemove = null;
                int tempx = 100;
                int tempy = 100;
                for (GameBoard.GamePiece gameIndex: gameIndexes)
                {
                    if (gameIndex != GameBoard.this.gameIndex 
                        && gameIndex.x == (GameBoard.this.gameIndex.x + oldx)/2 
                        && gameIndex.y == (GameBoard.this.gameIndex.y + oldy)/2)
                    {
                        myChecker = gameIndex.checker.checkerType;
                        toRemove = gameIndex;
                        tempx = gameIndex.x;
                        tempy = gameIndex.y;
                        System.out.println("Remove checker X = " + gameIndex.x + "Y = " + gameIndex.y);
                        break;
                    }
                }
                System.out.println(myChecker);
                
                //Find the checker located at that region
		if (toRemove != null && GameBoard.this.gameIndex.checker.checkerType == GameTokenType.RED_REGULAR
                        && (GameBoard.this.gameIndex.y - oldy == 2) && 
		    (myChecker == GameTokenType.BLACK_REGULAR || myChecker == GameTokenType.BLACK_KING))
                {
                    System.out.println("CAME IN HERE!!!!!");
                    moved = true;
                    numBlack--;
                    //Delete the black checker
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx 
                        && GameBoard.this.gameIndexes.get(index).y == tempy)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                    System.out.println("Red jumped black: Numblack is now = " + numBlack + "\n");
                }
                else if (toRemove != null && GameBoard.this.gameIndex.checker.checkerType == GameTokenType.RED_KING
                        && (Math.abs(GameBoard.this.gameIndex.y - oldy) == 2) && 
		    (myChecker == GameTokenType.BLACK_REGULAR || myChecker == GameTokenType.BLACK_KING))
                {
                    moved = true;
                    numBlack--;
                    //Delete the black checker
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx 
                        && GameBoard.this.gameIndexes.get(index).y == tempy)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                    System.out.println("Red jumped black: Numblack is now = " + numBlack + "\n");
                }
                else if(toRemove != null && GameBoard.this.gameIndex.checker.checkerType == GameTokenType.BLACK_REGULAR
                        && (GameBoard.this.gameIndex.y - oldy == -2) && 
		   (myChecker == GameTokenType.RED_REGULAR || myChecker == GameTokenType.RED_KING))
                {
                      moved = true;
                      numRed--;
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx 
                        && GameBoard.this.gameIndexes.get(index).y == tempy)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                      System.out.println("Black jumped red: NumRed is now = " + numRed + "\n");        
                }
                else if(toRemove != null && GameBoard.this.gameIndex.checker.checkerType == GameTokenType.BLACK_KING
                        && (Math.abs(GameBoard.this.gameIndex.y - oldy) == 2) && 
		   (myChecker == GameTokenType.RED_REGULAR || myChecker == GameTokenType.RED_KING))
                {
                      moved = true;
                      numRed--;
                      //Delete the red checker
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx 
                        && GameBoard.this.gameIndexes.get(index).y == tempy)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                      System.out.println("Black jumped red: NumRed is now = " + numRed + "\n");        
                }
                else
                {
                    System.out.println("could not remove!");
                    GameBoard.this.gameIndex.cx = oldcx;
                    GameBoard.this.gameIndex.cy = oldcy;
                    GameBoard.this.gameIndex.x = oldx;
                    GameBoard.this.gameIndex.y = oldy;
                    moved = false;
                }
	    }
            else if(Math.abs(GameBoard.this.gameIndex.x - oldx) == 4) 
            {
                System.out.println("Made it to two squares - double jump!\n");
                //Check the piece jumped across
                GameTokenType myChecker = GameTokenType.UNDEFINED;
                GameBoard.GamePiece toRemove = null;
                int tempx = 100;
                int tempy = 100;
                
                GameTokenType myChecker2 = GameTokenType.UNDEFINED;
                GameBoard.GamePiece toRemove2 = null;
                int tempx2 = 100;
                int tempy2 = 100;
                System.out.println("X:= " + GameBoard.this.gameIndex.x + "  Y:= " + GameBoard.this.gameIndex.y);
                System.out.println("oldX:= " + oldx + " oldY:= " + oldy);
                int halfx = (oldx + GameBoard.this.gameIndex.x)/2;
                int halfy = (oldy + GameBoard.this.gameIndex.y)/2;
                System.out.println("halfx = " + halfx + " halfy = " + halfy);
                
                for (GameBoard.GamePiece gameIndex: gameIndexes)
                {
                    if (gameIndex != GameBoard.this.gameIndex 
                        && gameIndex.x == (halfx + oldx)/2 
                        && gameIndex.y == (halfy + oldy)/2)
                    {
                        myChecker = gameIndex.checker.checkerType;
                        toRemove = gameIndex;
                        tempx = gameIndex.x;
                        tempy = gameIndex.y;
                        System.out.println("Remove checker X = " + gameIndex.x + "Y = " + gameIndex.y);
                        break;
                    }
                }
                
                System.out.println("X = " + halfx + " Y = " + halfy);
                
                for (GameBoard.GamePiece gameIndex2: gameIndexes)
                {
                    if (gameIndex2 != GameBoard.this.gameIndex 
                        && gameIndex2.x == (GameBoard.this.gameIndex.x + halfx)/2 
                        && gameIndex2.y == (GameBoard.this.gameIndex.y + halfy)/2)
                    {
                        myChecker2 = gameIndex2.checker.checkerType;
                        toRemove2 = gameIndex2;
                        tempx2 = gameIndex2.x;
                        tempy2 = gameIndex2.y;
                        System.out.println("Remove checker X = " + gameIndex2.x + "Y = " + gameIndex2.y);
                        break;
                    }
                }
                System.out.println(myChecker);
                System.out.println(myChecker2);
                
                //Find the checker located at that region
		if (toRemove != null && toRemove2 != null && GameBoard.this.gameIndex.checker.checkerType == GameTokenType.RED_REGULAR
                        && (GameBoard.this.gameIndex.y - oldy == 4) && 
		    (myChecker == GameTokenType.BLACK_REGULAR || myChecker == GameTokenType.BLACK_KING)
                        && (myChecker2 == GameTokenType.BLACK_REGULAR || myChecker2 == GameTokenType.BLACK_KING))
                {
                    System.out.println("CAME IN HERE!!!!!");
                    moved = true;
                    numBlack--;
                    numBlack--;
                    //Delete the black checker
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx 
                        && GameBoard.this.gameIndexes.get(index).y == tempy)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx2 
                        && GameBoard.this.gameIndexes.get(index).y == tempy2)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                    System.out.println("Red jumped black: Numblack is now = " + numBlack + "\n");
                }
                else if (toRemove != null && toRemove2 != null && GameBoard.this.gameIndex.checker.checkerType == GameTokenType.RED_KING
                        && (Math.abs(GameBoard.this.gameIndex.y - oldy) == 4) && 
		    (myChecker == GameTokenType.BLACK_REGULAR || myChecker == GameTokenType.BLACK_KING)
                        && (myChecker2 == GameTokenType.BLACK_REGULAR || myChecker2 == GameTokenType.BLACK_KING))
                {
                    moved = true;
                    numBlack--;
                    numBlack--;
                    //Delete the black checker
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx 
                        && GameBoard.this.gameIndexes.get(index).y == tempy)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx2 
                        && GameBoard.this.gameIndexes.get(index).y == tempy2)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                    System.out.println("Red jumped black: Numblack is now = " + numBlack + "\n");
                }
                else if(toRemove != null && toRemove2 != null && GameBoard.this.gameIndex.checker.checkerType == GameTokenType.BLACK_REGULAR
                        && (GameBoard.this.gameIndex.y - oldy == -4) && 
		   (myChecker == GameTokenType.RED_REGULAR || myChecker == GameTokenType.RED_KING)
                        && (myChecker2 == GameTokenType.RED_REGULAR || myChecker2 == GameTokenType.RED_KING))
                {
                      moved = true;
                      numRed--;
                      numRed--;
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx 
                        && GameBoard.this.gameIndexes.get(index).y == tempy)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx2 
                        && GameBoard.this.gameIndexes.get(index).y == tempy2)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                      System.out.println("Black jumped red: NumRed is now = " + numRed + "\n");        
                }
                else if(toRemove != null && toRemove2 != null && GameBoard.this.gameIndex.checker.checkerType == GameTokenType.BLACK_KING
                        && (Math.abs(GameBoard.this.gameIndex.y - oldy) == 4) && 
		   (myChecker == GameTokenType.RED_REGULAR || myChecker == GameTokenType.RED_KING)
                        && (myChecker2 == GameTokenType.RED_REGULAR || myChecker2 == GameTokenType.RED_KING))
                {
                      moved = true;
                      numRed--;
                      numRed--;
                      //Delete the red checker
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx 
                        && GameBoard.this.gameIndexes.get(index).y == tempy)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < GameBoard.this.gameIndexes.size(); index++)
                    {
                    if (GameBoard.this.gameIndexes.get(index).x == tempx2 
                        && GameBoard.this.gameIndexes.get(index).y == tempy2)
                    {
                        GameBoard.this.gameIndexes.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                      System.out.println("Black jumped red: NumRed is now = " + numRed + "\n");        
                }
                else
                {
                    System.out.println("could not remove!");
                    GameBoard.this.gameIndex.cx = oldcx;
                    GameBoard.this.gameIndex.cy = oldcy;
                    GameBoard.this.gameIndex.x = oldx;
                    GameBoard.this.gameIndex.y = oldy;
                    moved = false;
                }
	    }
            else
            {
                System.out.println("could not remove - reason 2!");                
                GameBoard.this.gameIndex.x = oldx;
                GameBoard.this.gameIndex.y = oldy;
                GameBoard.this.gameIndex.cx = oldcx;
                GameBoard.this.gameIndex.cy = oldcy;
                moved = false;
            }
            
            //Repaint and switch move
            repaint();
            //Check for kinged checkers
            if(moved && GameBoard.this.gameIndex.y == 8 && currentMove == "red")
            {
                GameBoard.this.gameIndex.checker.checkerType = GameTokenType.RED_KING;
            }
            else if(moved && GameBoard.this.gameIndex.y == 1 && currentMove == "black")
            {
                 GameBoard.this.gameIndex.checker.checkerType = GameTokenType.BLACK_KING;
            }
            else
            {
                //Do nothing
            }
            //Repaint and switch move
            repaint();
            switchTurn(moved);
        }
       });

      // Attach a mouse motion listener to the applet. That listener listens
      // for mouse drag events.

      addMouseMotionListener(new MouseMotionAdapter()
      {
        @Override
        public void mouseDragged(MouseEvent me)
        {
            if (isCurrentlyMoving)
            {
              // Update location of checker center.
              gameIndex.cx = me.getX() - deltax;
              gameIndex.cy = me.getY() - deltay;
              repaint();
            }
        }
       });

   }

   //Add pieces onto the list
   public void add(Piece checker, int row, int col)
   {
      if (row < 1 || row > 8)
         throw new IllegalArgumentException("row out of range: " + row);
      if (col < 1 || col > 8)
         throw new IllegalArgumentException("col out of range: " + col);
      GamePiece gameIndex = new GamePiece();
      gameIndex.checker = checker;
      gameIndex.x = col;
      gameIndex.y = row;
      gameIndex.cx = (col - 1) * TILESIZE + TILESIZE / 2;
      gameIndex.cy = (row - 1) * TILESIZE + TILESIZE / 2;
      for (GamePiece _gameIndex: gameIndexes)
         if (gameIndex.cx == _gameIndex.cx && gameIndex.cy == _gameIndex.cy)
            throw new OccupiedAlready("square at (" + row + "," +
                                               col + ") is occupied");
      gameIndexes.add(gameIndex);
   }

   //Dummy function doesn't work
   public void delete(Piece checker, int row, int col)
   {
      GamePiece gameIndex = new GamePiece();
      gameIndex.checker = checker;
      gameIndex.cx = (col - 1) * TILESIZE + TILESIZE / 2;
      gameIndex.cy = (row - 1) * TILESIZE + TILESIZE / 2;
      gameIndexes.remove(gameIndex);
   }
   
   //Get the dimension and return it
   @Override
   public Dimension getPreferredSize()
   {
      return boardSize;
   }

   //Paint the components
   @Override
   protected void paintComponent(Graphics g)
   {
      paintCheckerBoard(g);
      for (GamePiece gameIndex: gameIndexes)
         if (gameIndex != GameBoard.this.gameIndex)
            gameIndex.checker.draw(g, gameIndex.cx, gameIndex.cy);

      if (gameIndex != null)
         gameIndex.checker.draw(g, gameIndex.cx, gameIndex.cy);
   }

   //Paint the checkerboard
   private void paintCheckerBoard(Graphics g)
   {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);

      // Paint checkerboard
      for (int row = 0; row < 8; row++)
      {
         g.setColor(((row & 1) != 0) ? Color.BLACK : Color.GRAY);
         for (int col = 0; col < 8; col++)
         {
            g.fillRect(col * TILESIZE, row * TILESIZE, TILESIZE, TILESIZE);
            g.setColor((g.getColor() == Color.BLACK) ? Color.GRAY : Color.BLACK);
         }
      }
   }

   // positioned checker helper class
   private class GamePiece extends GameTokenPosition
   {
      public Piece checker;   //Type
   }
}