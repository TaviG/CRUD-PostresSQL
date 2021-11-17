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
import dao.CartiDAO;
import dao.VolumeDAO;
import model.Autori;
import model.Biblioteca;
import model.Carti;
import model.Rafturi;
import model.Users;
import model.Volume;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class JFrameVolume {

	protected Shell shlVolume;
	private Table tableVolume;
	private Text nrvolum;
	private Combo volumid;
	private VolumeDAO volumedao = new VolumeDAO();
	private CartiDAO cartidao = new CartiDAO();
	private AutoriDAO autoridao = new AutoriDAO();
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(Users user) {
		try {
			JFrameVolume window = new JFrameVolume();
			window.open(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void FillData(Users user) {
		
		volumid.removeAll();
		volumid.setText("Select the id");
		tableVolume.removeAll();
		for (Volume volum :volumedao.selectAllVolume()) {
			TableItem tableItem = new TableItem(tableVolume, SWT.NONE);
			tableItem.setText(new String[] {String.valueOf(volum.getVolumid()), volum.getAutor().getNumeautor(), volum.getAutor().getPrenumeautor(),volum.getAutor().getTaraorigine(), volum.getCarte().getDenumire(),String.valueOf(volum.getCarte().getAnaparitie()), volum.getCarte().getEditura(), String.valueOf(volum.getNrvolum())});
				volumid.add(String.valueOf(volum.getVolumid()));
		}
	}

	/**
	 * Open the window.
	 */
	public void open(Users user) {
		Display display = Display.getDefault();
		createContents(user);
		FillData(user);
		shlVolume.open();
		shlVolume.layout();
		while (!shlVolume.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Users user) {
		shlVolume = new Shell();
		shlVolume.setSize(869, 563);
		shlVolume.setText("Volume");
		
		tableVolume = new Table(shlVolume, SWT.BORDER | SWT.FULL_SELECTION);
		tableVolume.setBounds(10, 20, 822, 225);
		tableVolume.setHeaderVisible(true);
		tableVolume.setLinesVisible(true);
		
			volumid = new Combo(shlVolume, SWT.NONE);
			volumid.setBounds(338, 284, 156, 23);
			volumid.setText("Select the id");
		
		TableColumn tblclmnIdVolum = new TableColumn(tableVolume, SWT.NONE);
		tblclmnIdVolum.setWidth(100);
		tblclmnIdVolum.setText("Id Volum");
		
		TableColumn tblclmnNumeAutor = new TableColumn(tableVolume, SWT.NONE);
		tblclmnNumeAutor.setWidth(100);
		tblclmnNumeAutor.setText("Nume Autor");
		
		TableColumn tblclmnPrenumeAutor = new TableColumn(tableVolume, SWT.NONE);
		tblclmnPrenumeAutor.setWidth(100);
		tblclmnPrenumeAutor.setText("Prenume Autor");
		
		TableColumn tblclmnTaraDeOrigine = new TableColumn(tableVolume, SWT.NONE);
		tblclmnTaraDeOrigine.setWidth(100);
		tblclmnTaraDeOrigine.setText("Tara de Origine");
		
		TableColumn tblclmnDenumire = new TableColumn(tableVolume, SWT.NONE);
		tblclmnDenumire.setWidth(100);
		tblclmnDenumire.setText("Denumire Carte");
		
		TableColumn tblclmnAnulAparitiei = new TableColumn(tableVolume, SWT.NONE);
		tblclmnAnulAparitiei.setWidth(100);
		tblclmnAnulAparitiei.setText("Anul aparitiei");
		
		TableColumn tblclmnEditura = new TableColumn(tableVolume, SWT.NONE);
		tblclmnEditura.setWidth(100);
		tblclmnEditura.setText("Editura");
		
		TableColumn tblclmnNumarulVolumului = new TableColumn(tableVolume, SWT.NONE);
		tblclmnNumarulVolumului.setWidth(120);
		tblclmnNumarulVolumului.setText("Numarul volumului");
		
		
		
		Combo autorid = new Combo(shlVolume, SWT.NONE);
		autorid.setBounds(338, 313, 156, 23);
		autorid.setText("Select the id");
		
		for (Autori autor : autoridao.selectAllAutori()) {
			autorid.add(String.valueOf(autor.getAutorid())+", "+autor.getNumeautor());
		}
		
		Combo carteid = new Combo(shlVolume, SWT.NONE);
		carteid.setBounds(338, 341, 156, 23);
		carteid.setText("Select the id");
		
		for (Carti carte : cartidao.selectAllCarti()) {
			carteid.add(String.valueOf(carte.getCarteid())+", "+carte.getDenumire());
		}
		
		nrvolum = new Text(shlVolume, SWT.BORDER);
		nrvolum.setBounds(338, 370, 156, 21);
		
		Button btnAdaugaAutor = new Button(shlVolume, SWT.NONE);
		btnAdaugaAutor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Carti carte = cartidao.selectCarte(Integer.parseInt(carteid.getText().split(",")[0]));
				Autori autor = autoridao.selectAutor(Integer.parseInt(autorid.getText().split(",")[0]));
				Volume volum = new Volume(autor, carte, Integer.parseInt(nrvolum.getText()));
				try {
					volumedao.insertVolum(volum);
					JOptionPane.showMessageDialog(null, "Volum adaugat cu succes.");
					
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Adaugarea volumului a esuat.");
				}
			}
		});
		btnAdaugaAutor.setText("Adauga");
		btnAdaugaAutor.setBounds(338, 397, 48, 25);
		
		Button btnEditeazaAutor = new Button(shlVolume, SWT.NONE);
		btnEditeazaAutor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Carti carte = cartidao.selectCarte(Integer.parseInt(carteid.getText().split(",")[0]));
				Autori autor = autoridao.selectAutor(Integer.parseInt(autorid.getText().split(",")[0]));
				Volume volum = new Volume(Integer.parseInt(volumid.getText()),autor, carte, Integer.parseInt(nrvolum.getText()));
				try {
					volumedao.updateVolum(volum);
					JOptionPane.showMessageDialog(null, "Volum editat cu succes.");
					
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Editarea volumului a esuat.");
				}
			}
		});
		btnEditeazaAutor.setText("Editeaza");
		btnEditeazaAutor.setBounds(392, 397, 48, 25);
		if ( user.isAdmin() ) {
		Button btnStergeAutor = new Button(shlVolume, SWT.NONE);
		btnStergeAutor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int id = Integer.parseInt(volumid.getText());
				try {
					volumedao.deleteVolum(id);
					JOptionPane.showMessageDialog(null, "Volum sters cu succes.");
					
					FillData(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Stergerea volumului a esuat.");
				}
			}
		});
		btnStergeAutor.setText("Sterge");
		btnStergeAutor.setBounds(446, 397, 48, 25);
		}
		
		
		Button btnNewButton = new Button(shlVolume, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrame home = new JFrame();
				shlVolume.dispose();
				home.main(user);
			}
		});
		btnNewButton.setText("Back Home");
		btnNewButton.setBounds(10, 473, 85, 41);
		
		
		
		Label lblNewLabel = new Label(shlVolume, SWT.NONE);
		lblNewLabel.setBounds(277, 287, 48, 15);
		lblNewLabel.setText("Id Volum");
		
		Label lblIdAutor = new Label(shlVolume, SWT.NONE);
		lblIdAutor.setBounds(284, 316, 48, 15);
		lblIdAutor.setText("Id Autor");
		
		Label lblIdCarte = new Label(shlVolume, SWT.NONE);
		lblIdCarte.setBounds(284, 344, 48, 15);
		lblIdCarte.setText("Id Carte");
		
		Label lblNrVolum = new Label(shlVolume, SWT.NONE);
		lblNrVolum.setBounds(277, 373, 55, 15);
		lblNrVolum.setText("Nr Volum");

	
	}

}
