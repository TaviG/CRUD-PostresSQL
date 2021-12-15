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
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterFrame {

	private JFrame frame;
	private JTextField username;
	private JPasswordField password;
	private JPasswordField cpassword;
	private UsersDAO usersdao = new UsersDAO();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterFrame window = new RegisterFrame();
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
	public RegisterFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1081, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		username = new JTextField();
		username.setBounds(441, 183, 138, 20);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(441, 235, 138, 20);
		frame.getContentPane().add(password);
		
		cpassword = new JPasswordField();
		cpassword.setBounds(441, 285, 138, 20);
		frame.getContentPane().add(cpassword);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(302, 186, 63, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(302, 238, 75, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Confirm Password");
		lblNewLabel_2.setBounds(302, 288, 129, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nickname = username.getText();
				String pass = password.getText();
				String cpass = cpassword.getText();
				Boolean admin = false;
				if ( !pass.equals(cpass) ) {
					JOptionPane.showMessageDialog(null, "Parolele trebuie sa coincida."+pass+" "+cpass);
				} else {
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
						Users user = new Users(nickname, hashtext, admin);
						Users userverify = usersdao.selectUser(nickname);
						if ( userverify == null) {
							usersdao.insertUser(user);
							JOptionPane.showMessageDialog(null, "Inregistrarea s a realizat cu succes." );
							LoginFrame login = new LoginFrame();
							frame.dispose();
							login.main(null);
						} else {
							JOptionPane.showMessageDialog(null, "Username-ul acesta deja exista.");
						}
						
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Eroare.");
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Eroare.");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Eroare.");
					}
				
				}
			}
		});
		btnNewButton.setBounds(429, 338, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Login Page");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				LoginFrame login = new LoginFrame();
				login.main(null);
			}
		});
		btnNewButton_1.setBounds(52, 434, 107, 52);
		frame.getContentPane().add(btnNewButton_1);
	}

}
