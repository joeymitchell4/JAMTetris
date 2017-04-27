import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class TetrisBoard extends JPanel {
	private static final long serialVersionUID = -2768315633419131803L;

	private BoardBlock[][] boardSpace;
	private int windowWidth = 700;
	private int blockSize = 20;
	private int leftBorder, rightBorder;

	private Timer timer = null;

	Tetromino activePiece, previewPiece1, previewPiece2, previewPiece3, holdPiece;
	int rowsCleared = 0, level = 1;
	int totalScore = 0, levelScore = 0;
	int roundTreshold = 1000;

	public TetrisBoard() {
		this.setFocusable(true);
		this.requestFocusInWindow();

		setupBoardSpace();
		setupKeyListener();

		leftBorder = windowWidth / 2 - 5 * blockSize - 10;
		rightBorder = windowWidth / 2 + 5 * blockSize - 10;

		activePiece = new Tetromino(TetrisColor.LIGHTBLUE, boardSpace); // TODO: DON'T HARDCODE FIRST COLOR
		activePiece.setColors(new Integer[] { 1, 2, 3 }); // TODO: HACKY

		previewPiece1 = new Tetromino(activePiece.randomTetromino(), boardSpace); // TODO: THIS IS BAAAAD PROGRAMMING
		previewPiece2 = new Tetromino(activePiece.randomTetromino(), boardSpace);
		previewPiece3 = new Tetromino(activePiece.randomTetromino(), boardSpace);

		activePiece.setColors(new Integer[] {
				previewPiece1.getColor().ordinal(),
				previewPiece2.getColor().ordinal(),
				previewPiece3.getColor().ordinal()});

		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activePiece.incrementY();
				repaint();
			}
		});

		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		/* DRAW FIELD BORDERS */
		g.drawLine(leftBorder, 0, leftBorder, blockSize * blockSize);
		g.drawLine(rightBorder, 0, rightBorder, blockSize * blockSize);
		g.drawLine(0, blockSize * blockSize, windowWidth, blockSize * blockSize);

		/* DRAW NEXT PIECES */
		g.drawString("NEXT UP", rightBorder + 25, 25);
		previewPiece1.drawInactive(g, rightBorder + 25, 50);
		previewPiece2.drawInactive(g, rightBorder + 25, 140);
		previewPiece3.drawInactive(g, rightBorder + 25, 230);

		/* DRAW HOLDER */
		g.drawString("ON HOLD", leftBorder - 75, 25);
		if (holdPiece != null) holdPiece.drawInactive(g, leftBorder - 75, 50);

		/* DRAW INFORMATION TEXT ON BOTTOM */
		g.drawString("ROWS CLEARED: " + rowsCleared, leftBorder + 50, 425);
		g.drawString("LEVEL: " + level, leftBorder + 85, 450);
		g.drawString("SCORE: " + totalScore, rightBorder + 85, 450);

		activePiece.draw(g, this); // DRAW ACTIVE PIECE
		/* DRAW ENTIRE BOARD (boardSpace) */
		for (int i = 0; i < boardSpace.length; i++) {
			for (int j = 0; j < boardSpace[i].length; j++) {
				if (boardSpace[i][j].isInUse() && i < 20 && j != 0 && j != 11)
					g.drawImage(boardSpace[i][j].getTetrisBlock(), leftBorder + (j - 1) * blockSize, i * blockSize, null);
			}
		}
	}

	public void setTimerDelay(int delay) {
		timer.setDelay(delay);
	}

	private void setupBoardSpace() {
		boardSpace = new BoardBlock[21][12];

		for (int i = 0; i < 21; i++) {
			for (int j = 0; j < 12; j++) {
				boardSpace[i][j] = new BoardBlock();
				if (i < 20 && j != 0 && j != 11) {
					boardSpace[i][j].setInUse(false);
				} else {
					boardSpace[i][j].setInUse(true);
				}
			}
		}
	}

	private void setupKeyListener() {
		this.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				// CODE INSPIRED BY: http://stackoverflow.com/a/617004
				int keyCode = e.getKeyCode();
				switch (keyCode) {
				case KeyEvent.VK_UP:
					break;
				case KeyEvent.VK_DOWN:
					timer.restart();
					activePiece.incrementY();
					break;
				case KeyEvent.VK_LEFT:
					activePiece.decrementX();
					break;
				case KeyEvent.VK_RIGHT:
					activePiece.incrementX();
					break;
				case KeyEvent.VK_CONTROL:
					timer.restart();
					activePiece.rotate();
					break;
				case KeyEvent.VK_SPACE:
					timer.restart();
					if (holdPiece == null) {
						holdPiece = new Tetromino(activePiece.getColor(), boardSpace);
						activePiece = new Tetromino(previewPiece1.getColor(), boardSpace);
						previewPiece1 = new Tetromino(previewPiece2.getColor(), boardSpace);
						previewPiece2 = new Tetromino(previewPiece3.getColor(), boardSpace);
						previewPiece3 = new Tetromino(previewPiece1.randomTetromino(), boardSpace);
					} else {
						TetrisColor c = holdPiece.getColor();
						holdPiece = new Tetromino(activePiece.getColor(), boardSpace);
						activePiece = new Tetromino(c, boardSpace);
					}
					break;
				}
				repaint();
			}
		});
	}
}
