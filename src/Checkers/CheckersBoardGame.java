package Checkers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import static javax.swing.JOptionPane.showMessageDialog;

public class CheckersBoardGame extends JComponent {

    public final static int TILESIZE = (int) (Piece.getDimension() * 1.25);
    public final int BOARDSIZE = 8 * TILESIZE;
    public Dimension boardSize;                 // To get the size of the board
    public boolean isCurrentlyMoving = false;   // Check if it is currently moving
    public int deltax, deltay;
    public CheckerPiece gameIndex;                 // The index location
    public int oldcx, oldcy;                    // Old pixel position
    public int oldx, oldy;                      // Old x and y coordinate position
    public List<CheckerPiece> gameIndexes;         // The list of game pieces and locations
    public CheckersGameDriver myGame;

    //Call the rules function in the constructor
    public CheckersBoardGame() throws IOException {
        myGame = new CheckersGameDriver("haha", "haha", BOARDSIZE, BOARDSIZE, "haha");
        //Initialize arrays
        gameIndexes = new ArrayList<>();
        boardSize = new Dimension(BOARDSIZE, BOARDSIZE);
        myGame.currentTurn = "black";
        myRules();
    }

    //Rules to move game pieces
    public void myRules() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                //********************************************
                // this has been copied to CheckersGame class
                // flagging this for removal
                if (myGame.isDone()) {
                    showMessageDialog(null, "Game Over!");
                    return;
                }
                //**********************************************

                // Obtain mouse coordinates at time of press.
                int x = me.getX();
                int y = me.getY();

                // Locate positioned piece under mouse press.
                for (CheckerPiece gameIndex : gameIndexes) {
                    if (Piece.contains(x, y, gameIndex.cx, gameIndex.cy)) {
                        CheckersBoardGame.this.gameIndex = gameIndex;
                        oldcx = gameIndex.cx;
                        oldcy = gameIndex.cy;
                        oldx = ((oldcx - TILESIZE / 2) / TILESIZE + 1);
                        oldy = ((oldcy - TILESIZE / 2) / TILESIZE + 1);
                        deltax = x - gameIndex.cx;
                        deltay = y - gameIndex.cy;
                        isCurrentlyMoving = true;
                        return;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                //Check here
                if (isCurrentlyMoving) {
                    isCurrentlyMoving = false;
                } else {
                    return;
                }

                //********************************************
                // this has been copied to CheckersGame class
                // flagging this for removal
                if (myGame.isDone()) {
                    showMessageDialog(null, "Game Over!");
                    return;
                }
                //**********************************************

                //********************************************************
                // use the isLegalMove in game and replace the code below
                // isLegalMove would takes the currentMove and the taken grabbed
                // if it is legal continue else return the game piece to the original settings
                //********************************************************
                // current logic below is to determine whose turn it is
                if (myGame.currentTurn == "black") {
                    if (CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.BLACK_KING
                            || CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.BLACK_REGULAR) {
                        //do nothing and continue
                        System.out.println("Continuing past skip!\n");
                    } else {
                        showMessageDialog(null, "Black's turn!");
                        CheckersBoardGame.this.gameIndex.cx = oldcx;
                        CheckersBoardGame.this.gameIndex.cy = oldcy;
                        CheckersBoardGame.this.gameIndex.x = oldx;
                        CheckersBoardGame.this.gameIndex.y = oldy;
                        repaint();
                        return;
                    }
                } else if (CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.RED_KING
                        || CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.RED_REGULAR) {
                    //do nothing and continue
                    System.out.println("Continuing past skip!\n");
                } else {
                    showMessageDialog(null, "Red's turn!");
                    CheckersBoardGame.this.gameIndex.cx = oldcx;
                    CheckersBoardGame.this.gameIndex.cy = oldcy;
                    CheckersBoardGame.this.gameIndex.x = oldx;
                    CheckersBoardGame.this.gameIndex.y = oldy;
                    repaint();
                    return;
                }
                //********************************************************

                //Stuff the gamepiece into the position
                boolean moved = true;
                int x = me.getX();
                int y = me.getY();
                gameIndex.cx = (x - deltax) / TILESIZE * TILESIZE + TILESIZE / 2;
                gameIndex.cy = (y - deltay) / TILESIZE * TILESIZE + TILESIZE / 2;
                CheckersBoardGame.this.gameIndex.x = ((CheckersBoardGame.this.gameIndex.cx - TILESIZE / 2) / TILESIZE + 1);
                CheckersBoardGame.this.gameIndex.y = ((CheckersBoardGame.this.gameIndex.cy - TILESIZE / 2) / TILESIZE + 1);

                //Do not move gamepiece onto an occupied square.
                for (CheckerPiece gameIndex : gameIndexes) {
                    if (gameIndex != CheckersBoardGame.this.gameIndex
                            && gameIndex.cx == CheckersBoardGame.this.gameIndex.cx
                            && gameIndex.cy == CheckersBoardGame.this.gameIndex.cy) {
                        System.out.println("Could not add to occupied square!\n");
                        CheckersBoardGame.this.gameIndex.cx = oldcx;
                        CheckersBoardGame.this.gameIndex.cy = oldcy;
                        CheckersBoardGame.this.gameIndex.x = oldx;
                        CheckersBoardGame.this.gameIndex.y = oldy;
                        moved = false;
                    }
                }

                System.out.println("Continuing past onto the rules!\n");
                System.out.println("X = " + CheckersBoardGame.this.gameIndex.x + " oldx = " + oldx);
                System.out.println("Y = " + CheckersBoardGame.this.gameIndex.y + " oldy = " + oldy);

                if ((CheckersBoardGame.this.gameIndex.x - oldx) == 0 && ((CheckersBoardGame.this.gameIndex.y - oldy) == 4 || (CheckersBoardGame.this.gameIndex.y - oldy) == -4)) {
                    //Check if x+ or -2 and y == 1/2 is clear, then check same with y == 2*
                    boolean foundProblem = false;
                    int halfx = CheckersBoardGame.this.gameIndex.x + 2;
                    int halfy = (CheckersBoardGame.this.gameIndex.y + oldy)/2;

                    for (CheckerPiece gameIndex : gameIndexes) {
                        if (gameIndex != CheckersBoardGame.this.gameIndex && gameIndex.x == CheckersBoardGame.this.gameIndex.x + 2
                                && gameIndex.y == (CheckersBoardGame.this.gameIndex.y + oldy) / 2) {
                            halfx = 100;
                            halfy = 100;
                            foundProblem = true;
                            System.out.println("It's not clear");
                            break;
                        }
                    }
                    
                    boolean foundSecondProblem = false;
                    if (foundProblem) {
                        halfx = CheckersBoardGame.this.gameIndex.x - 2;
                        halfy = (CheckersBoardGame.this.gameIndex.y + oldy)/2;
                        for (CheckerPiece gameIndex : gameIndexes) {
                            if (gameIndex != CheckersBoardGame.this.gameIndex && gameIndex.x == CheckersBoardGame.this.gameIndex.x - 2
                                    && gameIndex.y == (CheckersBoardGame.this.gameIndex.y + oldy) / 2) {
                                halfx = 100;
                                halfy = 100;
                                foundSecondProblem = true;
                                System.out.println("It's not clear");
                                break;
                            }
                        }
                    }
                    
                   
                    if(foundProblem && foundSecondProblem)
                    {
                        System.out.println("Didn't make it!\n");
                    }
                    else
                    {
                        System.out.println("Made it to the second round!");
                    }
                    

                    //Check the piece jumped across
                    GameTokenType myGamePiece = GameTokenType.UNDEFINED;
                    CheckerPiece toRemove = null;
                    int tempx = 100;
                    int tempy = 100;

                    GameTokenType myGamePiece2 = GameTokenType.UNDEFINED;
                    CheckerPiece toRemove2 = null;
                    int tempx2 = 100;
                    int tempy2 = 100;

                    //System.out.println("X:= " + CheckersBoardGame.this.gameIndex.x + "  Y:= " + CheckersBoardGame.this.gameIndex.y);
                    //System.out.println("oldX:= " + oldx + " oldY:= " + oldy);

                    for (CheckerPiece gameIndex : gameIndexes) {
                        if (gameIndex != CheckersBoardGame.this.gameIndex
                                && gameIndex.x == (halfx + oldx) / 2
                                && gameIndex.y == (halfy + oldy) / 2) {
                            myGamePiece = gameIndex.gameTokenType;
                            toRemove = gameIndex;
                            tempx = gameIndex.x;
                            tempy = gameIndex.y;
                            System.out.println("Flag Removal checker X = " + gameIndex.x + "Y = " + gameIndex.y);
                            break;
                        }
                    }
                  //  System.out.println("tempx = " + tempx + " tempy = " + tempy);
                    for (CheckerPiece gameIndex2 : gameIndexes) {
                        if (gameIndex2 != CheckersBoardGame.this.gameIndex
                                && gameIndex2.x == (CheckersBoardGame.this.gameIndex.x + halfx) / 2
                                && gameIndex2.y == (CheckersBoardGame.this.gameIndex.y + halfy) / 2) {
                            myGamePiece2 = gameIndex2.gameTokenType;
                            toRemove2 = gameIndex2;
                            tempx2 = gameIndex2.x;
                            tempy2 = gameIndex2.y;
                            System.out.println("Flag Removal checker X = " + gameIndex2.x + "Y = " + gameIndex2.y);
                            break;
                        }
                    }
                   // System.out.println("tempx2= " + tempx2 + " tempy2 = " + tempy2);
                    System.out.println(myGamePiece);
                    System.out.println(myGamePiece2);

                    //Find the checker located at that region
                    if (toRemove != null && toRemove2 != null && CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.RED_REGULAR
                            && (CheckersBoardGame.this.gameIndex.y - oldy == 4)
                            && (myGamePiece == GameTokenType.BLACK_REGULAR || myGamePiece == GameTokenType.BLACK_KING)
                            && (myGamePiece2 == GameTokenType.BLACK_REGULAR || myGamePiece2 == GameTokenType.BLACK_KING)) {
                        System.out.println("CAME IN HERE!!!!!");
                        moved = true;
                        myGame.numBlack--;
                        myGame.numBlack--;
                        //Delete the black checker
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx2
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy2) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        repaint();
                        System.out.println("Red jumped black: Numblack is now = " + myGame.numBlack + "\n");
                    } else if (toRemove != null && toRemove2 != null && CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.RED_KING
                            && (Math.abs(CheckersBoardGame.this.gameIndex.y - oldy) == 4)
                            && (myGamePiece == GameTokenType.BLACK_REGULAR || myGamePiece == GameTokenType.BLACK_KING)
                            && (myGamePiece2 == GameTokenType.BLACK_REGULAR || myGamePiece2 == GameTokenType.BLACK_KING)) {
                        moved = true;
                        myGame.numBlack--;
                        myGame.numBlack--;
                        //Delete the black checker
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx2
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy2) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        repaint();
                        System.out.println("Red jumped black: Numblack is now = " + myGame.numBlack + "\n");
                    } else if (toRemove != null && toRemove2 != null && CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.BLACK_REGULAR
                            && (CheckersBoardGame.this.gameIndex.y - oldy == -4)
                            && (myGamePiece == GameTokenType.RED_REGULAR || myGamePiece == GameTokenType.RED_KING)
                            && (myGamePiece2 == GameTokenType.RED_REGULAR || myGamePiece2 == GameTokenType.RED_KING)) {
                        moved = true;
                        myGame.numRed--;
                        myGame.numRed--;
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx2
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy2) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        repaint();
                        System.out.println("Black jumped red: NumRed is now = " + myGame.numRed + "\n");
                    } else if (toRemove != null && toRemove2 != null && CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.BLACK_KING
                            && (Math.abs(CheckersBoardGame.this.gameIndex.y - oldy) == 4)
                            && (myGamePiece == GameTokenType.RED_REGULAR || myGamePiece == GameTokenType.RED_KING)
                            && (myGamePiece2 == GameTokenType.RED_REGULAR || myGamePiece2 == GameTokenType.RED_KING)) {
                        moved = true;
                        myGame.numRed--;
                        myGame.numRed--;
                        //Delete the red checker
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx2
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy2) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        repaint();
                        System.out.println("Black jumped red: NumRed is now = " + myGame.numRed + "\n");
                    } else {
                        System.out.println("could not remove!");
                        CheckersBoardGame.this.gameIndex.cx = oldcx;
                        CheckersBoardGame.this.gameIndex.cy = oldcy;
                        CheckersBoardGame.this.gameIndex.x = oldx;
                        CheckersBoardGame.this.gameIndex.y = oldy;
                        moved = false;
                    }
                } else if (Math.abs(CheckersBoardGame.this.gameIndex.x - oldx) == 1) {
                    System.out.println("X IS ON THE NEXT LINE!\n");
                    if ((CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.RED_REGULAR)
                            && (CheckersBoardGame.this.gameIndex.y - oldy == 1)) {
                        System.out.println("Red: X could be moved!\n");
                        moved = true;
                    } else if ((CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.RED_KING)
                            && (Math.abs(CheckersBoardGame.this.gameIndex.y - oldy) == 1)) {
                        System.out.println("Red: X could be moved!\n");
                        moved = true;
                    } else if ((CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.BLACK_REGULAR)
                            && (CheckersBoardGame.this.gameIndex.y - oldy == -1)) {
                        System.out.println("Black: X could be moved!\n");
                        moved = true;
                    } else if ((CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.BLACK_KING)
                            && (Math.abs(CheckersBoardGame.this.gameIndex.y - oldy) == 1)) {
                        System.out.println("Black: X could be moved!\n");
                        moved = true;
                    } else {
                        CheckersBoardGame.this.gameIndex.x = oldx;
                        CheckersBoardGame.this.gameIndex.y = oldy;
                        CheckersBoardGame.this.gameIndex.cx = oldcx;
                        CheckersBoardGame.this.gameIndex.cy = oldcy;
                        moved = false;
                    }

                } // Checks case of a jump
                else if (Math.abs(CheckersBoardGame.this.gameIndex.x - oldx) == 2) {
                    System.out.println("Made it to two squares!\n");
                    //Check the piece jumped across
                    GameTokenType myGamePiece = GameTokenType.UNDEFINED;
                    CheckerPiece toRemove = null;
                    int tempx = 100;
                    int tempy = 100;
                    for (CheckerPiece gameIndex : gameIndexes) {
                        if (gameIndex != CheckersBoardGame.this.gameIndex
                                && gameIndex.x == (CheckersBoardGame.this.gameIndex.x + oldx) / 2
                                && gameIndex.y == (CheckersBoardGame.this.gameIndex.y + oldy) / 2) {
                            myGamePiece = gameIndex.gameTokenType;
                            toRemove = gameIndex;
                            tempx = gameIndex.x;
                            tempy = gameIndex.y;
                            System.out.println("Remove checker X = " + gameIndex.x + "Y = " + gameIndex.y);
                            break;
                        }
                    }
                    System.out.println(myGamePiece);

                    //Find the checker located at that region
                    if (toRemove != null && CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.RED_REGULAR
                            && (CheckersBoardGame.this.gameIndex.y - oldy == 2)
                            && (myGamePiece == GameTokenType.BLACK_REGULAR || myGamePiece == GameTokenType.BLACK_KING)) {
                        System.out.println("CAME IN HERE!!!!!");
                        moved = true;
                        myGame.numBlack--;
                        //Delete the black checker
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        repaint();
                        System.out.println("Red jumped black: Numblack is now = " + myGame.numBlack + "\n");
                    } else if (toRemove != null && CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.RED_KING
                            && (Math.abs(CheckersBoardGame.this.gameIndex.y - oldy) == 2)
                            && (myGamePiece == GameTokenType.BLACK_REGULAR || myGamePiece == GameTokenType.BLACK_KING)) {
                        moved = true;
                        myGame.numBlack--;
                        //Delete the black checker
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        repaint();
                        System.out.println("Red jumped black: Numblack is now = " + myGame.numBlack + "\n");
                    } else if (toRemove != null && CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.BLACK_REGULAR
                            && (CheckersBoardGame.this.gameIndex.y - oldy == -2)
                            && (myGamePiece == GameTokenType.RED_REGULAR || myGamePiece == GameTokenType.RED_KING)) {
                        moved = true;
                        myGame.numRed--;
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        repaint();
                        System.out.println("Black jumped red: NumRed is now = " + myGame.numRed + "\n");
                    } else if (toRemove != null && CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.BLACK_KING
                            && (Math.abs(CheckersBoardGame.this.gameIndex.y - oldy) == 2)
                            && (myGamePiece == GameTokenType.RED_REGULAR || myGamePiece == GameTokenType.RED_KING)) {
                        moved = true;
                        myGame.numRed--;
                        //Delete the red checker
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        repaint();
                        System.out.println("Black jumped red: NumRed is now = " + myGame.numRed + "\n");
                    } else {
                        System.out.println("could not remove!");
                        CheckersBoardGame.this.gameIndex.cx = oldcx;
                        CheckersBoardGame.this.gameIndex.cy = oldcy;
                        CheckersBoardGame.this.gameIndex.x = oldx;
                        CheckersBoardGame.this.gameIndex.y = oldy;
                        moved = false;
                    }
                } else if (Math.abs(CheckersBoardGame.this.gameIndex.x - oldx) == 4) {
                    System.out.println("Made it to two squares - double jump!\n");
                    //Check the piece jumped across
                    GameTokenType myGamePiece = GameTokenType.UNDEFINED;
                    CheckerPiece toRemove = null;
                    int tempx = 100;
                    int tempy = 100;

                    GameTokenType myGamePiece2 = GameTokenType.UNDEFINED;
                    CheckerPiece toRemove2 = null;
                    int tempx2 = 100;
                    int tempy2 = 100;
                    System.out.println("X:= " + CheckersBoardGame.this.gameIndex.x + "  Y:= " + CheckersBoardGame.this.gameIndex.y);
                    System.out.println("oldX:= " + oldx + " oldY:= " + oldy);
                    int halfx = (oldx + CheckersBoardGame.this.gameIndex.x) / 2;
                    int halfy = (oldy + CheckersBoardGame.this.gameIndex.y) / 2;
                    System.out.println("halfx = " + halfx + " halfy = " + halfy);

                    for (CheckerPiece gameIndex : gameIndexes) {
                        if (gameIndex != CheckersBoardGame.this.gameIndex
                                && gameIndex.x == (halfx + oldx) / 2
                                && gameIndex.y == (halfy + oldy) / 2) {
                            myGamePiece = gameIndex.gameTokenType;
                            toRemove = gameIndex;
                            tempx = gameIndex.x;
                            tempy = gameIndex.y;
                            System.out.println("Remove checker X = " + gameIndex.x + "Y = " + gameIndex.y);
                            break;
                        }
                    }

                    System.out.println("X = " + halfx + " Y = " + halfy);

                    for (CheckerPiece gameIndex2 : gameIndexes) {
                        if (gameIndex2 != CheckersBoardGame.this.gameIndex
                                && gameIndex2.x == (CheckersBoardGame.this.gameIndex.x + halfx) / 2
                                && gameIndex2.y == (CheckersBoardGame.this.gameIndex.y + halfy) / 2) {
                            myGamePiece2 = gameIndex2.gameTokenType;
                            toRemove2 = gameIndex2;
                            tempx2 = gameIndex2.x;
                            tempy2 = gameIndex2.y;
                            System.out.println("Remove checker X = " + gameIndex2.x + "Y = " + gameIndex2.y);
                            break;
                        }
                    }
                    System.out.println(myGamePiece);
                    System.out.println(myGamePiece2);

                    //Find the checker located at that region
                    if (toRemove != null && toRemove2 != null && CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.RED_REGULAR
                            && (CheckersBoardGame.this.gameIndex.y - oldy == 4)
                            && (myGamePiece == GameTokenType.BLACK_REGULAR || myGamePiece == GameTokenType.BLACK_KING)
                            && (myGamePiece2 == GameTokenType.BLACK_REGULAR || myGamePiece2 == GameTokenType.BLACK_KING)) {
                        System.out.println("CAME IN HERE!!!!!");
                        moved = true;
                       myGame.numBlack--;
                        myGame.numBlack--;
                        //Delete the black checker
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx2
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy2) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        repaint();
                        System.out.println("Red jumped black: Numblack is now = " + myGame.numBlack + "\n");
                    } else if (toRemove != null && toRemove2 != null && CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.RED_KING
                            && (Math.abs(CheckersBoardGame.this.gameIndex.y - oldy) == 4)
                            && (myGamePiece == GameTokenType.BLACK_REGULAR || myGamePiece == GameTokenType.BLACK_KING)
                            && (myGamePiece2 == GameTokenType.BLACK_REGULAR || myGamePiece2 == GameTokenType.BLACK_KING)) {
                        moved = true;
                        myGame.numBlack--;
                        myGame.numBlack--;
                        //Delete the black checker
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx2
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy2) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        repaint();
                        System.out.println("Red jumped black: Numblack is now = " + myGame.numBlack + "\n");
                    } else if (toRemove != null && toRemove2 != null && CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.BLACK_REGULAR
                            && (CheckersBoardGame.this.gameIndex.y - oldy == -4)
                            && (myGamePiece == GameTokenType.RED_REGULAR || myGamePiece == GameTokenType.RED_KING)
                            && (myGamePiece2 == GameTokenType.RED_REGULAR || myGamePiece2 == GameTokenType.RED_KING)) {
                        moved = true;
                        myGame.numRed--;
                        myGame.numRed--;
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx2
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy2) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        repaint();
                        System.out.println("Black jumped red: NumRed is now = " + myGame.numRed + "\n");
                    } else if (toRemove != null && toRemove2 != null && CheckersBoardGame.this.gameIndex.gameTokenType == GameTokenType.BLACK_KING
                            && (Math.abs(CheckersBoardGame.this.gameIndex.y - oldy) == 4)
                            && (myGamePiece == GameTokenType.RED_REGULAR || myGamePiece == GameTokenType.RED_KING)
                            && (myGamePiece2 == GameTokenType.RED_REGULAR || myGamePiece2 == GameTokenType.RED_KING)) {
                        moved = true;
                        myGame.numRed--;
                        myGame.numRed--;
                        //Delete the red checker
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        for (int index = 0; index < CheckersBoardGame.this.gameIndexes.size(); index++) {
                            if (CheckersBoardGame.this.gameIndexes.get(index).x == tempx2
                                    && CheckersBoardGame.this.gameIndexes.get(index).y == tempy2) {
                                CheckersBoardGame.this.gameIndexes.remove(index);
                                System.out.println("Removed");
                                break;
                            }
                        }
                        repaint();
                        System.out.println("Black jumped red: NumRed is now = " + myGame.numRed + "\n");
                    } else {
                        System.out.println("could not remove!");
                        CheckersBoardGame.this.gameIndex.cx = oldcx;
                        CheckersBoardGame.this.gameIndex.cy = oldcy;
                        CheckersBoardGame.this.gameIndex.x = oldx;
                        CheckersBoardGame.this.gameIndex.y = oldy;
                        moved = false;
                    }
                } else {
                    System.out.println("could not remove - reason 2!");
                    CheckersBoardGame.this.gameIndex.x = oldx;
                    CheckersBoardGame.this.gameIndex.y = oldy;
                    CheckersBoardGame.this.gameIndex.cx = oldcx;
                    CheckersBoardGame.this.gameIndex.cy = oldcy;
                    moved = false;
                }

                //Repaint and switch move
                repaint();
                //Check for kinged checkers
                if (moved && CheckersBoardGame.this.gameIndex.y == 8 && myGame.currentTurn == "red") {
                    CheckersBoardGame.this.gameIndex.gameTokenType = GameTokenType.RED_KING;
                } else if (moved && CheckersBoardGame.this.gameIndex.y == 1 && myGame.currentTurn == "black") {
                    CheckersBoardGame.this.gameIndex.gameTokenType = GameTokenType.BLACK_KING;
                } else {
                    //Do nothing
                }
                //Repaint and switch move
                repaint();
                myGame.switchTurn(moved, myGame.currentTurn);
            }
        });

        // Attach a mouse motion listener to the applet. That listener listens
        // for mouse drag events.
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                if (isCurrentlyMoving) {
                    // Update location of checker center.
                    gameIndex.cx = me.getX() - deltax;
                    gameIndex.cy = me.getY() - deltay;
                    repaint();
                }
            }
        });

    }

    //Add pieces onto the list
    public void add(Piece checker, int row, int col) {
        if (row < 1 || row > 8) {
            throw new IllegalArgumentException("row out of range: " + row);
        }
        if (col < 1 || col > 8) {
            throw new IllegalArgumentException("col out of range: " + col);
        }
        CheckerPiece gameIndex = new CheckerPiece(checker.gameTokenType);
        gameIndex = (CheckerPiece) checker;
        gameIndex.x = col;
        gameIndex.y = row;
        gameIndex.cx = (col - 1) * TILESIZE + TILESIZE / 2;
        gameIndex.cy = (row - 1) * TILESIZE + TILESIZE / 2;
        for (CheckerPiece _gameIndex : gameIndexes) {
            if (gameIndex.cx == _gameIndex.cx && gameIndex.cy == _gameIndex.cy) {
                throw new OccupiedAlready("square at (" + row + ","
                        + col + ") is occupied");
            }
        }
        gameIndexes.add(gameIndex);
    }

    //Dummy function doesn't work
    public void delete(Piece checker, int row, int col) {
        CheckerPiece gameIndex = new CheckerPiece(checker.gameTokenType);
        gameIndex = (CheckerPiece) checker;
        gameIndex.cx = (col - 1) * TILESIZE + TILESIZE / 2;
        gameIndex.cy = (row - 1) * TILESIZE + TILESIZE / 2;
        gameIndexes.remove(gameIndex);
    }

    //Get the dimension and return it
    @Override
    public Dimension getPreferredSize() {
        return boardSize;
    }

    //Paint the components
    @Override
    protected void paintComponent(Graphics g) {
        paintCheckerBoard(g);
        for (CheckerPiece gameIndex : gameIndexes) {
            if (gameIndex != CheckersBoardGame.this.gameIndex) {
                gameIndex.draw(g, gameIndex.cx, gameIndex.cy);
            }
        }

        if (gameIndex != null) {
            gameIndex.draw(g, gameIndex.cx, gameIndex.cy);
        }
    }

    //Paint the checkerboard
    private void paintCheckerBoard(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint checkerboard
        for (int row = 0; row < 8; row++) {
            g.setColor(((row & 1) != 0) ? Color.BLACK : Color.GRAY);
            for (int col = 0; col < 8; col++) {
                g.fillRect(col * TILESIZE, row * TILESIZE, TILESIZE, TILESIZE);
                g.setColor((g.getColor() == Color.BLACK) ? Color.GRAY : Color.BLACK);
            }
        }
    }

}
