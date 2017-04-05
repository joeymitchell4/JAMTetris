public class Tetromino {
	private boolean[][] occupiedSpace;
	private TetrisColor color;

	public Tetromino() {

	}

	public Tetromino(TetrisColor color) {
		this.color = color;
		
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
					{ false, false, false },
					{ false, true, false }, 
					{ true, true, true } };
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

	public void rotate() {
		int size = occupiedSpace.length;
		boolean[][] rotatedArray = new boolean[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = size - 1; j >= 0; j--) {
				rotatedArray[i][size - 1 - j] = occupiedSpace[j][i];
				System.out.print(rotatedArray[i][size - 1 - j] + " ");
			}
			System.out.println();
		}
		occupiedSpace = rotatedArray;
		System.out.println("ROTATED");
	}
}
