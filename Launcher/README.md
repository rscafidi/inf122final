The Memory Game is in the Launcher Folder in the MemGame folder

Had to make some changes from the Game Environment but I'll try to see if I can avoid these changes in the final version

Changes include: 

In BoardGame.java: 
-Making rowclicked, colclicked, and boardclicked public 
-Editing displayerWinner() in BoardGame.java to account for a tie game scenario
-Some changes in addCell() to call the handleMove function (couldn't get while loop and run game to work)
-Some changes in modifyCell() to call the handleMove function and also refresh grid lines

In Player.java:
-Adding gamePiece instance variable
-Adding setGamePiece(Gamepiece gamepiece) and getGamePiece() functions 

In GameDriver.java:
-Making boardGUI, currentPlayer, and players public (so MemoryDriver could access them)

Also MemoryPlayer class might not be necessary anymore after making changes to Player.java 

