package othelloGame;

import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;

import GameEnvironment.GameDriver;
import GameEnvironment.GamePiece;
import GameEnvironment.Player;
import boardGameGUI.BoardGame;
import javafx.application.Platform;
import javafx.scene.image.Image;

public class OthelloDriver extends GameDriver{
	public int rows, cols;
	final Image redDisk = new Image("/resources/red-circle.jpg");
	final Image blueDisk = new Image("/resources/blue-circle.png");
	volatile CountDownLatch cl = new CountDownLatch(1);
	public OthelloDriver(String player1Name, String player2Name, int rows, int cols, String gameName) throws IOException {
		super(player1Name, player2Name, rows, cols, gameName);
		this.rows = rows;
		this.cols = cols;
		this.players = new OthelloPlayer[]{new OthelloPlayer(player1Name, 0), new OthelloPlayer(player2Name, 1)};
        boardGUI = new BoardGame(players[0], players[1],rows, cols, gameName);
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
	
	@Override
	public void runGame() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initializeBoardArray();
				System.out.println("hello");
				while (!isGameOver()) {
					System.out.println("waiting for click");
					if (boardGUI.boardClicked) {
//						cl.countDown();
						System.out.println("released here");
						System.out.println(boardGUI.rowClicked);
						System.out.println(boardGUI.colClicked);
						boardGUI.boardClicked = false;
					}	
				}
			}
		});
	}
	public boolean existsLegalMoves() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (isLegalMove(col, row)) {
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
			return existsLegalMoves();
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
		GamePiece cur = getLeftCell(x, y);
		while (cur != null) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = getLeftCell(x, y);
		}
		return false;
	}
	
	public boolean existsSameColorRight(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		GamePiece cur = getRightCell(x, y);
		while (cur != null) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = getRightCell(x, y);
		}
		return false;
	}
	
	public boolean existsSameColorUpper(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		GamePiece cur = getUpperCell(x, y);
		while (cur != null) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = getUpperCell(x, y);
		}
		return false;
	}
	
	public boolean existsSameColorLower(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		GamePiece cur = getLowerCell(x, y);
		while (cur != null) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = getLowerCell(x, y);
		}
		return false;
	}
	
	public boolean existsSameColorUpperLeft(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		GamePiece cur = getUpperLeftCell(x, y);
		while (cur != null) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = getUpperLeftCell(x, y);
		}
		return false;
	}
	
	public boolean existsSameColorUpperRight(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		GamePiece cur = getUpperRightCell(x, y);
		while (cur != null) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = getUpperRightCell(x, y);
		}
		return false;
	}
	
	public boolean existsSameColorLowerLeft(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		GamePiece cur = getLowerLeftCell(x, y);
		while (cur != null) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = getLowerLeftCell(x, y);
		}
		return false;
	}
	
	public boolean existsSameColorLowerRight(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		GamePiece cur = getLowerRightCell(x, y);
		while (cur != null) {
			if (cur.getName().equals(target)) {
				return true;
			}
			cur = getLowerRightCell(x, y);
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
	
	public boolean flipLeftCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getLeftCell(x, y);
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target)) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getLeftCell(cur.x, cur.y);
		}
		if (cur != null && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
			return true;
		}
		return false;
	}
	
	public boolean flipRightCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getRightCell(x, y);
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target)) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getRightCell(cur.x, cur.y);
		}
		if (cur != null && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
			return true;
		}
		return false;
	}
	
	public boolean flipUpperCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getUpperCell(x, y);;
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target)) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getUpperCell(cur.x, cur.y);
		}
		if (cur != null && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
			return true;
		}
		return false;
	}
	
	public boolean flipLowerCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getLowerCell(x, y);;
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target)) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getLowerCell(cur.x, cur.y);
		}
		if (cur != null && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
			return true;
		}
		return false;
	}
	
	public boolean flipUpperLeftCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getUpperLeftCell(x, y);
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target)) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getUpperLeftCell(cur.x, cur.y);
		}
		if (cur != null && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
			return true;
		}
		return false;
	}
	
	public boolean flipUpperRightCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getUpperRightCell(x, y);
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target)) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getUpperRightCell(cur.x, cur.y);
		}
		if (cur != null && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
			return true;
		}
		return false;
	}
	
	public boolean flipLowerLeftCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getLowerLeftCell(x, y);
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target)) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getLowerLeftCell(cur.x, cur.y);
		}
		if (cur != null && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
			return true;
		}
		return false;
	}
	
	public boolean flipLowerRightCells(int x, int y, String target) {
		OthelloPiece cur = (OthelloPiece) getLowerRightCell(x, y);
		Stack<OthelloPiece> toBeFlipped = new Stack<OthelloPiece>();
		while (cur != null && !cur.getName().equals(target)) {
			toBeFlipped.push(cur);
			cur = (OthelloPiece) getLowerRightCell(cur.x, cur.y);
		}
		if (cur != null && !toBeFlipped.isEmpty()) {
			int score = getCurrentPlayer().getScore();
			getCurrentPlayer().setPlayerScore(score + toBeFlipped.size());
			Player opposite = getOppositePlayer(currentPlayer);
			opposite.setPlayerScore(opposite.getScore() - toBeFlipped.size());
			while(!toBeFlipped.isEmpty()) {
				cur = toBeFlipped.pop();
				cur.flip();
				boardGUI.modifyCell(cur.x, cur.y, getCurrentPlayer().getGamePiece().getImage());
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isLegalMove(int x, int y) {
		String target = getCurrentPlayer().getGamePiece().getName();
		if (isOccupied(x, y)) {
			return false;
		}
		boolean isLegal = flipLeftCells(x, y, target);
		isLegal |= flipRightCells(x, y, target);
		isLegal |= flipLowerCells(x, y, target);
		isLegal |= flipUpperCells(x, y, target);
		isLegal |= flipUpperLeftCells(x, y, target);
		isLegal |= flipUpperRightCells(x, y, target);
		isLegal |= flipLowerLeftCells(x, y, target);
		isLegal |= flipLowerRightCells(x, y, target);
		return isLegal;
	}

	@Override
	public void makeMove(int x, int y) {
		if (isLegalMove(x, y)) {
			OthelloPiece move = ((OthelloPiece) boardArray[x][y]);
			if (currentPlayer == 0) {
				move.placeDisk("R");
			} else {
				move.placeDisk("B");
			}
		}
	}

	@Override
	public void run() {
		runGame();
	}

}
