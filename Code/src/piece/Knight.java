package piece;

public class Knight extends Piece {

	private static final long serialVersionUID = -119788415985384543L;
	private boolean moved;
	private int color;
	private boolean promoted;

	public Knight(int color) {

		this.color = color;
		moved = false;
		promoted = false;
	}

	@Override
	public boolean getMoved() {
		// TODO Auto-generated method stub
		return moved;
	}

	@Override
	public void setMoved(boolean moved) {
		// TODO Auto-generated method stub
		this.moved = moved;
	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public void setColor(int color) {
		// TODO Auto-generated method stub
		this.color = color;
	}

	@Override
	public boolean getPromoted() {
		// TODO Auto-generated method stub
		return promoted;
	}

	@Override
	public void setPromoted(boolean promoted) {
		// TODO Auto-generated method stub
		this.promoted = promoted;
	}

	public String getImage() {
		if (color == 0) {
			return "white_knight";
		} else {
			return "black_knight";
		}
	}

}
