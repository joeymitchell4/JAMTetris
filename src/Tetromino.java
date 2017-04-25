import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Tetromino {

	private boolean[][] shapeSpace, boardSpace;
	private TetrisColor color;
	private Image img;

	private int x = 4, y = 0; // TODO: GET RID OF THIS HARDCODING
	private short width, height;
	private int size = 20;

	private boolean lock = false;

	public Tetromino(TetrisColor color, boolean[][] boardSpace) {
		this.color = color;
		this.boardSpace = boardSpace;

		img = new ImageIcon("src/images/" + this.color.toString().toLowerCase() +  ".png")
				.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT);

		setupOccupiedSpace();
	}

	public void draw(Graphics g, TetrisBoard board) {
		for (int i = 0; i < shapeSpace.length; i++) {
			for (int j = 0; j < shapeSpace.length; j++) {
				if (shapeSpace[i][j]) {
					g.drawImage(img, 240 + (x + j) * size, (y + i) * size, board);
				}
			}
		}

		if (lock) {
			for (int i = 0; i < shapeSpace.length; i++) {
				for (int j = 0; j < shapeSpace[i].length; j++) {
					if (shapeSpace[i][j]) { 
						boardSpace[y + i][x + j + 1] = true;
					}
				}
			}

			System.out.println();
			printBoard();
			System.out.println();

			lock = false;
			board.boardPieces.add(this);
			board.activePiece = new Tetromino(nextTetromino(), boardSpace);
			board.activePiece.draw(g, board);
		}
	}

	public void rotate() {
		if (color == TetrisColor.YELLOW) return; // YELLOW DOESN'T ROTATE

		int size = shapeSpace.length;
		boolean[][] rotatedArray = new boolean[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = size - 1; j >= 0; j--) {
				rotatedArray[i][size - 1 - j] = shapeSpace[j][i];
			}
		}
		shapeSpace = rotatedArray;
	}

	public void incrementX() {
		boolean canMove = true;
		short numBlankRightRows = getNumBlankRightRows();

		for (int i = 0; i < height; i++) {
			if (boardSpace[y + i][x - numBlankRightRows + width + 1] && shapeSpace[i][width - numBlankRightRows - 1]) {
				canMove = false;
				break;
			}
		}

		if (canMove) x++;
	}

	public void decrementX() {
		boolean canMove = true;
		short numBlankLeftRows = getNumBlankLeftRows();

		for (int i = 0; i < height; i++) {
			if (boardSpace[y + i][x + numBlankLeftRows] && shapeSpace[i][numBlankLeftRows]) {
				canMove = false;
				break;
			}
		}

		if (canMove) x--;
	}

	public void incrementY() {
		boolean canMove = true;
		short numBlankBottomRows = getNumBlankBottomRows();
		
		for (int j = 0; j < width; j++) {
			if (boardSpace[y + height - numBlankBottomRows][x + j + 1] && shapeSpace[height - numBlankBottomRows - 1][j]) {
				canMove = false;
				break;
			}
		}

		if (canMove) y++;
		else lock = true;
	}

	private void setupOccupiedSpace() {
		switch (this.color) {
		case DARKBLUE:
			shapeSpace = new boolean[][] { 
					{ false, true, false },
					{ false, true, false }, 
					{ true, true, false } };
			width = 3;
			height = 3;
			break;
		case GREEN:
			shapeSpace = new boolean[][] { 
					{ false, false, false },
					{ false, true, true }, 
					{ true, true, false } };
			width = 3;
			height = 3;
			break;
		case LIGHTBLUE:
			shapeSpace = new boolean[][] { 
					{ false, true, false, false },
					{ false, true, false, false }, 
					{ false, true, false, false },
					{ false, true, false, false } };
			width = 4;
			height = 4;
			break;
		case ORANGE:
			shapeSpace = new boolean[][] { 
					{ false, true, false },
					{ false, true, false }, 
					{ false, true, true } };
			width = 3;
			height = 3;
			break;
		case PURPLE:
			shapeSpace = new boolean[][] { 
					{ false, true, false },
					{ true, true, false }, 
					{ false, true, false } };
			width = 3;
			height = 3;
			break;
		case RED:
			shapeSpace = new boolean[][] { 
					{ false, false, false },
					{ true, true, false }, 
					{ false, true, true } };
			width = 3;
			height = 3;
			break;
		case YELLOW:
			shapeSpace = new boolean[][] { 
					{ true, true },
					{ true, true } };
			width = 2;
			height = 2;
			break;
		}
	}

	private short getNumBlankBottomRows() {
		short num = 0;
		for (int i = height - 1; i >= 0; i--) {
			boolean hasBlankRow = true;
			for (int j = 0; j < width; j++) {
				if (shapeSpace[i][j]) {
					hasBlankRow = false;
					break;
				}
			}

			if (hasBlankRow) num++;
			else break;
		}

		return num;
	}

	private short getNumBlankLeftRows() {
		short num = 0;
		for (int i = 0; i < height; i++) {
			boolean hasBlankRow = true;
			for (int j = 0; j < height; j++) {
				if (shapeSpace[j][i]) {
					hasBlankRow = false;
					break;
				}
			}

			if (hasBlankRow) num++;
			else break;
		}

		return num;
	}

	private short getNumBlankRightRows() {
		short num = 0;
		for (int i = width - 1; i >= 0; i--) {
			boolean hasBlankRow = true;
			for (int j = 0; j < height; j++) {
				if (shapeSpace[j][i]) {
					hasBlankRow = false;
					break;
				}
			}

			if (hasBlankRow) num++;
			else break;
		}

		return num;
	}

	private TetrisColor nextTetromino() { // CODE INSPIRED BY: http://stackoverflow.com/a/30641206
		return TetrisColor.values()[new Random().nextInt(TetrisColor.values().length)];
	}

	private void printBoard() {
		for (int i = 0; i < boardSpace.length; i++) {
			for (int j = 0; j < boardSpace[i].length; j++) {
				if (boardSpace[i][j]) System.out.print("1");
				else System.out.print("0");
			}
			System.out.println();
		}
	}

}
