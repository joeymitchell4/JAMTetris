import java.awt.EventQueue;

import javax.swing.JFrame;

public class TetrisApplication extends JFrame {
	private static final long serialVersionUID = -4951055018923259413L;

	public TetrisApplication() {
		initUI();
	}

	private void initUI() {
		add(new TetrisBoard());

		setSize(700, 500);
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
