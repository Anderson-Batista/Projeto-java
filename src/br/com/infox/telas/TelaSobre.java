package br.com.infox.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;

public class TelaSobre extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaSobre frame = new TelaSobre();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaSobre() {
		setTitle("Sobre");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sistema para controle de Ordens de Servi\u00E7os");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(135, 73, 371, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblDesenvolvidoPorAnderson = new JLabel("Desenvolvido por Anderson Batista");
		lblDesenvolvidoPorAnderson.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDesenvolvidoPorAnderson.setBounds(135, 126, 371, 25);
		contentPane.add(lblDesenvolvidoPorAnderson);
		
		JLabel lblSobALicena = new JLabel("Sob a licen\u00E7a GPL");
		lblSobALicena.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSobALicena.setBounds(135, 181, 289, 25);
		contentPane.add(lblSobALicena);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(TelaSobre.class.getResource("/br/com/infox/icones/GNU.png")));
		lblNewLabel_1.setBounds(253, 263, 76, 141);
		contentPane.add(lblNewLabel_1);
		
		setLocationRelativeTo(null);
	}
}
