package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

import dao.AutoriDAO;
import dao.BibliotecaDAO;
import dao.CartiDAO;
import dao.RafturiDAO;
import model.Autori;
import model.Biblioteca;
import model.Carti;
import model.Rafturi;
import model.Users;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class JFrameRafturi {

	protected Shell shlRafturi;
	private Table tableRafturi;
	private Text codraft;
	private Combo raftid;
	private RafturiDAO rafturidao = new RafturiDAO();
	private CartiDAO cartidao = new CartiDAO();
	private BibliotecaDAO bibliotecadao = new BibliotecaDAO();
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(Users user) {
		try {
			JFrameRafturi window = new JFrameRafturi();
			window.open(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void FillData(Users user) {
		
			raftid.removeAll();
			raftid.setText("Select the id");
		tableRafturi.removeAll();
		for (Rafturi raft : rafturidao.selectAllRafturi()) {
			TableItem tableItem = new TableItem(tableRafturi, SWT.NONE);
			tableItem.setText(new String[] {String.valueOf(raft.getRaftid()), raft.getCarte().getDenumire(),String.valueOf(raft.getCarte().getAnaparitie()), raft.getCarte().getEditura(), raft.getBiblioteca().getDenumire(), raft.getBiblioteca().getAdresa(), raft.getCodraft()});
				raftid.add(String.valueOf(raft.getRaftid()));
		}
	}
	/**
	 * Open the window.
	 */
	public void open(Users user) {
		Display display = Display.getDefault();
		createContents(user);
		FillData(user);
		shlRafturi.open();
		shlRafturi.layout();
		while (!shlRafturi.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Users user) {
		shlRafturi = new Shell();
		shlRafturi.setSize(877, 567);
		shlRafturi.setText("Rafturi");
		
		tableRafturi = new Table(shlRafturi, SWT.BORDER | SWT.FULL_SELECTION);
		tableRafturi.setBounds(79, 33, 696, 226);
		tableRafturi.setHeaderVisible(true);
		tableRafturi.setLinesVisible(true);
		
		raftid = new Combo(shlRafturi, SWT.NONE);
		raftid.setBounds(334, 303, 156, 23);
		raftid.setText("Select the id");
		
		Combo carteid = new Combo(shlRafturi, SWT.NONE);
		carteid.setBounds(334, 332, 156, 15);
		carteid.setText("Select the id");
		
		
		for (Carti carte : cartidao.selectAllCarti()) {
			carteid.add(String.valueOf(carte.getCarteid())+", "+carte.getDenumire());
		}
		
		Combo bibliotecaid = new Combo(shlRafturi, SWT.NONE);
		bibliotecaid.setBounds(334, 360, 156, 23);
		bibliotecaid.setText("Select the id");
		
		
		for ( Biblioteca biblioteca : bibliotecadao.selectAllBiblioteca()) {
			bibliotecaid.add(String.valueOf(biblioteca.getBibliotecaid())+", "+biblioteca.getDenumire());
		}
		
		codraft = new Text(shlRafturi, SWT.BORDER);
		codraft.setBounds(334, 389, 156, 21);
		
		Button btnAdaugaAutor = new Button(shlRafturi, SWT.NONE);
		btnAdaugaAutor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Carti carte = cartidao.selectCarte(Integer.parseInt(carteid.getText().split(",")[0]));
				Biblioteca biblioteca = bibliotecadao.selectBiblioteca(Integer.parseInt(bibliotecaid.getText().split(",")[0]));
				Rafturi raft = new Rafturi(carte, biblioteca, codraft.getText());
				try {
					rafturidao.insertRaft(raft);
					JOptionPane.showMessageDialog(null, "Raft adaugat cu succes.");
					
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Adaugarea raftului a esuat.");
				}
			}
		});
		btnAdaugaAutor.setText("Adauga");
		btnAdaugaAutor.setBounds(334, 416, 48, 25);
		
		Button btnEditeazaAutor = new Button(shlRafturi, SWT.NONE);
		btnEditeazaAutor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Carti carte = cartidao.selectCarte(Integer.parseInt(carteid.getText().split(",")[0]));
				Biblioteca biblioteca = bibliotecadao.selectBiblioteca(Integer.parseInt(bibliotecaid.getText().split(",")[0]));
				Rafturi raft = new Rafturi(Integer.parseInt(raftid.getText()), carte, biblioteca, codraft.getText());
				try {
					rafturidao.updateRaft(raft);
					JOptionPane.showMessageDialog(null, "Raft editat cu succes.");
					
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Editarea raftului a esuat.");
				}
			}
		});
		btnEditeazaAutor.setText("Editeaza");
		btnEditeazaAutor.setBounds(388, 416, 48, 25);
		if ( user.isAdmin() ) {
		Button btnStergeAutor = new Button(shlRafturi, SWT.NONE);
		btnStergeAutor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int id = Integer.parseInt(raftid.getText());
				try {
					rafturidao.deleteRaft(id);
					JOptionPane.showMessageDialog(null, "Raft sters cu succes.");
					
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Stergerea raftului a esuat.");
				}
			}
		});
		btnStergeAutor.setText("Sterge");
		btnStergeAutor.setBounds(442, 416, 48, 25);
		}
		
		TableColumn tblclmnIdRaft = new TableColumn(tableRafturi, SWT.NONE);
		tblclmnIdRaft.setWidth(100);
		tblclmnIdRaft.setText("Id Raft");
		
		TableColumn tblclmnDenumireCarte = new TableColumn(tableRafturi, SWT.NONE);
		tblclmnDenumireCarte.setWidth(100);
		tblclmnDenumireCarte.setText("Denumire Carte");
		
		TableColumn tblclmnNewColumn = new TableColumn(tableRafturi, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Anul aparitiei");
		
		TableColumn tblclmnEditura = new TableColumn(tableRafturi, SWT.NONE);
		tblclmnEditura.setWidth(100);
		tblclmnEditura.setText("Editura");
		
		TableColumn tblclmnDenumire = new TableColumn(tableRafturi, SWT.NONE);
		tblclmnDenumire.setWidth(100);
		tblclmnDenumire.setText("Denumire");
		
		TableColumn tblclmnAdresa = new TableColumn(tableRafturi, SWT.NONE);
		tblclmnAdresa.setWidth(100);
		tblclmnAdresa.setText("Adresa");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(tableRafturi, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("Codul Raftului");
		
		
		
		
		
		Button btnNewButton = new Button(shlRafturi, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrame home = new JFrame();
				shlRafturi.dispose();
				home.main(user);
			}
		});
		btnNewButton.setText("Back Home");
		btnNewButton.setBounds(37, 464, 85, 41);
		
		
		
		Label lblIdRaft = new Label(shlRafturi, SWT.NONE);
		lblIdRaft.setBounds(279, 306, 34, 15);
		lblIdRaft.setText("Id Raft");
		
		Label lblIdCarte = new Label(shlRafturi, SWT.NONE);
		lblIdCarte.setBounds(272, 336, 41, 15);
		lblIdCarte.setText("Id Carte");
		
		Label lblIdBiblioteca = new Label(shlRafturi, SWT.NONE);
		lblIdBiblioteca.setBounds(251, 363, 65, 15);
		lblIdBiblioteca.setText("Id Biblioteca");
		
		Label lblCodRaft = new Label(shlRafturi, SWT.NONE);
		lblCodRaft.setBounds(272, 392, 52, 15);
		lblCodRaft.setText("Cod Raft");
		
		
		

	}
}
