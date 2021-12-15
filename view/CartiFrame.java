package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import dao.CartiDAO;
import model.Carti;
import model.Users;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CartiFrame {

	private JFrame frame;
	private JTable table;
	private JTextField denumire;
	private JTextField anaparitie;
	private JTextField editura;
	private CartiDAO cartidao = new CartiDAO();
	private JComboBox carteid = new JComboBox();
	/**
	 * Launch the application.
	 */
	public static void main(Users user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CartiFrame window = new CartiFrame(user);
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
		model.addColumn("Carteid");
		model.addColumn("Denumire");
		model.addColumn("Anul aparitiei");
		model.addColumn("Editura");
		for (Carti carte : cartidao.selectAllCarti()) {
			model.addRow(new Object[] {
					carte.getCarteid(), carte.getDenumire(), carte.getAnaparitie(), carte.getEditura()
			});
			combo.addElement(String.valueOf(carte.getCarteid()) + ", " + carte.getDenumire());
		}
		table.setModel(model);
		carteid.setModel(combo);
	}
	
	/**
	 * Create the application.
	 */
	public CartiFrame(Users user) {
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Users user) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1092, 579);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(211, 30, 601, 173);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		carteid.setBounds(421, 246, 164, 22);
		frame.getContentPane().add(carteid);
		
		denumire = new JTextField();
		denumire.setColumns(10);
		denumire.setBounds(421, 291, 164, 20);
		frame.getContentPane().add(denumire);
		
		anaparitie = new JTextField();
		anaparitie.setColumns(10);
		anaparitie.setBounds(421, 335, 164, 20);
		frame.getContentPane().add(anaparitie);
		
		editura = new JTextField();
		editura.setColumns(10);
		editura.setBounds(421, 380, 164, 20);
		frame.getContentPane().add(editura);
		
		JButton btnNewButton = new JButton("Adauga");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Carti carte = new Carti(denumire.getText(), Date.valueOf(anaparitie.getText()),editura.getText() );
				try {
					cartidao.insertCarte(carte);
					JOptionPane.showMessageDialog(null, "Carte adaugata cu succes.");
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Adaugarea cartii a esuat.");
				}
			}
		});
		btnNewButton.setBounds(314, 423, 82, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnEditeaza = new JButton("Editeaza");
		btnEditeaza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Carti carte = new Carti(Integer.parseInt(carteid.getSelectedItem().toString().split(",")[0]), denumire.getText(), Date.valueOf(anaparitie.getText()),editura.getText() );
				try {
					cartidao.updateCarte(carte);
					JOptionPane.showMessageDialog(null, "Carte editata cu succes.");
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Editarea cartii a esuat.");
				}
			}
		});
		btnEditeaza.setBounds(421, 423, 82, 23);
		frame.getContentPane().add(btnEditeaza);
		if ( user.isAdmin() ) {
		JButton btnSterge = new JButton("Sterge");
		btnSterge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(carteid.getSelectedItem().toString().split(",")[0]);
				try {
					cartidao.deleteCarte(id);
					JOptionPane.showMessageDialog(null, "Carte stearsa cu succes.");
					FillData();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Stergerea cartii a esuat.");
				}
			}
		});
		btnSterge.setBounds(524, 423, 72, 23);
		frame.getContentPane().add(btnSterge);
		}
		JLabel lblCarteid = new JLabel("Carteid");
		lblCarteid.setBounds(314, 250, 97, 14);
		frame.getContentPane().add(lblCarteid);
		
		JLabel lblNewLabel_1 = new JLabel("Denumire");
		lblNewLabel_1.setBounds(314, 294, 97, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Anul aparitiei");
		lblNewLabel_1_1.setBounds(314, 338, 97, 14);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Editura");
		lblNewLabel_1_2.setBounds(314, 383, 97, 14);
		frame.getContentPane().add(lblNewLabel_1_2);
		
		JButton btnNewButton_1 = new JButton("Home");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				HomeFrame home = new HomeFrame(user);
				home.main(user);
			}
		});
		btnNewButton_1.setBounds(32, 451, 89, 64);
		frame.getContentPane().add(btnNewButton_1);
		FillData();
	}
}
