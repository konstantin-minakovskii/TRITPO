package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class GUI extends TimerTask {
	public JFrame frame;
	private Game game;
	private int state;
	private int savedRow, savedColumn;
	private Piece savedPiece;
	private JButton[][] pieces = new JButton[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
	private JButton[][] pieces2 = new JButton[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
	private JButton[][] hold1 = new JButton[Constants.BOARD_SIZE][4];
	private JButton[][] hold2 = new JButton[Constants.BOARD_SIZE][4];
	private JButton[][] hold3 = new JButton[Constants.BOARD_SIZE][4];
	private JButton[][] hold4 = new JButton[Constants.BOARD_SIZE][4];

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		game = new Game();
		game.init();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(this, 0, 100);
	}

	public GUI(Game game) {
		initialize();
		this.game = game;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(this, 0, 100);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("HackUMass - Bughouse Chess");
		frame.getContentPane().setBackground(new Color(128, 0, 0));
		frame.setResizable(false);
		frame.setBounds(0, 0, 1105, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblPlayer = new JLabel("Player 4");
		lblPlayer.setForeground(new Color(245, 255, 250));
		lblPlayer.setFont(new Font("AR JULIAN", Font.BOLD, 24));
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer.setBounds(20, 194, 400, 50);
		frame.getContentPane().add(lblPlayer);

		Panel panel = new Panel();
		panel.setLayout(new GridLayout(8, 8));
		panel.setBounds(20, 250, 400, 400);
		frame.getContentPane().add(panel);

		JPanel space = new JPanel();
		space.setBackground(Color.PINK);
		space.setLayout(new GridLayout(2, 2));
		space.setBounds(450, 250, 200, 400);
		frame.getContentPane().add(space);

		JPanel holding1 = new JPanel();
		holding1.setBorder(new LineBorder(new Color(0, 0, 0)));
		holding1.setBackground(new Color(128, 128, 128));
		holding1.setLayout(new GridLayout(8, 4));
		space.add(holding1);

		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			for (int j = 0; j < 4; j++) {
				hold1[i][j] = new JButton();
				holding1.add(hold1[i][j]);
				hold1[i][j].setBackground(new Color(230, 230, 250));
				hold1[i][j]
						.addActionListener(new HoldingButtonListener(3, i, j));
			}
		}

		JPanel holding2 = new JPanel();
		holding2.setBorder(new LineBorder(new Color(0, 0, 0)));
		holding2.setBackground(new Color(128, 128, 128));
		holding2.setLayout(new GridLayout(8, 4));
		space.add(holding2);

		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			for (int j = 0; j < 4; j++) {
				hold2[i][j] = new JButton();
				holding2.add(hold2[i][j]);
				hold2[i][j].setBackground(new Color(250, 250, 210));
				hold2[i][j]
						.addActionListener(new HoldingButtonListener(2, i, j));
			}
		}

		JPanel holding3 = new JPanel();
		holding3.setBorder(new LineBorder(new Color(0, 0, 0)));
		holding3.setBackground(new Color(128, 128, 128));
		holding3.setLayout(new GridLayout(8, 4));
		space.add(holding3);
		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			for (int j = 0; j < 4; j++) {
				hold3[i][j] = new JButton();
				holding3.add(hold3[i][j]);
				hold3[i][j].setBackground(new Color(240, 255, 240));
				hold3[i][j]
						.addActionListener(new HoldingButtonListener(0, i, j));
			}
		}

		JPanel holding4 = new JPanel();
		holding4.setBorder(new LineBorder(new Color(0, 0, 0)));
		holding4.setBackground(new Color(128, 128, 128));
		holding4.setLayout(new GridLayout(8, 4));
		space.add(holding4);

		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			for (int j = 0; j < 4; j++) {
				hold4[i][j] = new JButton();
				holding4.add(hold4[i][j]);
				hold4[i][j].setBackground(new Color(255, 240, 245));
				hold4[i][j]
						.addActionListener(new HoldingButtonListener(1, i, j));
			}
		}

		Panel panel_1 = new Panel();
		panel_1.setLayout(new GridLayout(8, 8));
		panel_1.setBounds(680, 250, 400, 400);
		frame.getContentPane().add(panel_1);

		JLabel label = new JLabel("Player 2");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(245, 255, 250));
		label.setFont(new Font("AR JULIAN", Font.BOLD, 24));
		label.setBounds(680, 656, 400, 50);
		frame.getContentPane().add(label);

		JLabel lblNewLabel_1 = new JLabel("Player 1");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(245, 255, 250));
		lblNewLabel_1.setFont(new Font("AR JULIAN", Font.BOLD, 24));
		lblNewLabel_1.setBounds(20, 656, 400, 50);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("Player 3");
		lblNewLabel.setFont(new Font("AR JULIAN", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(245, 255, 250));
		lblNewLabel.setBounds(680, 194, 400, 50);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblBughouseChess = new JLabel("Bughouse Chess");
		lblBughouseChess.setHorizontalAlignment(SwingConstants.CENTER);
		lblBughouseChess.setForeground(new Color(245, 255, 250));
		lblBughouseChess.setFont(new Font("Algerian", Font.BOLD, 60));
		lblBughouseChess.setBounds(178, 75, 742, 93);
		frame.getContentPane().add(lblBughouseChess);
		frame.setVisible(true);

		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			for (int j = 0; j < Constants.BOARD_SIZE; j++) {
				pieces[i][j] = new JButton();
				panel.add(pieces[i][j]);
				pieces[i][j].addActionListener(new ButtonListener(0, i, j));
				if ((i + j) % 2 == 0) {
					pieces[i][j].setBackground(new Color(222, 184, 135));
				} else
					pieces[i][j].setBackground(Color.white);
			}
		}

		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			for (int j = 0; j < Constants.BOARD_SIZE; j++) {
				pieces2[i][j] = new JButton();
				panel_1.add(pieces2[i][j]);
				pieces2[i][j].addActionListener(new ButtonListener(1, i, j));
				if ((i + j) % 2 == 0) {
					pieces2[i][j].setBackground(Color.pink);
				} else
					pieces2[i][j].setBackground(Color.white);
			}
		}

	}

	public void update() {
		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			for (int j = 0; j < Constants.BOARD_SIZE; j++) {
				if (game.getBoard(0).getPiece(i, j) == null) {
					pieces[i][j].setIcon(new ImageIcon(getClass().getResource(
							"images/null.png")));
				} else {
					pieces[i][j].setIcon(new ImageIcon(getClass().getResource(
							"images/"
									+ game.getBoard(0).getPiece(i, j)
											.getImage() + ".png")));
				}
				if (game.getBoard(1).getPiece(i, j) == null) {
					pieces2[i][j].setIcon(new ImageIcon(getClass().getResource(
							"images/null.png")));
				} else {
					pieces2[i][j].setIcon(new ImageIcon(getClass().getResource(
							"images/"
									+ game.getBoard(1).getPiece(i, j)
											.getImage() + ".png")));
				}
			}
		}
		int i = 0;
		while (i < game.getHolding(3).getPieces().size()) {
			hold1[i / 4][i % 4].setIcon(new ImageIcon(getClass().getResource(
					"images/"
							+ game.getHolding(3).getPieces().get(i).getImage()
							+ "_sm.png")));
			i++;
		}
		i = 0;
		while (i < game.getHolding(2).getPieces().size()) {
			hold2[i / 4][i % 4].setIcon(new ImageIcon(getClass().getResource(
					"images/"
							+ game.getHolding(2).getPieces().get(i).getImage()
							+ "_sm.png")));
			i++;
		}
		i = 0;
		while (i < game.getHolding(0).getPieces().size()) {
			hold3[i / 4][i % 4].setIcon(new ImageIcon(getClass().getResource(
					"images/"
							+ game.getHolding(0).getPieces().get(i).getImage()
							+ "_sm.png")));
			i++;
		}
		i = 0;
		while (i < game.getHolding(1).getPieces().size()) {
			hold4[i / 4][i % 4].setIcon(new ImageIcon(getClass().getResource(
					"images/"
							+ game.getHolding(1).getPieces().get(i).getImage()
							+ "_sm.png")));
			i++;
		}
	}

	private class ButtonListener implements ActionListener {
		private int row;
		private int column;
		private int board;

		public ButtonListener(int b, int r, int c) {
			row = r;
			column = c;
			board = b;
		}

		public void actionPerformed(ActionEvent e) {
			if (state == 0) {
				if (game.getBoard(board).getPiece(row, column) != null) {
					savedRow = row;
					savedColumn = column;
					state = 1;
				}
			} else if (state == 1) {
				if (game.getBoard(board).getPiece(row, column) == null) {
					// move
					game.move(board, savedRow, savedColumn, row, column);
					state = 0;
				} else {
					// captured
					int h = 4; // it should change or something went wrong.
					Piece captured = game.getBoard(board).getPiece(row, column);
					if (board == 0 && captured.getColor() == 0)
						h = 2;
					else if (board == 0 && captured.getColor() == 1)
						h = 1;
					else if (board == 1 && captured.getColor() == 0)
						h = 0;
					else if (board == 1 && captured.getColor() == 1)
						h = 3;
					game.passToHolding(captured, game.getHolding(h));
					game.getBoard(board).removePiece(row, column);
					game.move(board, savedRow, savedColumn, row, column);
					state = 0;

				}
			} else if (state == 2) {
				game.getBoard(board).placePiece(savedPiece, row, column);
				state = 0;
			}
		}
	}

	private class HoldingButtonListener implements ActionListener {
		private int holding, i, j;

		public HoldingButtonListener(int holding, int i, int j) {
			this.holding = holding;
			this.i = i;
			this.j = j;
		}

		public void actionPerformed(ActionEvent e) {
			savedPiece = game.getHolding(holding).getPieces().get(i * 4 + j);
			game.getHolding(holding).removePiece(savedPiece);
			hold1[game.getHolding(3).getPieces().size() / 4][game.getHolding(3)
					.getPieces().size() % 4].setIcon(null);
			hold2[game.getHolding(2).getPieces().size() / 4][game.getHolding(2)
					.getPieces().size() % 4].setIcon(null);
			hold3[game.getHolding(0).getPieces().size() / 4][game.getHolding(0)
					.getPieces().size() % 4].setIcon(null);
			hold4[game.getHolding(1).getPieces().size() / 4][game.getHolding(1)
					.getPieces().size() % 4].setIcon(null);
			state = 2;
		}
	}

	@Override
	public void run() {
		update();
	}
}
