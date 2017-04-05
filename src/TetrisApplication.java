import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;

public class TetrisApplication extends JFrame {
	private static final long serialVersionUID = -4951055018923259413L;

	public TetrisApplication() {
		initUI();
	}

	private void initUI() {
		try {
			add(new TetrisBoard());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setSize(700, 690);
		setResizable(false); // CANNOT RESIZE GAME
		
		setTitle("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				TetrisApplication tetrisApp = new TetrisApplication();
				tetrisApp.setVisible(true);
			}
		});
	}
}
