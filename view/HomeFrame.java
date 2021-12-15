package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import model.Users;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomeFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(Users user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeFrame window = new HomeFrame(user);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HomeFrame(Users user) {
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Users user) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1093, 583);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Autori");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				AutoriFrame autori = new AutoriFrame(user);
				autori.main(user);
			}
		});
		btnNewButton.setBounds(123, 270, 107, 42);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnCarti = new JButton("Carti");
		btnCarti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				CartiFrame carti = new CartiFrame(user);
				carti.main(user);
			}
		});
		btnCarti.setBounds(290, 270, 107, 42);
		frame.getContentPane().add(btnCarti);
		
		JButton btnBiblioteca = new JButton("Biblioteca");
		btnBiblioteca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				BibliotecaFrame biblioteca = new BibliotecaFrame(user);
				biblioteca.main(user);
			}
		});
		btnBiblioteca.setBounds(470, 270, 107, 42);
		frame.getContentPane().add(btnBiblioteca);
		
		JButton btnRafturi = new JButton("Rafturi");
		btnRafturi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				RafturiFrame rafturi = new RafturiFrame(user);
				rafturi.main(user);
			}
		});
		btnRafturi.setBounds(643, 270, 107, 42);
		frame.getContentPane().add(btnRafturi);
		
		JButton btnVolume = new JButton("Volume");
		btnVolume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				VolumeFrame volume = new VolumeFrame(user);
				volume.main(user);
			}
		});
		btnVolume.setBounds(813, 270, 107, 42);
		frame.getContentPane().add(btnVolume);
		
		JButton btnNewButton_1 = new JButton("Log out");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				LoginFrame login = new LoginFrame();
				login.main(null);
			}
		});
		btnNewButton_1.setBounds(454, 435, 123, 52);
		frame.getContentPane().add(btnNewButton_1);
	}
}
