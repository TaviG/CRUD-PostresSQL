package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.AutoriDAO;
import dao.CartiDAO;
import dao.VolumeDAO;
import model.Autori;
import model.Biblioteca;
import model.Carti;
import model.Rafturi;
import model.Users;
import model.Volume;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class VolumeFrame {

	private JFrame frame;
	private JTable table;
	private JTextField nrvolum;
	private VolumeDAO volumedao = new VolumeDAO();
	private CartiDAO cartidao = new CartiDAO();
	private AutoriDAO autoridao = new AutoriDAO();
	private JComboBox volumid = new JComboBox();
	private JComboBox autorid = new JComboBox();
	private JComboBox carteid = new JComboBox();
	/**
	 * Launch the application.
	 */
	public static void main(Users user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VolumeFrame window = new VolumeFrame(user);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void FillData() {
		DefaultComboBoxModel	combo = new DefaultComboBoxModel();
		DefaultComboBoxModel	combo2 = new DefaultComboBoxModel();
		DefaultComboBoxModel	combo3 = new DefaultComboBoxModel();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Volumid");
		model.addColumn("Nume Autor");
		model.addColumn("Prenume Autor");
		model.addColumn("Tara de origine");
		model.addColumn("Denumire Carte");
		model.addColumn("Anul aparitiei");
		model.addColumn("Editura");
		model.addColumn("Numarul volumului");
		for (Volume volum : volumedao.selectAllVolume()) {
			model.addRow(new Object[] {
					volum.getVolumid(), volum.getAutor().getNumeautor(), volum.getAutor().getPrenumeautor(), volum.getAutor().getTaraorigine(), volum.getCarte().getDenumire(), volum.getCarte().getAnaparitie(), volum.getCarte().getEditura(), volum.getNrvolum()
			});
			combo.addElement(String.valueOf(volum.getVolumid()) + ", Numarul volumului:" + volum.getNrvolum());
			
			
		}
		table.setModel(model);
		volumid.setModel(combo);
		for (Carti carte : cartidao.selectAllCarti()) {
			combo2.addElement(String.valueOf(carte.getCarteid()) + ", " + carte.getDenumire());
		}
		carteid.setModel(combo2);
		for ( Autori autor : autoridao.selectAllAutori()) {
			combo3.addElement(String.valueOf(autor.getAutorid()) + ", " + autor.getNumeautor());
		}
		autorid.setModel(combo3);
		
	}
	
	/**
	 * Create the application.
	 */
	public VolumeFrame(Users user) {
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Users user) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1097, 586);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(189, 30, 683, 194);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		volumid.setBounds(422, 263, 164, 22);
		frame.getContentPane().add(volumid);
		
		
		autorid.setBounds(422, 306, 164, 22);
		frame.getContentPane().add(autorid);
		
		
		carteid.setBounds(422, 349, 164, 22);
		frame.getContentPane().add(carteid);
		
		nrvolum = new JTextField();
		nrvolum.setColumns(10);
		nrvolum.setBounds(422, 389, 164, 20);
		frame.getContentPane().add(nrvolum);
		
		JButton btnNewButton = new JButton("Adauga");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Carti carte = cartidao.selectCarte(Integer.parseInt(carteid.getSelectedItem().toString().split(",")[0]));
				Autori autor = autoridao.selectAutor(Integer.parseInt(autorid.getSelectedItem().toString().split(",")[0]));
				Volume volum = new Volume(autor, carte, Integer.parseInt(nrvolum.getText()));
				try {
					volumedao.insertVolum(volum);
					JOptionPane.showMessageDialog(null, "Volum adaugat cu succes.");
					
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Adaugarea volumului a esuat.");
				}
			}
		});
		btnNewButton.setBounds(336, 427, 82, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnEditeaza = new JButton("Editeaza");
		btnEditeaza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Carti carte = cartidao.selectCarte(Integer.parseInt(carteid.getSelectedItem().toString().split(",")[0]));
				Autori autor = autoridao.selectAutor(Integer.parseInt(autorid.getSelectedItem().toString().split(",")[0]));
				Volume volum = new Volume(Integer.parseInt(volumid.getSelectedItem().toString().split(",")[0]),autor, carte, Integer.parseInt(nrvolum.getText()));
				try {
					volumedao.updateVolum(volum);
					JOptionPane.showMessageDialog(null, "Volum editat cu succes.");
					
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Editarea volumului a esuat.");
				}
			}
		});
		btnEditeaza.setBounds(432, 427, 82, 23);
		frame.getContentPane().add(btnEditeaza);
		if ( user.isAdmin() ) {
		JButton btnSterge = new JButton("Sterge");
		btnSterge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(volumid.getSelectedItem().toString().split(",")[0]);
				try {
					volumedao.deleteVolum(id);
					JOptionPane.showMessageDialog(null, "Volum sters cu succes.");
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Stergerea volumului a esuat.");
				}
			}
		});
		btnSterge.setBounds(524, 427, 72, 23);
		frame.getContentPane().add(btnSterge);
		}
		JLabel lblVolumid = new JLabel("Volumid");
		lblVolumid.setBounds(315, 267, 97, 14);
		frame.getContentPane().add(lblVolumid);
		
		JLabel lblAutorid = new JLabel("Autorid");
		lblAutorid.setBounds(315, 310, 97, 14);
		frame.getContentPane().add(lblAutorid);
		
		JLabel lblCarteid = new JLabel("Carteid");
		lblCarteid.setBounds(315, 353, 97, 14);
		frame.getContentPane().add(lblCarteid);
		
		JLabel lblNumarulVolumului = new JLabel("Numarul Volumului");
		lblNumarulVolumului.setBounds(315, 392, 97, 14);
		frame.getContentPane().add(lblNumarulVolumului);
		
		JButton btnNewButton_1 = new JButton("Home");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				HomeFrame home = new HomeFrame(user);
				home.main(user);
			}
		});
		btnNewButton_1.setBounds(31, 461, 89, 64);
		frame.getContentPane().add(btnNewButton_1);
		FillData();
	}
}
