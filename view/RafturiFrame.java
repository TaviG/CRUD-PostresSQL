package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.BibliotecaDAO;
import dao.CartiDAO;
import dao.RafturiDAO;
import model.Biblioteca;
import model.Carti;
import model.Rafturi;
import model.Users;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RafturiFrame {

	private JFrame frame;
	private JTable table;
	private JTextField codraft;
	private RafturiDAO rafturidao = new RafturiDAO();
	private CartiDAO cartidao = new CartiDAO();
	private BibliotecaDAO bibliotecadao = new BibliotecaDAO();
	private JComboBox raftid = new JComboBox();
	private JComboBox carteid = new JComboBox();
	private JComboBox bibliotecaid = new JComboBox();
	
	/**
	 * Launch the application.
	 */
	public static void main(Users user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RafturiFrame window = new RafturiFrame(user);
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
		model.addColumn("Raftid");
		model.addColumn("Denumire carte");
		model.addColumn("Anul aparitiei");
		model.addColumn("Editura");
		model.addColumn("Denumire Biblioteca");
		model.addColumn("Adresa");
		model.addColumn("Codul Raftului");
		for (Rafturi raft : rafturidao.selectAllRafturi()) {
			model.addRow(new Object[] {
					raft.getRaftid(), raft.getCarte().getDenumire(), raft.getCarte().getAnaparitie(), raft.getCarte().getEditura(), raft.getBiblioteca().getDenumire(), raft.getBiblioteca().getAdresa(), raft.getCodraft()
			});
			combo.addElement(String.valueOf(raft.getRaftid()) + ", " + raft.getCodraft());
			
			
		}
		table.setModel(model);
		raftid.setModel(combo);
		for (Carti carte : cartidao.selectAllCarti()) {
			combo2.addElement(String.valueOf(carte.getCarteid()) + ", " + carte.getDenumire());
		}
		carteid.setModel(combo2);
		for ( Biblioteca biblioteca : bibliotecadao.selectAllBiblioteca()) {
			combo3.addElement(String.valueOf(biblioteca.getBibliotecaid()) + ", " + biblioteca.getDenumire());
		}
		bibliotecaid.setModel(combo3);
		
	}
	

	/**
	 * Create the application.
	 */
	public RafturiFrame(Users user) {
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Users user) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1090, 574);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(218, 30, 652, 173);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		raftid.setBounds(473, 254, 164, 22);
		frame.getContentPane().add(raftid);
		
		
		carteid.setBounds(473, 302, 164, 22);
		frame.getContentPane().add(carteid);
		
		codraft = new JTextField();
		codraft.setColumns(10);
		codraft.setBounds(473, 389, 164, 20);
		frame.getContentPane().add(codraft);
		
		
		bibliotecaid.setBounds(473, 342, 164, 22);
		frame.getContentPane().add(bibliotecaid);
		
		JButton btnNewButton = new JButton("Adauga");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Carti carte = cartidao.selectCarte(Integer.parseInt(carteid.getSelectedItem().toString().split(",")[0]));
				Biblioteca biblioteca = bibliotecadao.selectBiblioteca(Integer.parseInt(bibliotecaid.getSelectedItem().toString().split(",")[0]));
				Rafturi raft = new Rafturi(carte, biblioteca, codraft.getText());
				try {
					rafturidao.insertRaft(raft);
					JOptionPane.showMessageDialog(null, "Raft adaugat cu succes.");
					
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Adaugarea raftului a esuat.");
				}
			}
		});
		btnNewButton.setBounds(366, 430, 82, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnEditeaza = new JButton("Editeaza");
		btnEditeaza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Carti carte = cartidao.selectCarte(Integer.parseInt(carteid.getSelectedItem().toString().split(",")[0]));
				Biblioteca biblioteca = bibliotecadao.selectBiblioteca(Integer.parseInt(bibliotecaid.getSelectedItem().toString().split(",")[0]));
				Rafturi raft = new Rafturi(Integer.parseInt(raftid.getSelectedItem().toString().split(",")[0]), carte, biblioteca, codraft.getText());
				try {
					rafturidao.updateRaft(raft);
					JOptionPane.showMessageDialog(null, "Raft editat cu succes.");
					
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Editarea raftului a esuat.");
				}
			}
		});
		btnEditeaza.setBounds(473, 430, 82, 23);
		frame.getContentPane().add(btnEditeaza);
		if ( user.isAdmin() ) {
		JButton btnSterge = new JButton("Sterge");
		btnSterge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(raftid.getSelectedItem().toString().split(",")[0]);
				try {
					rafturidao.deleteRaft(id);
					JOptionPane.showMessageDialog(null, "Raft sters cu succes.");
					
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Stergerea raftului a esuat.");
				}
			}
		});
		btnSterge.setBounds(579, 430, 72, 23);
		frame.getContentPane().add(btnSterge);
		}
		JLabel lblRaftid = new JLabel("Raftid");
		lblRaftid.setBounds(366, 258, 97, 14);
		frame.getContentPane().add(lblRaftid);
		
		JLabel lblCarteid_1 = new JLabel("Carteid");
		lblCarteid_1.setBounds(366, 306, 97, 14);
		frame.getContentPane().add(lblCarteid_1);
		
		JLabel lblBibliotecaid = new JLabel("Bibliotecaid");
		lblBibliotecaid.setBounds(366, 346, 97, 14);
		frame.getContentPane().add(lblBibliotecaid);
		
		JLabel lblCodulRaftului = new JLabel("Codul raftului");
		lblCodulRaftului.setBounds(366, 392, 97, 14);
		frame.getContentPane().add(lblCodulRaftului);
		
		JButton btnNewButton_1 = new JButton("Home");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				HomeFrame home = new HomeFrame(user);
				home.main(user);
			}
		});
		btnNewButton_1.setBounds(32, 448, 89, 64);
		frame.getContentPane().add(btnNewButton_1);
		FillData();
	}
}
