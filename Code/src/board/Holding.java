package board;

import piece.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Holding implements Serializable {

	private static final long serialVersionUID = -7699400901320471619L;
	private List<Piece> pieces;

	public Holding() {
		pieces = new ArrayList<Piece>();
	}

	public void addPiece(Piece piece) {
		pieces.add(piece);
	}

	public Piece removePiece(Piece piece) {
		if (pieces.indexOf(piece) < 0)
			return null;
		Piece ret = pieces.get(pieces.indexOf(piece));
		pieces.remove(piece);
		return ret;
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}

	public void init() {
		pieces = new ArrayList<Piece>();
	}
}
