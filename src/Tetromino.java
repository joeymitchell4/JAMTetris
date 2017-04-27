import java.awt.Graphics;
import java.awt.Image;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;

public class Tetromino {

	private boolean[][] shapeSpace;
	private BoardBlock[][] boardSpace;
	private TetrisColor color;
	private Image img;

	private int x = 4, y = 0;
	private short width, height;
	private int blockSize = 20;

	private Integer[] colors;

	private boolean lock = false;

	public Tetromino(TetrisColor color, BoardBlock[][] boardSpace) {
		this.color = color;
		this.boardSpace = boardSpace;

		img = new ImageIcon("src/images/" + this.color.toString().toLowerCase() +  ".png")
				.getImage().getScaledInstance(blockSize, blockSize, Image.SCALE_DEFAULT);

		setupOccupiedSpace();
	}

	public TetrisColor getColor() {
		return color;
	}

	public void setColors(Integer[] colors) {
		this.colors = colors;
	}

	public void drawInactive(Graphics g, int x, int y) {
		for (int i = 0; i < shapeSpace.length; i++) {
			for (int j = 0; j < shapeSpace.length; j++) {
				if (shapeSpace[i][j]) {
					g.drawImage(img, x + j * blockSize, y + i * blockSize, null);
				}
			}
		}
	}

	public void draw(Graphics g, TetrisBoard board) {
		for (int i = 0; i < shapeSpace.length; i++) {
			for (int j = 0; j < shapeSpace.length; j++) {
				if (shapeSpace[i][j]) {
					g.drawImage(img, 240 + (x + j) * blockSize, (y + i) * blockSize, board);
					// TODO: DRAW SHADOW HERE
				}
			}
		}

		if (lock) {
			/* INSERT NEW PIECE INTO boardSpace */
			for (int i = 0; i < shapeSpace.length; i++) {
				for (int j = 0; j < shapeSpace[i].length; j++) {
					if (shapeSpace[i][j]) {
						boardSpace[y + i][x + j + 1].setTetrisBlock(new ImageIcon("src/images/" + 
								this.color.toString().toLowerCase() +  ".png")
								.getImage().getScaledInstance(blockSize, blockSize, Image.SCALE_DEFAULT));
						boardSpace[y + i][x + j + 1].setInUse(true);
					}
				}
			}

			/* CHECK FOR FULL ROWS & SHIFT DOWN IF NECESSARY */
			int numRowsCleared = 0;
			for (int i = 0; i < boardSpace.length - 1; i++) {
				if (isRowFull(i)) {
					board.rowsCleared++;
					numRowsCleared++;
					for (int j = 0; j < boardSpace[i].length; j++) {
						boardSpace[i][j].setInUse(false);
						for (int k = i; k >= 1; k--) {
							boardSpace[k][j].setInUse(boardSpace[k - 1][j].isInUse());
							boardSpace[k][j].setTetrisBlock(boardSpace[k - 1][j].getTetrisBlock());
						}
					}
				}
			}

			if (numRowsCleared == 4) { // TETRIS
				board.totalScore += 800; // TODO: CHECK FOR BACK-TO-BACK TETRIS
				board.levelScore += 800;
			} else {
				board.totalScore += 100 * numRowsCleared;
				board.levelScore += 100 * numRowsCleared;
			}

			if (board.levelScore >= board.roundTreshold) {
				board.level++;
				board.levelScore -= board.roundTreshold;
				board.setTimerDelay(1000 - 60 * (board.level - 1));
			}

			System.out.println();
			printBoard();
			System.out.println();

			lock = false; // UNLOCK

			board.activePiece = new Tetromino(board.previewPiece1.getColor(), boardSpace);
			board.previewPiece1 = new Tetromino(board.previewPiece2.getColor(), boardSpace);
			board.previewPiece2 = new Tetromino(board.previewPiece3.getColor(), boardSpace);
			board.previewPiece3 = new Tetromino(randomTetromino(), boardSpace);

			board.activePiece.setColors(new Integer[] {
					board.previewPiece1.getColor().ordinal(),
					board.previewPiece2.getColor().ordinal(),
					board.previewPiece3.getColor().ordinal()});

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
			if (boardSpace[y + i][x - numBlankRightRows + width + 1].isInUse() && shapeSpace[i][width - numBlankRightRows - 1]) {
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
			if (boardSpace[y + i][x + numBlankLeftRows].isInUse() && shapeSpace[i][numBlankLeftRows]) {
				canMove = false;
				break;
			}
		}

		if (canMove) x--;
	}

	public void incrementY() {
		boolean canMove = true;
		short numBlankBottomRows = getNumBlankBottomRows();

		try { // TODO: THIS IS HACKY
			for (int j = 0; j < width; j++) {
				if (boardSpace[y + height - numBlankBottomRows][x + j + 1].isInUse() && shapeSpace[height - numBlankBottomRows - 1][j]) {
					canMove = false;
					break;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			if (height != 4) {
				e.printStackTrace();
				return;
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

	public TetrisColor randomTetromino() { // CODE INSPIRED BY: http://stackoverflow.com/a/30641206
		int i = new Random().nextInt(TetrisColor.values().length);
		if (colors == null) return TetrisColor.values()[i];

		while (Arrays.asList(colors).contains(i)) { // GET A DIFFERENT RANDOM PIECE
			i = new Random().nextInt(TetrisColor.values().length);
		}

		return TetrisColor.values()[i];
	}

	private boolean isRowFull(int rowNumber) {
		for (int j = 0; j < boardSpace[0].length; j++) {
			if (!boardSpace[rowNumber][j].isInUse()) {
				return false;
			}
		}

		return true;
	}

	private void printBoard() {
		for (int i = 0; i < boardSpace.length; i++) {
			for (int j = 0; j < boardSpace[i].length; j++) {
				if (boardSpace[i][j].isInUse()) System.out.print("1");
				else System.out.print("0");
			}
			System.out.println();
		}
	}

}
