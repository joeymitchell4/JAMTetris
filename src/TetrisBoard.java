import java.awt.Graphics;
import java.awt.Image;
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

	Tetromino activePiece;

	public TetrisBoard() {
		this.setFocusable(true);
		this.requestFocusInWindow();

		setupBoardSpace();
		setupKeyListener();

		leftBorder = windowWidth / 2 - 5 * blockSize - 10;
		rightBorder = windowWidth / 2 + 5 * blockSize - 10;

		activePiece = new Tetromino(TetrisColor.LIGHTBLUE, boardSpace);

        timer = new Timer(1500, new ActionListener() {
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

		activePiece.draw(g, this);
		for (int i = 0; i < boardSpace.length; i++) {
			for (int j = 0; j < boardSpace[i].length; j++) {
				if (boardSpace[i][j].isInUse() && i < 20 && j != 0 && j != 11)
					g.drawImage(boardSpace[i][j].getTetrisBlock(), leftBorder + (j - 1) * blockSize, i * blockSize, null);
			}
		}
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
					activePiece.rotate();
					break;
				}
				repaint();
			}
		});
	}
}
