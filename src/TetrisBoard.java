import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TetrisBoard extends JPanel {
	private static final long serialVersionUID = -2768315633419131803L;

	public TetrisBoard() throws IOException {
		JLabel pic = new JLabel(new ImageIcon(ImageIO.read(new File("block_images/yellow.png"))));
		
		this.add(pic);
	}
}
