package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

import dao.BibliotecaDAO;
import model.Autori;
import model.Biblioteca;
import model.Users;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class JFrameBiblioteca {

	protected Shell shlBiblioteca;
	private Table table;
	private Text denumire;
	private Text adresa;
	private BibliotecaDAO bibliotecadao = new BibliotecaDAO();
	private Combo bibliotecaid;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(Users user) {
		try {
			JFrameBiblioteca window = new JFrameBiblioteca();
			window.open(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void FillData(Users user) {
		
			bibliotecaid.removeAll();
		table.removeAll();
		for (Biblioteca biblioteca : bibliotecadao.selectAllBiblioteca()) {
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(new String[] {String.valueOf(biblioteca.getBibliotecaid()), biblioteca.getDenumire(), biblioteca.getAdresa()});
				bibliotecaid.add(String.valueOf(biblioteca.getBibliotecaid()));
		}
	}
	
	/**
	 * Open the window.
	 */
	public void open(Users user) {
		Display display = Display.getDefault();
		createContents(user);
		FillData(user);
		shlBiblioteca.open();
		shlBiblioteca.layout();
		while (!shlBiblioteca.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Users user) {
		shlBiblioteca = new Shell();
		shlBiblioteca.setSize(875, 568);
		shlBiblioteca.setText("Biblioteca");
		
		Button btnNewButton = new Button(shlBiblioteca, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrame home = new JFrame();
				shlBiblioteca.dispose();
				home.main(user);
			}
		});
		
		
		
		btnNewButton.setText("Back Home");
		btnNewButton.setBounds(53, 456, 85, 41);
		
		table = new Table(shlBiblioteca, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(133, 38, 569, 148);
	
			bibliotecaid = new Combo(shlBiblioteca, SWT.NONE);
			bibliotecaid.setBounds(341, 260, 156, 23);
			bibliotecaid.setText("Select the id");
		
		TableColumn tblclmnBibliotecaid = new TableColumn(table, SWT.NONE);
		tblclmnBibliotecaid.setWidth(177);
		tblclmnBibliotecaid.setText("Bibliotecaid");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(205);
		tblclmnNewColumn_1.setText("Denumire");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(262);
		tblclmnNewColumn_2.setText("Adresa");
		
		
	
		denumire = new Text(shlBiblioteca, SWT.BORDER);
		denumire.setBounds(341, 289, 156, 21);
		
		adresa = new Text(shlBiblioteca, SWT.BORDER);
		adresa.setBounds(341, 316, 156, 21);
		
		Button btnAdaugaBiblioteca = new Button(shlBiblioteca, SWT.NONE);
		btnAdaugaBiblioteca.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Biblioteca biblioteca = new Biblioteca(denumire.getText(), adresa.getText());
				try {
					bibliotecadao.insertBiblioteca(biblioteca);
					JOptionPane.showMessageDialog(null, "Biblioteca adaugata cu succes.");
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Adaugarea bibliotecii a esuat.");
				}
			}
		});
		btnAdaugaBiblioteca.setText("Adauga");
		btnAdaugaBiblioteca.setBounds(341, 343, 48, 25);
		
		Button btnEditeazaBiblioteca = new Button(shlBiblioteca, SWT.NONE);
		btnEditeazaBiblioteca.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Biblioteca biblioteca = new Biblioteca(Integer.parseInt(bibliotecaid.getText()),denumire.getText(), adresa.getText());
				try {
					bibliotecadao.updateBiblioteca(biblioteca);
					JOptionPane.showMessageDialog(null, "Biblioteca editata cu succes.");
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Editarea bibliotecii a esuat.");
				}
			}
		});
		btnEditeazaBiblioteca.setText("Editeaza");
		btnEditeazaBiblioteca.setBounds(395, 343, 48, 25);
		if ( user.isAdmin() ) {
		Button btnStergeBiblioteca = new Button(shlBiblioteca, SWT.NONE);
		btnStergeBiblioteca.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int id = Integer.parseInt(bibliotecaid.getText());
				try {
					bibliotecadao.deleteBiblioteca(id);
					JOptionPane.showMessageDialog(null, "Biblioteca stearsa cu succes.");
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Stergerea bibliotecii a esuat.");
				}
			}
		});
		btnStergeBiblioteca.setText("Sterge");
		btnStergeBiblioteca.setBounds(449, 343, 48, 25);
		}
		Label lblNewLabel = new Label(shlBiblioteca, SWT.NONE);
		lblNewLabel.setBounds(258, 263, 77, 15);
		lblNewLabel.setText("Id Biblioteca");
		
		Label lblNewLabel_1 = new Label(shlBiblioteca, SWT.NONE);
		lblNewLabel_1.setBounds(268, 289, 55, 15);
		lblNewLabel_1.setText("Denumire");
		
		Label lblNewLabel_2 = new Label(shlBiblioteca, SWT.NONE);
		lblNewLabel_2.setBounds(287, 319, 36, 15);
		lblNewLabel_2.setText("Adresa");

	}
	

}
