package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

import dao.AutoriDAO;
import dao.CartiDAO;
import model.Autori;
import model.Carti;
import model.Users;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class JFrameCarti {

	protected Shell shlCarti;
	private Table table;
	private Text denumire;
	private Text editura;
	private CartiDAO cartidao = new CartiDAO();
	private Combo carteid;
	/**
	 * Launch the application.
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public static void main(Users user) {
		try {
			JFrameCarti window = new JFrameCarti();
			window.open(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void FillData(Users user) {
			carteid.removeAll();
		table.removeAll();
		for (Carti carte : cartidao.selectAllCarti()) {
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(new String[] {String.valueOf(carte.getCarteid()), carte.getDenumire(), String.valueOf(carte.getAnaparitie()), carte.getEditura()});
				carteid.add(String.valueOf(carte.getCarteid()));
		}
	}
	
	/**
	 * Open the window.
	 */
	public void open(Users user) {
		Display display = Display.getDefault();
		createContents(user);
		FillData(user);
		shlCarti.open();
		shlCarti.layout();
		while (!shlCarti.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Users user) {
		shlCarti = new Shell();
		shlCarti.setSize(851, 575);
		shlCarti.setText("Carti");
		
		table = new Table(shlCarti, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(148, 28, 508, 192);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
	
		
			carteid = new Combo(shlCarti, SWT.NONE);
			carteid.setBounds(308, 256, 156, 23);
			carteid.setText("Select the id");
		
		

		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(113);
		tblclmnNewColumn.setText("Id Carte");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(127);
		tblclmnNewColumn_1.setText("Denumire");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(146);
		tblclmnNewColumn_2.setText("Anul de aparitie");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(204);
		tblclmnNewColumn_3.setText("Editura");
		
		
			DateTime anaparitie = new DateTime(shlCarti, SWT.BORDER);
			anaparitie.setBounds(308, 307, 156, 24);
			
			denumire = new Text(shlCarti, SWT.BORDER);
			denumire.setBounds(308, 283, 156, 21);
		
			editura = new Text(shlCarti, SWT.BORDER);
			editura.setBounds(308, 337, 156, 21);
		
		Button btnAdaugaCarte = new Button(shlCarti, SWT.NONE);
		btnAdaugaCarte.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Carti carte = new Carti(denumire.getText(), new Date(anaparitie.getYear()-1900, anaparitie.getMonth(), anaparitie.getDay()),editura.getText() );
				try {
					cartidao.insertCarte(carte);
					JOptionPane.showMessageDialog(null, "Carte adaugata cu succes.");
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Adaugarea cartii a esuat.");
				}
			}
		});
		btnAdaugaCarte.setText("Adauga");
		btnAdaugaCarte.setBounds(308, 364, 48, 25);
		
		Button btnEditeazaCarte = new Button(shlCarti, SWT.NONE);
		btnEditeazaCarte.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Carti carte = new Carti(Integer.parseInt(carteid.getText()), denumire.getText(), new Date(anaparitie.getYear()-1900, anaparitie.getMonth(), anaparitie.getDay()),editura.getText() );
				try {
					cartidao.updateCarte(carte);
					JOptionPane.showMessageDialog(null, "Carte editata cu succes.");
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Editarea cartii a esuat.");
				}
			}
		});
		btnEditeazaCarte.setText("Editeaza");
		btnEditeazaCarte.setBounds(362, 364, 48, 25);
		if ( user.isAdmin() ) {
		Button btnStergeCarte = new Button(shlCarti, SWT.NONE);
		btnStergeCarte.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int id = Integer.parseInt(carteid.getText());
				try {
					cartidao.deleteCarte(id);
					JOptionPane.showMessageDialog(null, "Carte stearsa cu succes.");
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Stergerea cartii a esuat.");
				}
			}
		});
		btnStergeCarte.setText("Sterge");
		btnStergeCarte.setBounds(416, 364, 48, 25);
		}
		Button btnNewButton = new Button(shlCarti, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrame home = new JFrame();
				shlCarti.dispose();
				home.main(user);
			}
		});
		
		btnNewButton.setText("Back Home");
		btnNewButton.setBounds(58, 470, 85, 41);
		
		
			Label lblNewLabel = new Label(shlCarti, SWT.NONE);
			lblNewLabel.setBounds(245, 259, 41, 15);
			lblNewLabel.setText("Id Carte");
			
			Label lblNewLabel_1 = new Label(shlCarti, SWT.NONE);
			lblNewLabel_1.setBounds(234, 286, 52, 15);
			lblNewLabel_1.setText("Denumire");
		
			Label lblAnulDeAparitie = new Label(shlCarti, SWT.NONE);
			lblAnulDeAparitie.setBounds(201, 313, 85, 15);
			lblAnulDeAparitie.setText("Anul de aparitie");
		
			Label lblEditura = new Label(shlCarti, SWT.NONE);
			lblEditura.setBounds(245, 340, 41, 15);
			lblEditura.setText("Editura");
		
		

	}
}
