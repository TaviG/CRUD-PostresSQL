package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import dao.AutoriDAO;
import model.Autori;
import model.Users;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class AutoriFrame {

	private JFrame frame;
	private JTable table;
	private AutoriDAO autoridao = new AutoriDAO();
	private JTextField numeautor;
	private JTextField prenumeautor;
	private JTextField taraorigine;
	private JComboBox autorid = new JComboBox();

	/**
	 * Launch the application.
	 */
	public static void main(Users user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutoriFrame window = new AutoriFrame(user);
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
	model.addColumn("Autorid");
	model.addColumn("Nume Autor");
	model.addColumn("Prenume Autor");
	model.addColumn("Tara de Origine");
	for (Autori autor : autoridao.selectAllAutori()) {
		model.addRow(new Object[] {
				autor.getAutorid(), autor.getNumeautor(), autor.getPrenumeautor(), autor.getTaraorigine()
		});
		combo.addElement(String.valueOf(autor.getAutorid()) + ", " + autor.getNumeautor());
	}
	table.setModel(model);
	autorid.setModel(combo);
	}
	
	/**
	 * Create the application.
	 */
	public AutoriFrame(Users user) {
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Users user) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1074, 575);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(181, 25, 686, 205);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		frame.getContentPane().add(autorid);
		numeautor = new JTextField();
		numeautor.setBounds(431, 307, 164, 20);
		frame.getContentPane().add(numeautor);
		numeautor.setColumns(10);
		
		prenumeautor = new JTextField();
		prenumeautor.setBounds(431, 338, 164, 20);
		frame.getContentPane().add(prenumeautor);
		prenumeautor.setColumns(10);
		
		taraorigine = new JTextField();
		taraorigine.setBounds(431, 369, 164, 20);
		frame.getContentPane().add(taraorigine);
		taraorigine.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Autorid");
		lblNewLabel.setBounds(324, 279, 97, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nume Autor");
		lblNewLabel_1.setBounds(324, 310, 97, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Prenume Autor");
		lblNewLabel_2.setBounds(324, 341, 97, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Tara de Origine");
		lblNewLabel_3.setBounds(324, 372, 97, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("Adauga");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Autori autor = new Autori(numeautor.getText(), prenumeautor.getText(), taraorigine.getText());
				try {
					autoridao.insertAutor(autor);
					JOptionPane.showMessageDialog(null, "Autor adaugat cu succes.");
					
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Adaugarea autorului a esuat.");
				}
			}
		});
		btnNewButton.setBounds(337, 413, 82, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnEditeaza = new JButton("Editeaza");
		btnEditeaza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Autori autor = new Autori(Integer.parseInt(autorid.getSelectedItem().toString().split(",")[0]),numeautor.getText(), prenumeautor.getText(), taraorigine.getText());
				try {
					autoridao.updateAutor(autor);
					JOptionPane.showMessageDialog(null, "Autor editat cu succes.");
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Editarea autorului a esuat.");
				}
			}
		});
		btnEditeaza.setBounds(431, 413, 82, 23);
		frame.getContentPane().add(btnEditeaza);
		if ( user.isAdmin() ) {
		JButton btnSterge = new JButton("Sterge");
		btnSterge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(autorid.getSelectedItem().toString().split(",")[0]);
				try {
					autoridao.deleteAutor(id);
					JOptionPane.showMessageDialog(null, "Autor sters cu succes.");
					
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Stergerea autorului a esuat.");
				}
			}
		});
		btnSterge.setBounds(523, 413, 72, 23);
		frame.getContentPane().add(btnSterge);
		}
		JButton btnNewButton_1 = new JButton("Home");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				HomeFrame home = new HomeFrame(user);
				home.main(user);
			}
		});
		btnNewButton_1.setBounds(44, 441, 89, 64);
		frame.getContentPane().add(btnNewButton_1);
		
		
		
		autorid.setBounds(431, 275, 164, 22);
		
		FillData();
	}
}
