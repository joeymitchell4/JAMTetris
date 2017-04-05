import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Tetromino {
	private boolean[][] occupiedSpace;
	private TetrisColor color;
	private Image img;
	private int x, y;
	private int size = 20;

	public Tetromino() {

	}

	public Tetromino(TetrisColor color) {
		this.color = color;

		img = new ImageIcon("src/images/" + this.color.toString().toLowerCase() +  ".png")
				.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT);

		switch (color) {
		case DARKBLUE:
			occupiedSpace = new boolean[][] { 
					{ false, false, false },
					{ true, false, false }, 
					{ true, true, true } };
			break;
		case GREEN:
			occupiedSpace = new boolean[][] { 
					{ false, false, false },
					{ false, true, true }, 
					{ true, true, false } };
			break;
		case LIGHTBLUE:
			occupiedSpace = new boolean[][] { 
					{ false, false, false, false },
					{ false, false, false, false }, 
					{ false, false, false, false },
					{ true, true, true, true } };
			break;
		case ORANGE:
			occupiedSpace = new boolean[][] { 
					{ false, false, false },
					{ false, false, true }, 
					{ true, true, true } };
			break;
		case PURPLE:
			occupiedSpace = new boolean[][] { 
					{ false, true, false },
					{ true, true, false }, 
					{ false, true, false } };
			break;
		case RED:
			occupiedSpace = new boolean[][] { 
					{ false, false, false },
					{ true, true, false }, 
					{ false, true, true } };
			break;
		case YELLOW:
			occupiedSpace = new boolean[][] { 
					{ true, true },
					{ true, true} };
			break;
		}
	}
	
	public void draw(Graphics g, TetrisBoard board) {
		for (int i = 0; i < occupiedSpace.length; i++) {
			for (int j = 0; j < occupiedSpace.length; j++) {
				if (occupiedSpace[i][j]) {
					g.drawImage(img, (x + j) * size, (y + i) * size, board);
				}
			}
		}
	}

	public void rotate() {
		if (color == TetrisColor.YELLOW) return; // YELLOW DOESN'T ROTATE
		
		int size = occupiedSpace.length;
		boolean[][] rotatedArray = new boolean[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = size - 1; j >= 0; j--) {
				rotatedArray[i][size - 1 - j] = occupiedSpace[j][i];
//				System.out.print(rotatedArray[i][size - 1 - j] + " ");
//				System.out.println("i = " + i + ", j = " + j);
			}
//			System.out.println();
		}
		occupiedSpace = rotatedArray;
//		System.out.println("ROTATED");
	}

	public void incrementX() {
		x++;
	}

	public void decrementX() {
		x--;
	}

	public void incrementY() {
		y++;
	}
}
