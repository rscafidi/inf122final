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

public class Board extends JComponent
{
   // dimension of checkerboard square (25% bigger than checker)

   private final static int SQUAREDIM = (int) (Checker.getDimension() * 1.25);

   // dimension of checkerboard (width of 8 squares)

   private final int BOARDDIM = 8 * SQUAREDIM;

   // preferred size of Board component

   private Dimension dimPrefSize;

   // dragging flag -- set to true when user presses mouse button over checker
   // and cleared to false when user releases mouse button

   private boolean inDrag = false;

   // displacement between drag start coordinates and checker center coordinates

   private int deltax, deltay;

   // reference to positioned checker at start of drag

   private PosCheck posCheck;

   // center location of checker at start of drag

   private int oldcx, oldcy;
   private int oldx, oldy;

   // list of Checker objects and their initial positions

   private List<PosCheck> posChecks;
   
   //Private variable for turns
   private String currentMove;
   private int numRed;
   private int numBlack;

   public Board()
   {
      posChecks = new ArrayList<>();
      dimPrefSize = new Dimension(BOARDDIM, BOARDDIM);
      currentMove = "black";
      numRed = 12;
      numBlack = 12;
      
      addMouseListener(new MouseAdapter()
      {
        @Override
        public void mousePressed(MouseEvent me)
        {
             if(numRed == 0)
             {
                 showMessageDialog(null, "Black wins!");
                 return;
             }
             if(numBlack == 0)
             {
                 showMessageDialog(null, "Red Wins!");
                 return;
             }
            // Obtain mouse coordinates at time of press.
            int x = me.getX();
            int y = me.getY();

            // Locate positioned checker under mouse press.
            for (PosCheck posCheck: posChecks)
            if (Checker.contains(x, y, posCheck.cx, posCheck.cy))
            {
                Board.this.posCheck = posCheck;
                oldcx = posCheck.cx;
                oldcy = posCheck.cy;
                oldx = ((oldcx - SQUAREDIM/2)/SQUAREDIM + 1);
                oldy = ((oldcy - SQUAREDIM/2)/SQUAREDIM + 1);
                deltax = x - posCheck.cx;
                deltay = y - posCheck.cy;
                inDrag = true;
                return;
            }
         }

       @Override
       public void mouseReleased(MouseEvent me)
       {
            // When mouse released, clear inDrag (to
            // indicate no drag in progress) if inDrag is
            // already set.
            if (inDrag)
                inDrag = false;
            else
                return;
            
            if(numRed == 0)
             {
                 showMessageDialog(null, "Black wins!");
                 return;
             }
             if(numBlack == 0)
             {
                 showMessageDialog(null, "Red Wins!");
                 return;
             }
             
            //Check that a black checker is moving
            if(currentMove == "black")
            {
                if(Board.this.posCheck.checker.checkerType == CheckerType.BLACK_KING || 
                        Board.this.posCheck.checker.checkerType == CheckerType.BLACK_REGULAR)
                {
                    //do nothing and continue
                    System.out.println("Continuing past skip!\n");
                }
                else
                {
                    showMessageDialog(null, "Black's turn!");
                    Board.this.posCheck.cx = oldcx;
                    Board.this.posCheck.cy = oldcy;
                    Board.this.posCheck.x = oldx;
                    Board.this.posCheck.y = oldy;
                    repaint();
                    return;
                }
            }
            else
            {
                if(Board.this.posCheck.checker.checkerType == CheckerType.RED_KING || 
                        Board.this.posCheck.checker.checkerType == CheckerType.RED_REGULAR)
                {
                    //do nothing and continue
                    System.out.println("Continuing past skip!\n");
                }
                else
                {
                    showMessageDialog(null, "Red's turn!");
                    Board.this.posCheck.cx = oldcx;
                    Board.this.posCheck.cy = oldcy;
                    Board.this.posCheck.x = oldx;
                    Board.this.posCheck.y = oldy;
                    repaint();
                    return;
                }
            }
                       
            // Snap checker to center of square
            boolean moved = true;
            int x = me.getX();
            int y = me.getY();
            posCheck.cx = (x - deltax) / SQUAREDIM * SQUAREDIM + SQUAREDIM / 2;
            posCheck.cy = (y - deltay) / SQUAREDIM * SQUAREDIM + SQUAREDIM / 2;
            Board.this.posCheck.x = ((Board.this.posCheck.cx - SQUAREDIM/2)/SQUAREDIM + 1);
            Board.this.posCheck.y = ((Board.this.posCheck.cy - SQUAREDIM/2)/SQUAREDIM + 1);
            
            //Do not move checker onto an occupied square.
            for (PosCheck posCheck: posChecks)
                if (posCheck != Board.this.posCheck && 
                posCheck.cx == Board.this.posCheck.cx &&
                posCheck.cy == Board.this.posCheck.cy)
                {
                    System.out.println("Could not add to occupied square!\n");
                    Board.this.posCheck.cx = oldcx;
                    Board.this.posCheck.cy = oldcy;
                    Board.this.posCheck.x = oldx;
                    Board.this.posCheck.y = oldy;
                    moved = false;
                }
            
            System.out.println("Continuing past onto the rules!\n"); 
            System.out.println("X = " + Board.this.posCheck.x + " oldx = " + oldx);
            System.out.println("Y = " + Board.this.posCheck.y + " oldy = " + oldy);
            
            if((Board.this.posCheck.x - oldx) == 0 && ((Board.this.posCheck.y - oldy) == 4 || (Board.this.posCheck.y - oldy) == -4))
            {
                //Check if x+ or -2 and y == 1/2 is clear
                boolean skipStep = false;
                
                int halfx = Board.this.posCheck.x + 2;
                int halfy = (Board.this.posCheck.y + oldy)/2;
                skipStep = false;
                for (PosCheck posCheck: posChecks)
                {
                    if (posCheck != Board.this.posCheck && posCheck.x == Board.this.posCheck.x + 2
                        && posCheck.y == (Board.this.posCheck.y + oldy)/2)
                    {
                        halfx = 100;
                        halfy = 100;
                        skipStep = true;
                       // System.out.println("HALF checker X = " + posCheck.x + "Y = " + posCheck.y);
                        break;
                    }
                }
                
                if(skipStep)
                {
                halfx = Board.this.posCheck.x - 2;
                halfy = (Board.this.posCheck.y + oldy)/2;
                for (PosCheck posCheck: posChecks)
                {
                    if (posCheck != Board.this.posCheck && posCheck.x == Board.this.posCheck.x - 2
                        && posCheck.y == (Board.this.posCheck.y + oldy)/2)
                    {
                        halfx = 100;
                        halfy = 100;
                    //    System.out.println("HALF checker X = " + posCheck.x + "Y = " + posCheck.y);
                        break;
                    }
                }
                }
                                  
                System.out.println("Made it to two squares - double jump rare!\n");
                //Check the piece jumped across
                CheckerType myChecker = CheckerType.UNDEFINED;
                PosCheck toRemove = null;
                int tempx = 100;
                int tempy = 100;
                
                CheckerType myChecker2 = CheckerType.UNDEFINED;
                PosCheck toRemove2 = null;
                int tempx2 = 100;
                int tempy2 = 100;
                System.out.println("X:= " + Board.this.posCheck.x + "  Y:= " + Board.this.posCheck.y);
                System.out.println("oldX:= " + oldx + " oldY:= " + oldy);

                System.out.println("halfx = " + halfx + " halfy = " + halfy);
                
                for (PosCheck posCheck: posChecks)
                {
                    if (posCheck != Board.this.posCheck 
                        && posCheck.x == (halfx + oldx)/2 
                        && posCheck.y == (halfy + oldy)/2)
                    {
                        myChecker = posCheck.checker.checkerType;
                        toRemove = posCheck;
                        tempx = posCheck.x;
                        tempy = posCheck.y;
                        System.out.println("Remove checker X = " + posCheck.x + "Y = " + posCheck.y);
                        break;
                    }
                }
                
                System.out.println("X = " + halfx + " Y = " + halfy);
                
                for (PosCheck posCheck2: posChecks)
                {
                    if (posCheck2 != Board.this.posCheck 
                        && posCheck2.x == (Board.this.posCheck.x + halfx)/2 
                        && posCheck2.y == (Board.this.posCheck.y + halfy)/2)
                    {
                        myChecker2 = posCheck2.checker.checkerType;
                        toRemove2 = posCheck2;
                        tempx2 = posCheck2.x;
                        tempy2 = posCheck2.y;
                        System.out.println("Remove checker X = " + posCheck2.x + "Y = " + posCheck2.y);
                        break;
                    }
                }
                System.out.println(myChecker);
                System.out.println(myChecker2);
                
                //Find the checker located at that region
		if (toRemove != null && toRemove2 != null && Board.this.posCheck.checker.checkerType == CheckerType.RED_REGULAR
                        && (Board.this.posCheck.y - oldy == 4) && 
		    (myChecker == CheckerType.BLACK_REGULAR || myChecker == CheckerType.BLACK_KING)
                        && (myChecker2 == CheckerType.BLACK_REGULAR || myChecker2 == CheckerType.BLACK_KING))
                {
                    System.out.println("CAME IN HERE!!!!!");
                    moved = true;
                    numBlack--;
                    numBlack--;
                    //Delete the black checker
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx 
                        && Board.this.posChecks.get(index).y == tempy)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx2 
                        && Board.this.posChecks.get(index).y == tempy2)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                    System.out.println("Red jumped black: Numblack is now = " + numBlack + "\n");
                }
                else if (toRemove != null && toRemove2 != null && Board.this.posCheck.checker.checkerType == CheckerType.RED_KING
                        && (Math.abs(Board.this.posCheck.y - oldy) == 4) && 
		    (myChecker == CheckerType.BLACK_REGULAR || myChecker == CheckerType.BLACK_KING)
                        && (myChecker2 == CheckerType.BLACK_REGULAR || myChecker2 == CheckerType.BLACK_KING))
                {
                    moved = true;
                    numBlack--;
                    numBlack--;
                    //Delete the black checker
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx 
                        && Board.this.posChecks.get(index).y == tempy)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx2 
                        && Board.this.posChecks.get(index).y == tempy2)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                    System.out.println("Red jumped black: Numblack is now = " + numBlack + "\n");
                }
                else if(toRemove != null && toRemove2 != null && Board.this.posCheck.checker.checkerType == CheckerType.BLACK_REGULAR
                        && (Board.this.posCheck.y - oldy == -4) && 
		   (myChecker == CheckerType.RED_REGULAR || myChecker == CheckerType.RED_KING)
                        && (myChecker2 == CheckerType.RED_REGULAR || myChecker2 == CheckerType.RED_KING))
                {
                      moved = true;
                      numRed--;
                      numRed--;
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx 
                        && Board.this.posChecks.get(index).y == tempy)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx2 
                        && Board.this.posChecks.get(index).y == tempy2)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                      System.out.println("Black jumped red: NumRed is now = " + numRed + "\n");        
                }
                else if(toRemove != null && toRemove2 != null && Board.this.posCheck.checker.checkerType == CheckerType.BLACK_KING
                        && (Math.abs(Board.this.posCheck.y - oldy) == 4) && 
		   (myChecker == CheckerType.RED_REGULAR || myChecker == CheckerType.RED_KING)
                        && (myChecker2 == CheckerType.RED_REGULAR || myChecker2 == CheckerType.RED_KING))
                {
                      moved = true;
                      numRed--;
                      numRed--;
                      //Delete the red checker
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx 
                        && Board.this.posChecks.get(index).y == tempy)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx2 
                        && Board.this.posChecks.get(index).y == tempy2)
                    {
                        Board.this.posChecks.remove(index);
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
                    Board.this.posCheck.cx = oldcx;
                    Board.this.posCheck.cy = oldcy;
                    Board.this.posCheck.x = oldx;
                    Board.this.posCheck.y = oldy;
                    moved = false;
                } 
            }
            else if(Math.abs(Board.this.posCheck.x - oldx) == 1) 
            {
                System.out.println("X IS ON THE NEXT LINE!\n");
		if((Board.this.posCheck.checker.checkerType == CheckerType.RED_REGULAR) 
                        && (Board.this.posCheck.y - oldy == 1))
                {
                   System.out.println("Red: X could be moved!\n");
                   moved = true;
                }
                else if((Board.this.posCheck.checker.checkerType == CheckerType.RED_KING) 
                        && (Math.abs(Board.this.posCheck.y - oldy) == 1))
                {
                   System.out.println("Red: X could be moved!\n");
                   moved = true;
                }
                else if((Board.this.posCheck.checker.checkerType == CheckerType.BLACK_REGULAR) 
                        && (Board.this.posCheck.y - oldy == -1))
                {
                    System.out.println("Black: X could be moved!\n");
                    moved = true; 
                }
                else if((Board.this.posCheck.checker.checkerType == CheckerType.BLACK_KING)
                        && (Math.abs(Board.this.posCheck.y - oldy) == 1))
                {
                    System.out.println("Black: X could be moved!\n");
                    moved = true; 
                }
                else
                {
                    Board.this.posCheck.x = oldx;
                    Board.this.posCheck.y = oldy;
                    Board.this.posCheck.cx = oldcx;
                    Board.this.posCheck.cy = oldcy;
                    moved = false;
                }


	    }
	    // Checks case of a jump
	    else if(Math.abs(Board.this.posCheck.x - oldx) == 2) 
            {
                System.out.println("Made it to two squares!\n");
                //Check the piece jumped across
                CheckerType myChecker = CheckerType.UNDEFINED;
                PosCheck toRemove = null;
                int tempx = 100;
                int tempy = 100;
                for (PosCheck posCheck: posChecks)
                {
                    if (posCheck != Board.this.posCheck 
                        && posCheck.x == (Board.this.posCheck.x + oldx)/2 
                        && posCheck.y == (Board.this.posCheck.y + oldy)/2)
                    {
                        myChecker = posCheck.checker.checkerType;
                        toRemove = posCheck;
                        tempx = posCheck.x;
                        tempy = posCheck.y;
                        System.out.println("Remove checker X = " + posCheck.x + "Y = " + posCheck.y);
                        break;
                    }
                }
                System.out.println(myChecker);
                
                //Find the checker located at that region
		if (toRemove != null && Board.this.posCheck.checker.checkerType == CheckerType.RED_REGULAR
                        && (Board.this.posCheck.y - oldy == 2) && 
		    (myChecker == CheckerType.BLACK_REGULAR || myChecker == CheckerType.BLACK_KING))
                {
                    System.out.println("CAME IN HERE!!!!!");
                    moved = true;
                    numBlack--;
                    //Delete the black checker
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx 
                        && Board.this.posChecks.get(index).y == tempy)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                    System.out.println("Red jumped black: Numblack is now = " + numBlack + "\n");
                }
                else if (toRemove != null && Board.this.posCheck.checker.checkerType == CheckerType.RED_KING
                        && (Math.abs(Board.this.posCheck.y - oldy) == 2) && 
		    (myChecker == CheckerType.BLACK_REGULAR || myChecker == CheckerType.BLACK_KING))
                {
                    moved = true;
                    numBlack--;
                    //Delete the black checker
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx 
                        && Board.this.posChecks.get(index).y == tempy)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                    System.out.println("Red jumped black: Numblack is now = " + numBlack + "\n");
                }
                else if(toRemove != null && Board.this.posCheck.checker.checkerType == CheckerType.BLACK_REGULAR
                        && (Board.this.posCheck.y - oldy == -2) && 
		   (myChecker == CheckerType.RED_REGULAR || myChecker == CheckerType.RED_KING))
                {
                      moved = true;
                      numRed--;
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx 
                        && Board.this.posChecks.get(index).y == tempy)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                      System.out.println("Black jumped red: NumRed is now = " + numRed + "\n");        
                }
                else if(toRemove != null && Board.this.posCheck.checker.checkerType == CheckerType.BLACK_KING
                        && (Math.abs(Board.this.posCheck.y - oldy) == 2) && 
		   (myChecker == CheckerType.RED_REGULAR || myChecker == CheckerType.RED_KING))
                {
                      moved = true;
                      numRed--;
                      //Delete the red checker
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx 
                        && Board.this.posChecks.get(index).y == tempy)
                    {
                        Board.this.posChecks.remove(index);
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
                    Board.this.posCheck.cx = oldcx;
                    Board.this.posCheck.cy = oldcy;
                    Board.this.posCheck.x = oldx;
                    Board.this.posCheck.y = oldy;
                    moved = false;
                }
	    }
            else if(Math.abs(Board.this.posCheck.x - oldx) == 4) 
            {
                System.out.println("Made it to two squares - double jump!\n");
                //Check the piece jumped across
                CheckerType myChecker = CheckerType.UNDEFINED;
                PosCheck toRemove = null;
                int tempx = 100;
                int tempy = 100;
                
                CheckerType myChecker2 = CheckerType.UNDEFINED;
                PosCheck toRemove2 = null;
                int tempx2 = 100;
                int tempy2 = 100;
                System.out.println("X:= " + Board.this.posCheck.x + "  Y:= " + Board.this.posCheck.y);
                System.out.println("oldX:= " + oldx + " oldY:= " + oldy);
                int halfx = (oldx + Board.this.posCheck.x)/2;
                int halfy = (oldy + Board.this.posCheck.y)/2;
                System.out.println("halfx = " + halfx + " halfy = " + halfy);
                
                for (PosCheck posCheck: posChecks)
                {
                    if (posCheck != Board.this.posCheck 
                        && posCheck.x == (halfx + oldx)/2 
                        && posCheck.y == (halfy + oldy)/2)
                    {
                        myChecker = posCheck.checker.checkerType;
                        toRemove = posCheck;
                        tempx = posCheck.x;
                        tempy = posCheck.y;
                        System.out.println("Remove checker X = " + posCheck.x + "Y = " + posCheck.y);
                        break;
                    }
                }
                
                System.out.println("X = " + halfx + " Y = " + halfy);
                
                for (PosCheck posCheck2: posChecks)
                {
                    if (posCheck2 != Board.this.posCheck 
                        && posCheck2.x == (Board.this.posCheck.x + halfx)/2 
                        && posCheck2.y == (Board.this.posCheck.y + halfy)/2)
                    {
                        myChecker2 = posCheck2.checker.checkerType;
                        toRemove2 = posCheck2;
                        tempx2 = posCheck2.x;
                        tempy2 = posCheck2.y;
                        System.out.println("Remove checker X = " + posCheck2.x + "Y = " + posCheck2.y);
                        break;
                    }
                }
                System.out.println(myChecker);
                System.out.println(myChecker2);
                
                //Find the checker located at that region
		if (toRemove != null && toRemove2 != null && Board.this.posCheck.checker.checkerType == CheckerType.RED_REGULAR
                        && (Board.this.posCheck.y - oldy == 4) && 
		    (myChecker == CheckerType.BLACK_REGULAR || myChecker == CheckerType.BLACK_KING)
                        && (myChecker2 == CheckerType.BLACK_REGULAR || myChecker2 == CheckerType.BLACK_KING))
                {
                    System.out.println("CAME IN HERE!!!!!");
                    moved = true;
                    numBlack--;
                    numBlack--;
                    //Delete the black checker
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx 
                        && Board.this.posChecks.get(index).y == tempy)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx2 
                        && Board.this.posChecks.get(index).y == tempy2)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                    System.out.println("Red jumped black: Numblack is now = " + numBlack + "\n");
                }
                else if (toRemove != null && toRemove2 != null && Board.this.posCheck.checker.checkerType == CheckerType.RED_KING
                        && (Math.abs(Board.this.posCheck.y - oldy) == 4) && 
		    (myChecker == CheckerType.BLACK_REGULAR || myChecker == CheckerType.BLACK_KING)
                        && (myChecker2 == CheckerType.BLACK_REGULAR || myChecker2 == CheckerType.BLACK_KING))
                {
                    moved = true;
                    numBlack--;
                    numBlack--;
                    //Delete the black checker
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx 
                        && Board.this.posChecks.get(index).y == tempy)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx2 
                        && Board.this.posChecks.get(index).y == tempy2)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                    System.out.println("Red jumped black: Numblack is now = " + numBlack + "\n");
                }
                else if(toRemove != null && toRemove2 != null && Board.this.posCheck.checker.checkerType == CheckerType.BLACK_REGULAR
                        && (Board.this.posCheck.y - oldy == -4) && 
		   (myChecker == CheckerType.RED_REGULAR || myChecker == CheckerType.RED_KING)
                        && (myChecker2 == CheckerType.RED_REGULAR || myChecker2 == CheckerType.RED_KING))
                {
                      moved = true;
                      numRed--;
                      numRed--;
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx 
                        && Board.this.posChecks.get(index).y == tempy)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx2 
                        && Board.this.posChecks.get(index).y == tempy2)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    repaint();
                      System.out.println("Black jumped red: NumRed is now = " + numRed + "\n");        
                }
                else if(toRemove != null && toRemove2 != null && Board.this.posCheck.checker.checkerType == CheckerType.BLACK_KING
                        && (Math.abs(Board.this.posCheck.y - oldy) == 4) && 
		   (myChecker == CheckerType.RED_REGULAR || myChecker == CheckerType.RED_KING)
                        && (myChecker2 == CheckerType.RED_REGULAR || myChecker2 == CheckerType.RED_KING))
                {
                      moved = true;
                      numRed--;
                      numRed--;
                      //Delete the red checker
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx 
                        && Board.this.posChecks.get(index).y == tempy)
                    {
                        Board.this.posChecks.remove(index);
                        System.out.println("Removed");
                        break;
                    }
                    }
                    for (int index = 0; index < Board.this.posChecks.size(); index++)
                    {
                    if (Board.this.posChecks.get(index).x == tempx2 
                        && Board.this.posChecks.get(index).y == tempy2)
                    {
                        Board.this.posChecks.remove(index);
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
                    Board.this.posCheck.cx = oldcx;
                    Board.this.posCheck.cy = oldcy;
                    Board.this.posCheck.x = oldx;
                    Board.this.posCheck.y = oldy;
                    moved = false;
                }
	    }
            else
            {
                System.out.println("could not remove - reason 2!");                
                Board.this.posCheck.x = oldx;
                Board.this.posCheck.y = oldy;
                Board.this.posCheck.cx = oldcx;
                Board.this.posCheck.cy = oldcy;
                moved = false;
            }
            
            //Repaint and switch move
            repaint();
            //Check for kinged checkers
            if(moved && Board.this.posCheck.y == 8 && currentMove == "red")
            {
                Board.this.posCheck.checker.checkerType = CheckerType.RED_KING;
            }
            else if(moved && Board.this.posCheck.y == 1 && currentMove == "black")
            {
                 Board.this.posCheck.checker.checkerType = CheckerType.BLACK_KING;
            }
            else
            {
                //Do nothing
            }
            //Repaint and switch move
            repaint();
            if(moved && currentMove == "red")
            {
                currentMove = "black";
            }
            else if(moved && currentMove == "black")
            {
                currentMove = "red";
            }
        }
       });

      // Attach a mouse motion listener to the applet. That listener listens
      // for mouse drag events.

      addMouseMotionListener(new MouseMotionAdapter()
      {
        @Override
        public void mouseDragged(MouseEvent me)
        {
            if (inDrag)
            {
              // Update location of checker center.
              posCheck.cx = me.getX() - deltax;
              posCheck.cy = me.getY() - deltay;
              repaint();
            }
        }
       });

   }

   public void add(Checker checker, int row, int col)
   {
      if (row < 1 || row > 8)
         throw new IllegalArgumentException("row out of range: " + row);
      if (col < 1 || col > 8)
         throw new IllegalArgumentException("col out of range: " + col);
      PosCheck posCheck = new PosCheck();
      posCheck.checker = checker;
      posCheck.x = col;
      posCheck.y = row;
      posCheck.cx = (col - 1) * SQUAREDIM + SQUAREDIM / 2;
      posCheck.cy = (row - 1) * SQUAREDIM + SQUAREDIM / 2;
      for (PosCheck _posCheck: posChecks)
         if (posCheck.cx == _posCheck.cx && posCheck.cy == _posCheck.cy)
            throw new AlreadyOccupiedException("square at (" + row + "," +
                                               col + ") is occupied");
      posChecks.add(posCheck);
   }

   public void delete(Checker checker, int row, int col)
   {
      PosCheck posCheck = new PosCheck();
      posCheck.checker = checker;
      posCheck.cx = (col - 1) * SQUAREDIM + SQUAREDIM / 2;
      posCheck.cy = (row - 1) * SQUAREDIM + SQUAREDIM / 2;
      posChecks.remove(posCheck);
   }
   
   @Override
   public Dimension getPreferredSize()
   {
      return dimPrefSize;
   }

   @Override
   protected void paintComponent(Graphics g)
   {
      paintCheckerBoard(g);
      for (PosCheck posCheck: posChecks)
         if (posCheck != Board.this.posCheck)
            posCheck.checker.draw(g, posCheck.cx, posCheck.cy);

      // Draw dragged checker last so that it appears over any underlying 
      // checker.

      if (posCheck != null)
         posCheck.checker.draw(g, posCheck.cx, posCheck.cy);
   }

   private void paintCheckerBoard(Graphics g)
   {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);

      // Paint checkerboard.

      for (int row = 0; row < 8; row++)
      {
         g.setColor(((row & 1) != 0) ? Color.BLACK : Color.GRAY);
         for (int col = 0; col < 8; col++)
         {
            g.fillRect(col * SQUAREDIM, row * SQUAREDIM, SQUAREDIM, SQUAREDIM);
            g.setColor((g.getColor() == Color.BLACK) ? Color.GRAY : Color.BLACK);
            //g.drawString(row + "," + col, col * SQUAREDIM, row * SQUAREDIM);
         }
      }
   }

   // positioned checker helper class

   private class PosCheck
   {
      public Checker checker;
      public int cx;
      public int cy;
      public int x;
      public int y;
   }
}