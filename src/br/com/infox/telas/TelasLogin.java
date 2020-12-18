package br.com.infox.telas;

import java.awt.BorderLayout; // Obs: ver erro na aula 15 no campo perfil da tela do usuario

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Component;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelasLogin extends JFrame {

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	
	public void logar() {
		// Logica principal para pesquisar no banco de dados
		String sql = "select * from tbusuarios where login = ? and senha = ?";
		
		try {
			// As linhas abaixo preparam a consulta ao banco de dados em função do
			// que foi digitado nas caixas de texto. A ? é substituida pelo
			// conteúdo das variáveis 
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsuario.getText());
			pst.setString(2, txtSenha.getText());
			// a linha abaixo executa uma query
			rs = pst.executeQuery();
			// se existir usuário e senha correspondente
			if(rs.next()){
				// a linha abaixo obtem o campo perfil da tabela tbusuario 
				String perfil = rs.getString(6);
				//System.out.println(perfil);
				// a linha abaixo exibe o conteúdo do campo tabela
				if(perfil.equals("admin")) {
				TelaPrincipal principal = new TelaPrincipal();
				principal.setVisible(true);
				TelaPrincipal.menuRelatorio.setEnabled(true);
				TelaPrincipal.menuCadastroUsuarios.setEnabled(true);
				TelaPrincipal.lblUsuario.setText(rs.getString(2));
				TelaPrincipal.lblUsuario.setForeground(Color.red);
				this.dispose();
				}else {
					TelaPrincipal principal = new TelaPrincipal();
					principal.setVisible(true);
					TelaPrincipal.lblUsuario.setText(rs.getString(2));
					this.dispose();
				}
				conexao.close();
			}else {
				JOptionPane.showMessageDialog(null, "Usuario e/ou senha inválido(s)");
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelasLogin frame = new TelasLogin();
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
	public TelasLogin() {
		setResizable(false);
		setForeground(Color.LIGHT_GRAY);
		setBackground(new Color(192, 192, 192));
		setTitle("X System - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 417, 255);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usu\u00E1rio");
		lblNewLabel.setBounds(30, 36, 64, 28);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(lblNewLabel);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(30, 94, 64, 28);
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(lblSenha);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(139, 39, 197, 28);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// chamando o metodo logar
				logar();
			}
		});
		btnLogin.setBounds(240, 162, 96, 28);
		contentPane.add(btnLogin);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(139, 94, 197, 27);
		contentPane.add(txtSenha);
		
		JLabel lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon("C:\\Users\\Anderson\\Pictures\\Saved Pictures\\dberror.jpg"));
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStatus.setBounds(30, 162, 114, 28);
		contentPane.add(lblStatus);
		
		conexao = ModuloConexao.conector();
		//A linha abaixo serve de apoio ao status da conexao
		//System.out.println(conexao);
		if(conexao != null) {
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/dbok.png")));
		}else {
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/dberror.png")));
		}
	}
}





