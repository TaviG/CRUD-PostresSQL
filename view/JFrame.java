package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import dao.UsersDAO;
import model.Users;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class JFrame {

	protected Shell shlHome;
	private UsersDAO usersdao = new UsersDAO();
	/**
	 * Launch the application.
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public static void main(Users user) {
		try {
			
			JFrame window = new JFrame();
			window.open(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open(Users user) {
		
		Display display = Display.getDefault();
		createContents(user);
		shlHome.open();
		shlHome.layout();
		while (!shlHome.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Users user) {
		shlHome = new Shell();
		shlHome.setSize(875, 555);
		shlHome.setText("Home");
		
		Button btnNewButton = new Button(shlHome, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrameAutori autori = new JFrameAutori();
				shlHome.dispose();
				autori.main(user);
			}
		});
		btnNewButton.setBounds(32, 218, 90, 42);
		btnNewButton.setText("Autori");
		
		Button btnNewButton_1 = new Button(shlHome, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrameCarti carti = new JFrameCarti();
				shlHome.dispose();
				carti.main(user);
			}
		});
		btnNewButton_1.setBounds(200, 218, 90, 42);
		btnNewButton_1.setText("Carti");
		
		Button btnNewButton_2 = new Button(shlHome, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrameBiblioteca biblioteca = new JFrameBiblioteca();
				shlHome.dispose();
				biblioteca.main(user);
			}
		});
		btnNewButton_2.setBounds(377, 218, 90, 42);
		btnNewButton_2.setText("Biblioteca");
	
		Button btnNewButton_3 = new Button(shlHome, SWT.NONE);
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrameRafturi rafturi = new JFrameRafturi();
				shlHome.dispose();
				rafturi.main(user);
			}
		});
		btnNewButton_3.setBounds(577, 218, 90, 42);
		btnNewButton_3.setText("Rafturi");
		
		Button btnNewButton_4 = new Button(shlHome, SWT.NONE);
		btnNewButton_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrameVolume volume = new JFrameVolume();
				shlHome.dispose();
				volume.main(user);
			}
		});
		btnNewButton_4.setBounds(755, 218, 83, 42);
		btnNewButton_4.setText("Volume");
		
		Button btnNewButton_5 = new Button(shlHome, SWT.NONE);
		btnNewButton_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrameLogin login = new JFrameLogin();
				shlHome.dispose();
				login.main(null);
			}
		});
		btnNewButton_5.setBounds(346, 375, 142, 42);
		btnNewButton_5.setText("Log out");

	}
}
