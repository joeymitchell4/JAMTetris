import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TetrisBoard extends JPanel {
	private static final long serialVersionUID = -2768315633419131803L;

	private boolean[][] boardSpace;
	private int windowWidth = 700;
	
	Tetromino activePiece;
	ArrayList<Tetromino> boardPieces;

	public TetrisBoard() {
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		setupBoardSpace();
		setupKeyListener();
		
		activePiece = new Tetromino(TetrisColor.LIGHTBLUE, boardSpace);
		boardPieces = new ArrayList<Tetromino>();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// TODO: DO SOMETHING ELSE WITH THIS CRAP
		g.drawLine(windowWidth / 2 - 5 * 20 - 10, 0, windowWidth / 2 - 5 * 20
				- 10, 20 * 20);
		g.drawLine(windowWidth / 2 + 5 * 20 - 10, 0, windowWidth / 2 + 5 * 20
				- 10, 20 * 20);
		g.drawLine(0, 20 * 20, windowWidth, 20 * 20);

		activePiece.draw(g, this);
		for (int i = 0; i < boardPieces.size(); i++) {
//			System.out.println(boardPieces.size());
			boardPieces.get(i).draw(g, this);
		}
	}

	private void setupBoardSpace() {
		boardSpace = new boolean[21][12];

		for (int i = 0; i < 21; i++) {
			for (int j = 0; j < 12; j++) {
				if (i < 20 && j != 0 && j != 11)
					boardSpace[i][j] = false;
				else
					boardSpace[i][j] = true;
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
