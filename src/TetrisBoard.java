import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TetrisBoard extends JPanel {
	private static final long serialVersionUID = -2768315633419131803L;

	public TetrisBoard() throws IOException {
		JLabel pic = new JLabel(new ImageIcon(ImageIO.read(new File(
				"src/images/darkblue.png"))));
		this.add(pic);

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
					System.out.println("UP");
					// handle up
					break;
				case KeyEvent.VK_DOWN:
					System.out.println("DOWN");
					// handle down
					break;
				case KeyEvent.VK_LEFT:
					System.out.println("LEFT");
					// handle left
					break;
				case KeyEvent.VK_RIGHT:
					System.out.println("RIGHT");
					// handle right
					break;
				}
			}
		});
	}
}
