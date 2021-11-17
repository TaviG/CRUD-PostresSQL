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

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class JFrameLogin {

	protected Shell shlLogin;
	private Text username;
	private Text password;
	private UsersDAO usersdao = new UsersDAO();

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			JFrameLogin window = new JFrameLogin();
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
		shlLogin.open();
		shlLogin.layout();
		while (!shlLogin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlLogin = new Shell();
		shlLogin.setSize(891, 578);
		shlLogin.setText("Login");
		
		username = new Text(shlLogin, SWT.BORDER);
		username.setBounds(340, 193, 205, 21);
		
		password = new Text(shlLogin, SWT.BORDER | SWT.PASSWORD);
		password.setBounds(340, 253, 205, 21);
		
		Label lblUsername = new Label(shlLogin, SWT.NONE);
		lblUsername.setBounds(279, 196, 55, 15);
		lblUsername.setText("Username");
		
		Label lblPassword = new Label(shlLogin, SWT.NONE);
		lblPassword.setBounds(279, 256, 55, 15);
		lblPassword.setText("Password");
		
		Button btnLogin = new Button(shlLogin, SWT.NONE);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
			            	JFrame home = new JFrame();
			            	shlLogin.dispose();
			            	home.main(user);
			            } else {
			            	JOptionPane.showMessageDialog(null, "Parolele nu coincid.");
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
		btnLogin.setBounds(350, 304, 75, 25);
		btnLogin.setText("Login");
		
		Button btnRegister = new Button(shlLogin, SWT.NONE);
		btnRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrameRegister register  = new JFrameRegister();
				shlLogin.dispose();
				register.main(null);
			}
		});
		btnRegister.setBounds(447, 304, 75, 25);
		btnRegister.setText("Register");

	}
}
