package piece;

import java.io.Serializable;

public abstract class Piece implements Serializable {

	private static final long serialVersionUID = -2864418564345625663L;

	public abstract boolean getMoved();

	public abstract void setMoved(boolean moved);

	public abstract int getColor();

	public abstract void setColor(int color);

	public abstract boolean getPromoted();

	public abstract void setPromoted(boolean promoted);

	public abstract String getImage();
}