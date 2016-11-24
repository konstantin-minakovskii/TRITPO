package menus;

import java.awt.event.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import board.Game;
import util.Constants;
import communicator.Client;
import graphics.GUI;

public class ClientUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JRadioButton rdbtnSelectAServer, rdbtnManualConnection;
	private JList list;
	private DefaultListModel listModel;
	
	/** 
	 * Creating client window
	 */
	public ClientUI() {
		setTitle("Bughouse client");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 500);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(117, 436, 60, 25);
		contentPane.add(btnExit);

		rdbtnSelectAServer = new JRadioButton("Select a server from the list:");
		rdbtnSelectAServer.setBounds(66, 25, 161, 25);
		contentPane.add(rdbtnSelectAServer);

		rdbtnManualConnection = new JRadioButton("Manual Connection:");
		rdbtnManualConnection.setBounds(87, 325, 120, 25);
		contentPane.add(rdbtnManualConnection);

		buttonGroup();

		textField = new JTextField();
		textField.setBounds(47, 355, 200, 25);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ipAddress = null;
				if (rdbtnManualConnection.isSelected()) {
					ipAddress = textField.getText();
				} else if (rdbtnSelectAServer.isSelected()) {
					ipAddress = (String) (listModel.getElementAt(list.getLeadSelectionIndex()));
				}
				System.out.println("starting game");
				Game game = new Game(new Client(ipAddress));
				game.init();
				game.start();
				new GUI(game);
				System.out.println("game started");
				setVisible(false);
			}
		});
		btnConnect.setBounds(47, 400, 200, 25);
		contentPane.add(btnConnect);

		JButton btnScanForGames = new JButton("Scan for games");
		btnScanForGames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listModel.clear();
				scanForGames(100);
			}
		});
		btnScanForGames.setBounds(47, 290, 200, 25);
		contentPane.add(btnScanForGames);

		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(47, 50, 200, 225);
		contentPane.add(list);
	}

	private void buttonGroup() {
		ButtonGroup serverSelect = new ButtonGroup();
		serverSelect.add(rdbtnManualConnection);
		serverSelect.add(rdbtnSelectAServer);
	}

	private static boolean portIsOpen(String ip, int port, int timeout) {
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, port), timeout);
			socket.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	private void scanForGames(int timeout) {
		try {
			String ipAddress = InetAddress.getLocalHost().getHostAddress();
			String beginningIp = ipAddress.substring(0, ipAddress.lastIndexOf(".") + 1);
			for (int i = 0; i < 255; i++) {
				if (i % 50 == 0) {
					System.out.println(beginningIp + i);
				}
				if (portIsOpen(beginningIp + i, Constants.BROADCAST_PORT, timeout)) {
					listModel.addElement("" + beginningIp + i);
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
