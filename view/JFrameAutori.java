package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import dao.AutoriDAO;
import model.Autori;
import model.Users;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;

public class JFrameAutori {

	protected Shell shlAutori;
	private Table tableAutori;
	private Text numeautor;
	private Text prenumeautor;
	private Text taraorigine;
	private AutoriDAO autoridao = new AutoriDAO();
	private Combo autorid;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(Users user) {
		try {
			JFrameAutori window = new JFrameAutori();
			window.open(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void FillData(Users user) {
		
			autorid.removeAll();
		tableAutori.removeAll();
		for (Autori autor : autoridao.selectAllAutori()) {
			TableItem tableItem = new TableItem(tableAutori, SWT.NONE);
			tableItem.setText(new String[] {String.valueOf(autor.getAutorid()), autor.getNumeautor(), autor.getPrenumeautor(), autor.getTaraorigine()});
				autorid.add(String.valueOf(autor.getAutorid()));
		}
		
		
		
	}
	/**
	 * Open the window.
	 */
	public void open(Users user) {
		Display display = Display.getDefault();
		createContents(user);
		FillData(user);
		shlAutori.open();
		shlAutori.layout();
		while (!shlAutori.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Users user) {
		shlAutori = new Shell();
		shlAutori.setSize(580, 459);
		shlAutori.setText("Autori");
		
		tableAutori = new Table(shlAutori, SWT.BORDER | SWT.FULL_SELECTION);
		tableAutori.setBounds(26, 38, 507, 148);
		tableAutori.setHeaderVisible(true);
		tableAutori.setLinesVisible(true);
		
			autorid = new Combo(shlAutori, SWT.NONE);
			autorid.setBounds(217, 235, 156, 23);
			autorid.setText("Select the id");
		
		
		TableColumn tblclmnNewColumn = new TableColumn(tableAutori, SWT.NONE);
		tblclmnNewColumn.setWidth(107);
		tblclmnNewColumn.setText("Autorid");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(tableAutori, SWT.NONE);
		tblclmnNewColumn_1.setWidth(135);
		tblclmnNewColumn_1.setText("Nume Autor");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(tableAutori, SWT.NONE);
		tblclmnNewColumn_2.setWidth(137);
		tblclmnNewColumn_2.setText("Prenume Autor");
		
		TableColumn tblclmnTaraDeOrigine = new TableColumn(tableAutori, SWT.NONE);
		tblclmnTaraDeOrigine.setWidth(211);
		tblclmnTaraDeOrigine.setText("Tara de Origine");
		
		
		Button btnAdaugaAutor = new Button(shlAutori, SWT.NONE);
		btnAdaugaAutor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Autori autor = new Autori(numeautor.getText(), prenumeautor.getText(), taraorigine.getText());
				try {
					autoridao.insertAutor(autor);
					JOptionPane.showMessageDialog(null, "Autor adaugat cu succes.");
					
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Adaugarea autorului a esuat.");
				}
			}
		});
		btnAdaugaAutor.setBounds(217, 343, 48, 25);
		btnAdaugaAutor.setText("Adauga");
		
		numeautor = new Text(shlAutori, SWT.BORDER);
		numeautor.setBounds(217, 262, 156, 21);
		
		Label lblNumeAutor = new Label(shlAutori, SWT.NONE);
		lblNumeAutor.setText("Nume autor");
		lblNumeAutor.setBounds(136, 265, 64, 15);
		
		Label lblPrenumeAutor = new Label(shlAutori, SWT.NONE);
		lblPrenumeAutor.setText("Prenume autor");
		lblPrenumeAutor.setBounds(123, 292, 85, 15);
		
		prenumeautor = new Text(shlAutori, SWT.BORDER);
		prenumeautor.setBounds(217, 289, 156, 21);
		
		taraorigine = new Text(shlAutori, SWT.BORDER);
		taraorigine.setBounds(217, 316, 156, 21);
		
		Label lblTaraDeOrigine = new Label(shlAutori, SWT.NONE);
		lblTaraDeOrigine.setText("Tara de origine");
		lblTaraDeOrigine.setBounds(123, 313, 77, 15);
		
		Label lblIdAutor = new Label(shlAutori, SWT.NONE);
		lblIdAutor.setBounds(153, 238, 55, 15);
		lblIdAutor.setText("Id Autor");
		
		Button btnEditeazaAutor = new Button(shlAutori, SWT.NONE);
		btnEditeazaAutor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			
				Autori autor = new Autori(Integer.parseInt(autorid.getText()),numeautor.getText(), prenumeautor.getText(), taraorigine.getText());
				try {
					autoridao.updateAutor(autor);
					JOptionPane.showMessageDialog(null, "Autor editat cu succes.");
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Editarea autorului a esuat.");
				}
			}
		});
		btnEditeazaAutor.setBounds(271, 343, 48, 25);
		btnEditeazaAutor.setText("Editeaza");
		if ( user.isAdmin() ) {
		Button btnStergeAutor = new Button(shlAutori, SWT.NONE);
		btnStergeAutor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int id = Integer.parseInt(autorid.getText());
				try {
					autoridao.deleteAutor(id);
					JOptionPane.showMessageDialog(null, "Autor sters cu succes.");
					
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Stergerea autorului a esuat.");
				}
			}
		});
		btnStergeAutor.setText("Sterge");
		btnStergeAutor.setBounds(325, 343, 48, 25);
		}
		Button btnNewButton = new Button(shlAutori, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrame home = new JFrame();
				shlAutori.dispose();
				home.main(user);
			}
		});
		btnNewButton.setBounds(26, 369, 85, 41);
		btnNewButton.setText("Back Home");
		
		
		
		
		
		

	}
}
