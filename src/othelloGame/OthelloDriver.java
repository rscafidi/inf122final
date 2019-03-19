package othelloGame;

import java.io.IOException;
import java.util.Stack;
import GameEnvironment.GameDriver;
import GameEnvironment.GamePiece;
import GameEnvironment.Player;
import boardGameGUI.BoardGame;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class OthelloDriver extends GameDriver{
	public int rows, cols;
	final Image redDisk = new Image("/resources/red-circle.jpg");
	final Image blueDisk = new Image("/resources/blue-circle.png");
	public OthelloDriver(String player1Name, String player2Name, int rows, int cols, String gameName) throws IOException {
		super(player1Name, player2Name, rows, cols, gameName);
		this.rows = rows;
		this.cols = cols;
		this.players = new OthelloPlayer[]{new OthelloPlayer(player1Name, 0), new OthelloPlayer(player2Name, 1)};
		players[0].setPlayerScore(2);
		players[1].setPlayerScore(2);
        boardGUI = new BoardGame(players[0], players[1],rows, cols, gameName);
        boardGUI.updatePlayer1Score();
        boardGUI.updatePlayer2Score();
        this.currentPlayer = 0;
        
	}

	@Override
	public void initializeBoardArray() {
		Image defaultCell = new Image("/boardGameGUI/white-square-cell.jpg");
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if ((row == 3 && col == 3) || (row == 4 && col == 4)) {
					boardGUI.modifyCell(col, row, redDisk);
					boardArray[row][col] = new OthelloPiece("R", redDisk, col, row);
				} else if((row == 3 && col == 4) || (row == 4 && col == 3)) {
					boardGUI.modifyCell(col, row, blueDisk);
					boardArray[row][col] = new OthelloPiece("B", blueDisk, col, row);
				} else {
					boardArray[row][col] = new OthelloPiece("-", defaultCell, col, row);
				}
			}
		}
	}
	public void printBoardArray() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				System.out.print(boardArray[row][col].getName() + " ");
			}
			System.out.println();
		}
	}
	public void runTurn(GridPane boardGame) {
		if (!isGameOver() && isLegalMove(boardGUI.colClicked, boardGUI.rowClicked)) {
			printBoardArray();
			makeMove(boardGUI.colClicked, boardGUI.rowClicked);
			printBoardArray();
			switchTurn();
			boardGUI.switchTurnGUI();
			boardGUI.updatePlayer1Score();
		    boardGUI.updatePlayer2Score();
		    }
		if (isGameOver()) {
			determineWinner();
			boardGUI.displayWinner(winner);
		}
	}
	
	public void determineWinner() {
		if (players[0].getScore() > players[1].getScore()) {
			winner = players[0].getUserName();
		} else if (players[0].getScore() < players[1].getScore()) {
			winner = players[1].getUserName();
		} else {
			winner = "TIE";
		}
	}
	
	@Override
	public void runGame() {
		initializeBoardArray();
	}
	public boolean existsLegalMoves() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (isLegalMove(col, row)) {
					System.out.print(row);
					System.out.println(col);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean isGameOver() {
		if (existsLegalMoves()) {
			return false;
		} else {
			switchTurn();
			boardGUI.switchTurnGUI();
			return !existsLegalMoves();
		}
	}
	public boolean isOccupied(int x, int y) {
		if (boardArray[y][x].getName().equals("-")) {
			return false;
		}
		return true;
	}
	
	public GamePiece getLeftCell(int x, int y) {
		if (x - 1 >= 0) {
			return boardArray[y][x-1];
		}
		return null;
	}
	
	public GamePiece getRightCell(int x, int y) {
		if (x + 1 < cols) {
			return boardArray[y][x+1];
		}
		return null;
	}
	
	public GamePiece getUpperCell(int x, int y) {
		if (y - 1 >= 0) {
			return boardArray[y - 1][x];
		}
		return null;
	}
	
	public GamePiece getLowerCell(int x, int y) {
		if (y + 1 < rows) {
			return boardArray[y + 1][x];
		}
		return null;
	}
	
	public GamePiece getUpperLeftCell(int x, int y) {
		if (y - 1 >= 0 && x - 1 >= 0) {
			return boardArray[y - 1][x - 1];
		}
		return null;
	}
	
	public GamePiece getUpperRightCell(int x, int y) {
		if (y - 1 >= 0 && x + 1 < cols) {
			return boardArray[y - 1][x + 1];
		}
		return null;
	}
	
	public GamePiece getLowerLeftCell(int x, int y) {
		if (y + 1 < rows && x - 1 >= 0) {
			return boardArray[y+1][x-1];
		}
		return null;
	}
	
	public GamePiece getLowerRightCell(int x, int y) {
		if (y + 1 < rows && x + 1 < cols) {
			return boardArray[y+1][x+1];
		}
		return null;
	}
	
	public Player getCurrentPlayer() {
		return players[currentPlayer];
	}
	
	public boolean existsSameColorLeft(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getLeftCell(x, y);
		while (cur != null && !cur.getName().equals("-")) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = (OthelloPiece) getLeftCell(cur.x, cur.y);
		}
		return false;
	}
	
	public boolean existsSameColorRight(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getRightCell(x, y);
		while (cur != null && !cur.getName().equals("-")) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = (OthelloPiece) getRightCell(cur.x, cur.y);
		}
		return false;
	}
	
	public boolean existsSameColorUpper(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getUpperCell(x, y);
		while (cur != null && !cur.getName().equals("-")) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = (OthelloPiece) getUpperCell(cur.x, cur.y);
		}
		return false;
	}
	
	public boolean existsSameColorLower(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getLowerCell(x, y);
		while (cur != null && !cur.getName().equals("-")) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = (OthelloPiece) getLowerCell(cur.x, cur.y);
		}
		return false;
	}
	
	public boolean existsSameColorUpperLeft(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getUpperLeftCell(x, y);
		while (cur != null && !cur.getName().equals("-")) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = (OthelloPiece) getUpperLeftCell(cur.x, cur.y);
		}
		return false;
	}
	
	public boolean existsSameColorUpperRight(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getUpperRightCell(x, y);
		while (cur != null && !cur.getName().equals("-")) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = (OthelloPiece) getUpperRightCell(cur.x, cur.y);
		}
		return false;
	}
	
	public boolean existsSameColorLowerLeft(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getLowerLeftCell(x, y);
		while (cur != null && !cur.getName().equals("-")) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = (OthelloPiece) getLowerLeftCell(cur.x, cur.y);
		}
		return false;
	}
	
	public boolean existsSameColorLowerRight(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getLowerRightCell(x, y);
		while (cur != null && !cur.getName().equals("-")) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = (OthelloPiece) getLowerRightCell(cur.x, cur.y);
		}
		return false;
	}
	
	public Player getOppositePlayer(int turn) {
		if (turn == 0) {
			return players[1];
		} else {
			return players[0];
		}
	}
	
	public void flipLeftCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getLeftCell(x, y);
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target) && !cur.getName().equals("-")) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getLeftCell(cur.x, cur.y);
		}
		if (cur != null && !cur.getName().equals("-") && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
		}
	}
	
	public void flipRightCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getRightCell(x, y);
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target) && !cur.getName().equals("-")) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getRightCell(cur.x, cur.y);
		}
		if (cur != null && !cur.getName().equals("-") && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
		}
	}
	
	public void flipUpperCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getUpperCell(x, y);;
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target) && !cur.getName().equals("-")) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getUpperCell(cur.x, cur.y);
		}
		if (cur != null && !cur.getName().equals("-") && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
		}
	}
	
	public void flipLowerCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getLowerCell(x, y);;
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target) && !cur.getName().equals("-")) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getLowerCell(cur.x, cur.y);
		}
		if (cur != null && !cur.getName().equals("-") && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
		}
	}
	
	public void flipUpperLeftCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getUpperLeftCell(x, y);
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target) && !cur.getName().equals("-")) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getUpperLeftCell(cur.x, cur.y);
		}
		if (cur != null && !cur.getName().equals("-") && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
		}
	}
	
	public void flipUpperRightCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getUpperRightCell(x, y);
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target) && !cur.getName().equals("-")) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getUpperRightCell(cur.x, cur.y);
		}
		if (cur != null && !cur.getName().equals("-") &&  !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
		}
	}
	
	public void flipLowerLeftCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getLowerLeftCell(x, y);
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target) && !cur.getName().equals("-")) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getLowerLeftCell(cur.x, cur.y);
		}
		if (cur != null && !cur.getName().equals("-") && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
		}
	}
	
	public void flipLowerRightCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getLowerRightCell(x, y);
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target) && !cur.getName().equals("-")) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getLowerRightCell(cur.x, cur.y);
		}
		if (cur != null && !cur.getName().equals("-") && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
		}
	}
	public boolean isValidLeft(int x, int y) {
		String oppositeColor = players[currentPlayer^1].getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getLeftCell(x,y);
		return cur!= null && cur.getName().equals(oppositeColor) && existsSameColorLeft(cur.x, cur.y);
	}
	
	public boolean isValidRight(int x, int y) {
		String oppositeColor = players[currentPlayer^1].getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getRightCell(x,y);
		return cur!= null && cur.getName().equals(oppositeColor) && existsSameColorRight(cur.x, cur.y);
	}
	
	public boolean isValidUpper(int x, int y) {
		String oppositeColor = players[currentPlayer^1].getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getUpperCell(x,y);
		return cur!= null && cur.getName().equals(oppositeColor) && existsSameColorUpper(cur.x, cur.y);
	}
	
	public boolean isValidLower(int x, int y) {
		String oppositeColor = players[currentPlayer^1].getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getLowerCell(x,y);
		return cur!= null && cur.getName().equals(oppositeColor) && existsSameColorLower(cur.x, cur.y);
	}
	
	public boolean isValidUpperLeft(int x, int y) {
		String oppositeColor = players[currentPlayer^1].getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getUpperLeftCell(x,y);
		return cur!= null && cur.getName().equals(oppositeColor) && existsSameColorUpperLeft(cur.x, cur.y);
	}
	
	public boolean isValidUpperRight(int x, int y) {
		String oppositeColor = players[currentPlayer^1].getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getUpperRightCell(x,y);
		return cur!= null && cur.getName().equals(oppositeColor) && existsSameColorUpperRight(cur.x, cur.y);
	}
	
	public boolean isValidLowerLeft(int x, int y) {
		String oppositeColor = players[currentPlayer^1].getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getLowerLeftCell(x,y);
		return cur!= null && cur.getName().equals(oppositeColor) && existsSameColorLowerLeft(cur.x, cur.y);
	}
	
	public boolean isValidLowerRight(int x, int y) {
		String oppositeColor = players[currentPlayer^1].getGamePiece().getName();
		OthelloPiece cur = (OthelloPiece) getLowerRightCell(x,y);
		return cur!= null && cur.getName().equals(oppositeColor) && existsSameColorLowerRight(cur.x, cur.y);
	}
	@Override
	public boolean isLegalMove(int x, int y) {
		if (isOccupied(x, y)) {
			return false;
		}
		return isValidLeft(x, y) || isValidRight(x, y) || isValidUpper(x, y) || isValidLower(x, y) || 
				isValidUpperLeft(x, y) || isValidUpperRight(x, y) || isValidLowerLeft(x, y) || isValidLowerRight(x, y);
	}

	@Override
	public void makeMove(int x, int y) {
			String target = getCurrentPlayer().getGamePiece().getName();
			OthelloPiece move = ((OthelloPiece) boardArray[y][x]);
			if (currentPlayer == 0) {
				move.placeDisk("R");
			} else {
				move.placeDisk("B");
			}
			updateScore(currentPlayer, getCurrentPlayer().getScore() + 1);
			boardGUI.modifyCell(x, y, getCurrentPlayer().getGamePiece().getImage());
			flipLeftCells(x, y, target);
			flipRightCells(x, y, target);
			flipUpperCells(x, y, target);
			flipLowerCells(x, y, target);
			flipUpperRightCells(x, y, target);
			flipUpperLeftCells(x, y, target);
			flipLowerRightCells(x, y, target);
			flipLowerLeftCells(x, y, target);
	}
}
