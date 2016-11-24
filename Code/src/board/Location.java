package board;

import java.io.Serializable;

import piece.Piece;

public class Location implements Serializable {

	private static final long serialVersionUID = 7120348557326791298L;
	private int row;
	private int column;
	private Piece piece;

	public Location(int r, int c) {
		row = r;
		column = c;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setRow(int i) {
		row = i;
	}

	public void setColumn(int i) {
		column = i;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece p) {
		piece = p;
	}
}
