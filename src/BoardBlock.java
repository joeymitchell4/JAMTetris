import java.awt.Image;

public class BoardBlock {

	private boolean isInUse;
	private Image tetrisBlock;

	public BoardBlock() {
		
	}

	public boolean isInUse() {
		return isInUse;
	}

	public Image getTetrisBlock() {
		return tetrisBlock;
	}

	public void setInUse(boolean isInUse) {
		this.isInUse = isInUse;
	}

	public void setTetrisBlock(Image tetrisBlock) {
		this.tetrisBlock = tetrisBlock;
	}

}