import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TetrisBoard extends JPanel {
	private static final long serialVersionUID = -2768315633419131803L;
	private Image img1, img2, img3, img4;
	private int dx = 0, dy = 0;
	private int windowHeight = 690, windowWidth = 700;

	public TetrisBoard() throws IOException {
		img1 = img2 = img3 = img4 = new ImageIcon("src/images/darkblue.png")
				.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);

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
					dy--;
					break;
				case KeyEvent.VK_DOWN:
					dy++;
					break;
				case KeyEvent.VK_LEFT:
					dx--;
					break;
				case KeyEvent.VK_RIGHT:
					dx++;
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

		g.drawImage(img1, dx * 20, dy * 20, this);
		g.drawImage(img2, dx * 20, dy * 20 + 20, this);
		g.drawImage(img3, dx * 20 + 20, dy * 20 + 20, this);
		g.drawImage(img4, dx * 20 + 40, dy * 20 + 20, this);
	}
}
