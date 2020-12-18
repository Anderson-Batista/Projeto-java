package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;

public class TelaUsuario extends JInternalFrame {
	private JTextField TextoUsuarioId;
	private JTextField textoUsuarioNome;
	private JTextField textoUsuarioLogin;
	private JTextField textoUsuarioSenha;
	private JTextField textoUsuarioTelefone;
	private JComboBox cboUsuarioPerfil;
	
	
	// Usando a variável de conexão do DAL
	Connection conexao = null;
	// Criando variáveis especiais para conexão com o banco
	// prepared statement e result set são frameworks do pacote java.sql
	// e servem para prepara e executar as instruções SQL
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JComboBox cboUsuarioPerfil_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaUsuario frame = new TelaUsuario();
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
	public TelaUsuario() {
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setTitle("Usu\u00E1rios");
		setBounds(0, 0, 640, 480);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("*Id");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(91, 88, 35, 20);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNome = new JLabel("*Nome");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNome.setBounds(91, 119, 74, 20);
		getContentPane().add(lblNome);
		
		JLabel lblLogin = new JLabel("*Login");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLogin.setBounds(91, 150, 74, 20);
		getContentPane().add(lblLogin);
		
		JLabel lblSenha = new JLabel("*Senha");
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSenha.setBounds(91, 181, 74, 20);
		getContentPane().add(lblSenha);
		
		JLabel lblPerfil = new JLabel("*Perfil");
		lblPerfil.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPerfil.setBounds(91, 243, 74, 20);
		getContentPane().add(lblPerfil);
		
		TextoUsuarioId = new JTextField();
		TextoUsuarioId.setBounds(175, 90, 300, 20);
		getContentPane().add(TextoUsuarioId);
		TextoUsuarioId.setColumns(10);
		
		textoUsuarioNome = new JTextField();
		textoUsuarioNome.setColumns(10);
		textoUsuarioNome.setBounds(175, 121, 300, 20);
		getContentPane().add(textoUsuarioNome);
		
		textoUsuarioLogin = new JTextField();
		textoUsuarioLogin.setColumns(10);
		textoUsuarioLogin.setBounds(175, 152, 300, 20);
		getContentPane().add(textoUsuarioLogin);
		
		textoUsuarioSenha = new JTextField();
		textoUsuarioSenha.setColumns(10);
		textoUsuarioSenha.setBounds(175, 183, 300, 20);
		getContentPane().add(textoUsuarioSenha);
		
		cboUsuarioPerfil = new JComboBox();
		cboUsuarioPerfil.setModel(new DefaultComboBoxModel(new String[] {"admin", "user"}));
		cboUsuarioPerfil.setBounds(175, 244, 300, 22);
		getContentPane().add(cboUsuarioPerfil);
		
		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTelefone.setBounds(91, 212, 74, 20);
		getContentPane().add(lblTelefone);
		
		textoUsuarioTelefone = new JTextField();
		textoUsuarioTelefone.setColumns(10);
		textoUsuarioTelefone.setBounds(175, 214, 300, 20);
		getContentPane().add(textoUsuarioTelefone);
		
		JLabel lblNewLabel_1 = new JLabel("Dados do Usu\u00E1rio");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1.setBounds(222, 11, 157, 20);
		getContentPane().add(lblNewLabel_1);
		
		JButton botaoUsuarioCreate = new JButton("");
		botaoUsuarioCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// chamando o metodo adicionar
				adicionar();
			}
		});
		botaoUsuarioCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botaoUsuarioCreate.setToolTipText("adicionar");
		botaoUsuarioCreate.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/create.png")));
		botaoUsuarioCreate.setPreferredSize(new Dimension(80, 80));
		botaoUsuarioCreate.setBounds(33, 322, 89, 80);
		getContentPane().add(botaoUsuarioCreate);
		
		JButton botaoUsuarioRead = new JButton("");
		botaoUsuarioRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// chamando o metodo consultar
				consultar();
			}
		});
		botaoUsuarioRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botaoUsuarioRead.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/read.png")));
		botaoUsuarioRead.setToolTipText("pesquisar");
		botaoUsuarioRead.setPreferredSize(new Dimension(80, 80));
		botaoUsuarioRead.setBounds(185, 322, 89, 80);
		getContentPane().add(botaoUsuarioRead);
		
		// chamando o método alterar
		JButton botaoUsuarioUpdate = new JButton("");
		botaoUsuarioUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alterar();
			}
		});
		
		botaoUsuarioUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botaoUsuarioUpdate.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/update.png")));
		botaoUsuarioUpdate.setToolTipText("Atualizar");
		botaoUsuarioUpdate.setPreferredSize(new Dimension(80, 80));
		botaoUsuarioUpdate.setBounds(326, 322, 89, 80);
		getContentPane().add(botaoUsuarioUpdate);
		
		JButton botaoUsuarioDelete = new JButton("");
		botaoUsuarioDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botaoUsuarioDelete.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/delete.png")));
		
		// chamando o método remover
		botaoUsuarioDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				remover();
			}
		});
		
		botaoUsuarioDelete.setToolTipText("apagar");
		botaoUsuarioDelete.setPreferredSize(new Dimension(80, 80));
		botaoUsuarioDelete.setBounds(466, 322, 89, 80);
		getContentPane().add(botaoUsuarioDelete);
		
		JLabel lblNewLabel_2 = new JLabel("CAMPOS OBRIGATORIOS *");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblNewLabel_2.setBounds(452, 13, 162, 20);
		getContentPane().add(lblNewLabel_2);
		
		// conexão com o banco de dados
		conexao = ModuloConexao.conector();
	}
	
	// método para consultar usuarios
	private void consultar() {
		String sql = "select * from tbusuarios where iduser = ?";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1, TextoUsuarioId.getText());
			rs=pst.executeQuery();
			if (rs.next()) {
				textoUsuarioNome.setText(rs.getString(2));
				textoUsuarioTelefone.setText(rs.getString(3));
				textoUsuarioLogin.setText(rs.getString(4));
				textoUsuarioSenha.setText(rs.getString(5));
				// a linha abaixo refere-se ao combo box
				cboUsuarioPerfil.setSelectedItem(rs.getString(6));  
			} else {
				JOptionPane.showMessageDialog(null, "Usuário não cadastrado");
				// as linhas "limpas" os campos
				textoUsuarioNome.setText(null);
				textoUsuarioTelefone.setText(null);
				textoUsuarioLogin.setText(null);
				textoUsuarioSenha.setText(null);
				
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
			
		}
	}
	
	// método para adicionar usuários
	private void adicionar() {
		String sql = "Insert into tbusuarios(iduser,usuario,telefone,login,senha,perfil) values (?,?,?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, TextoUsuarioId.getText());
			pst.setString(2, textoUsuarioNome.getText());
			pst.setString(3, textoUsuarioTelefone.getText());
			pst.setString(4, textoUsuarioLogin.getText());
			pst.setString(5, textoUsuarioSenha.getText());
			pst.setString(6, cboUsuarioPerfil.getSelectedItem().toString());
		// validação dos campos obrigatórios
			if ((TextoUsuarioId.getText().isEmpty()) || (textoUsuarioNome.getText().isEmpty()) || (textoUsuarioLogin.getText().isEmpty()) || (textoUsuarioSenha.getText().isEmpty())){
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {

			
		// a linha abaixo atualiza a tabela usuario com os dados do formulario
		// a estrutura abaixo é usada para confirmar a inserção de dados na tabela
			int adicionado = pst.executeUpdate();
		// a linha abaixo serve de apoio ao entendimento da lógica
		//System.out.println(adicionado);
			if(adicionado > 0) {
				JOptionPane.showMessageDialog(null, "usuário adicionado com sucesso");
				// as linhas "limpas" os campos
				TextoUsuarioId.setText(null);
				textoUsuarioNome.setText(null);
				textoUsuarioTelefone.setText(null);
				textoUsuarioLogin.setText(null);
				textoUsuarioSenha.setText(null);
			}
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			
		}
		
	}
	
	// criando o método para alterar dados do usuário 
	private void alterar() {
		String sql ="update tbusuarios set usuario=?, telefone=?, login=?, senha=?, perfil=? where iduser=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, textoUsuarioNome.getText());
			pst.setString(2, textoUsuarioTelefone.getText());
			pst.setString(3, textoUsuarioLogin.getText());
			pst.setString(4, textoUsuarioSenha.getText());
			pst.setString(5, cboUsuarioPerfil.getSelectedItem().toString());
			pst.setString(6, TextoUsuarioId.getText());
			
			if ((TextoUsuarioId.getText().isEmpty()) || (textoUsuarioNome.getText().isEmpty()) || (textoUsuarioLogin.getText().isEmpty()) || (textoUsuarioSenha.getText().isEmpty())){
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {

			
		// a linha abaixo atualiza a tabela usuario com os dados do formulario
		// a estrutura abaixo é usada para confirmar a alteração de dados na tabela
			int adicionado = pst.executeUpdate();
		// a linha abaixo serve de apoio ao entendimento da lógica
		//System.out.println(adicionado);
			if(adicionado > 0) {
				JOptionPane.showMessageDialog(null, "Dados do usuário alterados com sucesso");
				// as linhas "limpam" os campos
				TextoUsuarioId.setText(null);
				textoUsuarioNome.setText(null);
				textoUsuarioTelefone.setText(null);
				textoUsuarioLogin.setText(null);
				textoUsuarioSenha.setText(null);
			}
			}
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
			
		}
	}
	
	private void remover() {
		// a estrutura abaixo confirma a remoção do usuário
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja removar este usuário ?", " Atencão", JOptionPane.YES_NO_OPTION);
		if(confirma == JOptionPane.YES_OPTION) {
			String sql = "delete from tbusuarios where iduser=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, TextoUsuarioId.getText());
				int apagado = pst.executeUpdate();
				if(apagado > 0) {
					JOptionPane.showMessageDialog(null, "usuário Removido com sucesso");
					// as linhas "limpam" os campos
					TextoUsuarioId.setText(null);
					textoUsuarioNome.setText(null);
					textoUsuarioTelefone.setText(null);
					textoUsuarioLogin.setText(null);
					textoUsuarioSenha.setText(null);
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
				e.printStackTrace();
				
			}
		}
	}
}


















