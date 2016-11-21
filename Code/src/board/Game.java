package board;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import piece.*;
import util.*;
import communicator.*;

public class Game implements Serializable {

	private static final long serialVersionUID = 7725665560866651386L;
	private Board[] boards;
	private Holding[] holdings;
	private Communicator comm;
	private boolean ready = false;

	public Game() {

		boards = new Board[Constants.NUM_TEAMS];
		holdings = new Holding[Constants.NUM_PLAYERS];

		for (int i = 0; i < Constants.NUM_TEAMS; i++)
			boards[i] = new Board();
		for (int i = 0; i < Constants.NUM_PLAYERS; i++)
			holdings[i] = new Holding();

		if (comm instanceof Client) {
			System.out.println("Client?");
			initClient();
		} else
			System.out.println("I'm a server!");
	}

	public Game(Communicator comm) {
		boards = new Board[Constants.NUM_TEAMS];
		holdings = new Holding[Constants.NUM_PLAYERS];

		for (int i = 0; i < Constants.NUM_TEAMS; i++)
			boards[i] = new Board();
		for (int i = 0; i < Constants.NUM_PLAYERS; i++)
			holdings[i] = new Holding();
		this.comm = comm;
	}

	public void start() {
		if (comm instanceof Client) {
			System.out.println("I'm a client!");
			initClient();
		} else
			System.out.println("I'm a server!");
	}

	public void init() {
		for (Board b : boards) {
			b = new Board();
		}
		boards[0].setTurn(1);
		boards[1].setTurn(3);
		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			boards[0].placePiece(new Pawn(0), 6, i);
			boards[0].placePiece(new Pawn(1), 1, i);
		}
		boards[0].placePiece(new Rook(0), 7, 0);
		boards[0].placePiece(new Rook(1), 0, 0);
		boards[0].placePiece(new Rook(0), 7, 7);
		boards[0].placePiece(new Rook(1), 0, 7);

		boards[0].placePiece(new Knight(0), 7, 1);
		boards[0].placePiece(new Knight(1), 0, 1);
		boards[0].placePiece(new Knight(0), 7, 6);
		boards[0].placePiece(new Knight(1), 0, 6);

		boards[0].placePiece(new Bishop(0), 7, 2);
		boards[0].placePiece(new Bishop(1), 0, 2);
		boards[0].placePiece(new Bishop(0), 7, 5);
		boards[0].placePiece(new Bishop(1), 0, 5);

		boards[0].placePiece(new Queen(0), 7, 3);
		boards[0].placePiece(new King(1), 0, 3);
		boards[0].placePiece(new King(0), 7, 4);
		boards[0].placePiece(new Queen(1), 0, 4);
		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			boards[1].placePiece(new Pawn(1), 6, i);
			boards[1].placePiece(new Pawn(0), 1, i);
		}
		boards[1].placePiece(new Rook(1), 7, 0);
		boards[1].placePiece(new Rook(0), 0, 0);
		boards[1].placePiece(new Rook(1), 7, 7);
		boards[1].placePiece(new Rook(0), 0, 7);

		boards[1].placePiece(new Knight(1), 7, 1);
		boards[1].placePiece(new Knight(0), 0, 1);
		boards[1].placePiece(new Knight(1), 7, 6);
		boards[1].placePiece(new Knight(0), 0, 6);

		boards[1].placePiece(new Bishop(1), 7, 2);
		boards[1].placePiece(new Bishop(0), 0, 2);
		boards[1].placePiece(new Bishop(1), 7, 5);
		boards[1].placePiece(new Bishop(0), 0, 5);

		boards[1].placePiece(new Queen(1), 7, 4);
		boards[1].placePiece(new King(0), 0, 4);
		boards[1].placePiece(new King(1), 7, 3);
		boards[1].placePiece(new Queen(0), 0, 3);
		for (Holding h : holdings)
			h.init();
	}

	public void move(int b, int iRow, int iColumn, int fRow, int fColumn) {
		boards[b].placePiece(boards[b].removePiece(iRow, iColumn), fRow,
				fColumn);
	}

	public boolean isValidMove(int row, int column) {
		return true;
	}

	public void passToHolding(Piece p, Holding h) {
		h.addPiece(p);
	}

	public Board getBoard(int i) {
		return boards[i];
	}

	public Holding getHolding(int holding) {
		return holdings[holding];
	}

	// start of netcode
	public void ready() {
		ready = true;
	}

	public void setCommunicator(Communicator comm) {
		this.comm = comm;
	}

	public Communicator getCommunicator() {
		return comm;
	}

	public void resetConnections() {
		((Server) comm).reset();
		comm.sendObject(this);
	}

	private void initClient() {
		Object o;
		new Thread(new RunClient((Client) comm, this)).start();
	}

	public void initServer() {
		while (!ready) { // wait until server says ready
			try {
				TimeUnit.NANOSECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (comm instanceof Server) {
			((Server) comm).setGame(this);
			comm.sendObject("testing");
			((Server) comm).cleanUp();
		}

		int players = ((Server) comm).getNumClients() + 1;

		comm.sendObject(this);

		for (int i = 0; i < players; i++)
			new Thread(new RunServer((Server) comm, i, this)).start();
	}

}
