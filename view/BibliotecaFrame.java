package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.BibliotecaDAO;
import model.Autori;
import model.Biblioteca;
import model.Users;

import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class BibliotecaFrame {

	private JFrame frame;
	private JTextField denumire;
	private JTextField adresa;
	private JTable table;
	private BibliotecaDAO bibliotecadao = new BibliotecaDAO();
	private JComboBox bibliotecaid = new JComboBox();
	/**
	 * Launch the application.
	 */
	public static void main(Users user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BibliotecaFrame window = new BibliotecaFrame(user);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private void FillData() {
		DefaultComboBoxModel	combo = new DefaultComboBoxModel();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Bibliotecaid");
		model.addColumn("Denumire");
		model.addColumn("Adresa");
		for (model.Biblioteca biblioteca : bibliotecadao.selectAllBiblioteca()) {
			model.addRow(new Object[] {
					biblioteca.getBibliotecaid(), biblioteca.getDenumire(), biblioteca.getAdresa()
			});
			combo.addElement(String.valueOf(biblioteca.getBibliotecaid()) + ", " + biblioteca.getDenumire());
		}
		table.setModel(model);
		bibliotecaid.setModel(combo);
		}

	/**
	 * Create the application.
	 */
	public BibliotecaFrame(Users user) {
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Users user) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1097, 584);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		bibliotecaid.setBounds(414, 290, 164, 22);
		frame.getContentPane().add(bibliotecaid);
		
		denumire = new JTextField();
		denumire.setColumns(10);
		denumire.setBounds(414, 335, 164, 20);
		frame.getContentPane().add(denumire);
		
		adresa = new JTextField();
		adresa.setColumns(10);
		adresa.setBounds(414, 378, 164, 20);
		frame.getContentPane().add(adresa);
		
		JLabel lblBibliotecaid = new JLabel("Bibliotecaid");
		lblBibliotecaid.setBounds(307, 294, 97, 14);
		frame.getContentPane().add(lblBibliotecaid);
		
		JLabel lblNewLabel_1 = new JLabel("Denumire");
		lblNewLabel_1.setBounds(307, 338, 97, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Adresa");
		lblNewLabel_2.setBounds(307, 381, 97, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("Home");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				HomeFrame home = new HomeFrame(user);
				home.main(user);
			}
		});
		btnNewButton_1.setBounds(44, 454, 89, 64);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Adauga");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.Biblioteca biblioteca = new model.Biblioteca(denumire.getText(), adresa.getText());
				try {
					bibliotecadao.insertBiblioteca(biblioteca);
					JOptionPane.showMessageDialog(null, "Biblioteca adaugata cu succes.");
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Adaugarea bibliotecii a esuat.");
				}
			}
		});
		btnNewButton.setBounds(307, 423, 82, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnEditeaza = new JButton("Editeaza");
		btnEditeaza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Biblioteca biblioteca = new Biblioteca(Integer.parseInt(bibliotecaid.getSelectedItem().toString().split(",")[0]),denumire.getText(), adresa.getText());
				try {
					bibliotecadao.updateBiblioteca(biblioteca);
					JOptionPane.showMessageDialog(null, "Biblioteca editata cu succes.");
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Editarea bibliotecii a esuat.");
				}
			}
		});
		btnEditeaza.setBounds(414, 423, 82, 23);
		frame.getContentPane().add(btnEditeaza);
		if ( user.isAdmin() ) {
		JButton btnSterge = new JButton("Sterge");
		btnSterge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(bibliotecaid.getSelectedItem().toString().split(",")[0]);
				try {
					bibliotecadao.deleteBiblioteca(id);
					JOptionPane.showMessageDialog(null, "Biblioteca stearsa cu succes.");
					
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Stergerea bibliotecii a esuat.");
				}
			}
		});
		btnSterge.setBounds(516, 423, 72, 23);
		frame.getContentPane().add(btnSterge);
		}
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(198, 36, 636, 195);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		FillData();
	}
}
