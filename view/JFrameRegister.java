package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dao.UsersDAO;
import model.Users;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class JFrameRegister {

	protected Shell shlRegister;
	private Text username;
	private Text password;
	private Text cpassword;
	private UsersDAO usersdao = new UsersDAO();
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			JFrameRegister window = new JFrameRegister();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlRegister.open();
		shlRegister.layout();
		while (!shlRegister.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlRegister = new Shell();
		shlRegister.setSize(887, 575);
		shlRegister.setText("Register");
		
		username = new Text(shlRegister, SWT.BORDER);
		username.setBounds(332, 184, 193, 21);
		
		password = new Text(shlRegister, SWT.BORDER | SWT.PASSWORD);
		password.setBounds(332, 225, 193, 21);
		
		cpassword = new Text(shlRegister, SWT.BORDER | SWT.PASSWORD);
		cpassword.setBounds(332, 266, 193, 21);
		
		Label lblUsername = new Label(shlRegister, SWT.NONE);
		lblUsername.setBounds(265, 187, 55, 15);
		lblUsername.setText("Username");
		
		Label lblPassword = new Label(shlRegister, SWT.NONE);
		lblPassword.setBounds(265, 228, 55, 15);
		lblPassword.setText("Password");
		
		Label lblConfirmPassword = new Label(shlRegister, SWT.NONE);
		lblConfirmPassword.setBounds(223, 266, 97, 15);
		lblConfirmPassword.setText("Confirm Password");
		
		Button btnNewButton = new Button(shlRegister, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
							JFrameLogin login = new JFrameLogin();
							shlRegister.dispose();
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
		btnNewButton.setBounds(367, 313, 112, 25);
		btnNewButton.setText("Register");
		
		Button btnNewButton_1 = new Button(shlRegister, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrameLogin login = new JFrameLogin();
				shlRegister.dispose();
				login.main(null);
			}
		});
		btnNewButton_1.setBounds(71, 445, 90, 41);
		btnNewButton_1.setText("Go to Login");

	}
}
