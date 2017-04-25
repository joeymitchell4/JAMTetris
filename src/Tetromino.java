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
				System.out.println();
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

		short temp = width;
		width = height;
		height = temp;
	}

	public void incrementX() {
		x++;
		// TODO: FINISH THIS
	}

	public void decrementX() {
		boolean canMove = true;
		for (int i = 0; i < height; i++) {
			if (boardSpace[y + i][x] && shapeSpace[i][0]) {
				canMove = false;
			}
		}

		if (canMove) x--;
	}

	public void incrementY() {
		boolean canMove = true;
		for (int j = 0; j < width; j++) {
			if (boardSpace[y + height][x + j + 1] && (shapeSpace[height - 1][j] || width == 1)) { // TODO: HACKY
				canMove = false;
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
			width = 1;
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
