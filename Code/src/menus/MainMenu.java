package menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.border.*;

import util.Constants;

public class MainMenu extends JFrame {

	public static JTextField nameTextField;
	
	/**
	* Creating main menu window
	*/
	public MainMenu() {
		setTitle("Bughouse chess");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 500);
		setLocationRelativeTo(null);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUName = new JLabel("Username:");
		lblUName.setBounds(121, 45, 52, 14);
		nameTextField = new JTextField();
		nameTextField.setBounds(104, 67, 86, 20);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);

		JButton btnStartServer = new JButton("Start server.");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (nameTextField.getText().length() < 1) {
					JOptionPane.showMessageDialog(getParent(), "Enter name");
				} else {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								Constants.name = nameTextField.getText();
								ServerUI frame = new ServerUI();
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					setVisible(false);
				}
			}
		});
		btnStartServer.setBounds(47, 364, 200, 25);
		contentPane.add(btnStartServer);

		JButton btnStartClient = new JButton("Start client.");
		btnStartClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (nameTextField.getText().length() < 1) {
					JOptionPane.showMessageDialog(getParent(), "Enter name");
				} else {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								Constants.name = nameTextField.getText();
								ClientUI frame = new ClientUI();
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					setVisible(false);
				}
			}
		});
		btnStartClient.setBounds(47, 400, 200, 25);
		contentPane.add(btnStartClient);

		JButton btnOptions = new JButton("Options");
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getParent(), "No options :)");
				setVisible(false);
			}
		});
		btnOptions.setBounds(37, 436, 100, 25);
		contentPane.add(btnOptions);

		JButton btnQuitGame = new JButton("Quit Game");
		btnQuitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnQuitGame.setBounds(157, 436, 100, 25);
		contentPane.add(btnQuitGame);

		setVisible(true);
	}
}
