package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import dao.UsersDAO;
import model.Users;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class LoginFrame {

	private JFrame frame;
	private JTextField username;
	private JPasswordField password;
	private UsersDAO usersdao = new UsersDAO();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame window = new LoginFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1086, 582);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		username = new JTextField();
		username.setBounds(449, 209, 137, 20);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(449, 258, 137, 20);
		frame.getContentPane().add(password);
		
		JLabel lblNewLabel = new JLabel("User");
		lblNewLabel.setBounds(371, 212, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(371, 261, 68, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nickname = username.getText();
				String pass = password.getText();
				Users user = usersdao.selectUser(nickname);
				if ( user.getUsername().equals(nickname) ) {
					byte[] bytesOfMessage;
					try {
						bytesOfMessage = pass.getBytes("UTF-8");
						MessageDigest md = MessageDigest.getInstance("MD5");
						byte[] theMD5digest = md.digest(bytesOfMessage);
						BigInteger no = new BigInteger(1, theMD5digest);
						String hashtext = no.toString(16);
			            while (hashtext.length() < 32) {
			                hashtext = "0" + hashtext;
			            }
			            if ( user.getPassword().equals(hashtext) ) {
			            	JOptionPane.showMessageDialog(null, "Autentificare cu succes.");
			            	HomeFrame home = new HomeFrame(user);
			            	frame.dispose();
			            	home.main(user);
			            } else {
			            	JOptionPane.showMessageDialog(null, "Parola este incorecta.");
			            }
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Eroare.");
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Eroare.");
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Acest username nu exista.");
				}
			}
		});
		btnNewButton.setBounds(375, 310, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Register");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				RegisterFrame register = new RegisterFrame();
				register.main(null);
			}
		});
		btnNewButton_1.setBounds(497, 310, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
	}
}
