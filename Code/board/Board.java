package board;

import java.io.Serializable;

import piece.Piece;
import util.Constants;

public class Board implements Serializable {
	private static final long serialVersionUID = 824461096453998426L;
	private Loc[][] loc;
	private int turn;

	public Board() {
		loc = new Loc[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
		for (int r = 0; r < Constants.BOARD_SIZE; r++) {
			for (int c = 0; c < Constants.BOARD_SIZE; c++) {
				loc[r][c] = new Loc(r, c);
			}
		}
	}

	public void init() {

	}

	public Piece getPiece(int row, int column) {
		return loc[row][column].getPiece();
	}

	public void placePiece(Piece p, int row, int column) {
		loc[row][column].setPiece(p);
	}

	public Piece removePiece(int row, int column) {
		Piece ret = loc[row][column].getPiece();
		loc[row][column].setPiece(null);
		return ret;
	}

	public Loc getLoc(int row, int column) {
		return loc[row][column];
	}

	public void setLoc(Loc l, int row, int column) {
		loc[row][column] = l;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int i) {
		turn = i;
	}
}
