import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class TetrisBoard extends JPanel {
	private static final long serialVersionUID = -2768315633419131803L;

	private int windowHeight = 690, windowWidth = 700;
	private Tetromino t = new Tetromino(TetrisColor.LIGHTBLUE);

	public TetrisBoard() {
		this.setFocusable(true);
		this.requestFocusInWindow();

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
					t.incrementY();
					break;
				case KeyEvent.VK_LEFT:
					t.decrementX();
					break;
				case KeyEvent.VK_RIGHT:
					t.incrementX();
					break;
				case KeyEvent.VK_CONTROL:
					t.rotate();
					break;
				}
				repaint();
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawLine(windowWidth / 2 - 5 * 20 - 10, 0, windowWidth / 2 - 5 * 20
				- 10, windowHeight);
		g.drawLine(windowWidth / 2 + 5 * 20 - 10, 0, windowWidth / 2 + 5 * 20
				- 10, windowHeight);
		
		t.draw(g, this);
	}
}
