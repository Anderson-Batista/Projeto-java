package br.com.infox.telas;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.JProgressBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
// a linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.ComponentOrientation;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import br.com.infox.telas.*;

public class TelaCliente extends JInternalFrame {
	private JTextField textoClienteNome;
	private JTextField textoClienteEndereco;
	private JTextField textoClienteTelefone;
	private JTextField textoClienteEmail;
	private JTextField textoClientePesquisar;
	private JTable tabelaCliente = new JTable();
	private JTextField textoClienteId;
	private JButton botaoAdicionar;
	
	
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCliente frame = new TelaCliente();
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
	public TelaCliente() {
		conexao = ModuloConexao.conector();
		
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setTitle("Clientes");
		setBounds(0, 0, 640, 480);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("* Campos obrigat\u00F3rios");
		lblNewLabel.setBounds(299, 11, 125, 26);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblNewLabel);
		
		JLabel lblNome = new JLabel("* Nome");
		lblNome.setBounds(23, 161, 80, 26);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblNome);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setBounds(23, 198, 98, 26);
		lblEndereo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblEndereo);
		
		JLabel lbltelefone = new JLabel("*Telefone");
		lbltelefone.setBounds(23, 235, 135, 26);
		lbltelefone.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lbltelefone);
		
		JLabel lblemail = new JLabel("Email");
		lblemail.setBounds(23, 272, 135, 26);
		lblemail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblemail);
		
		textoClienteNome = new JTextField();
		textoClienteNome.setBounds(113, 165, 301, 20);
		getContentPane().add(textoClienteNome);
		textoClienteNome.setColumns(10);
		
		textoClienteEndereco = new JTextField();
		textoClienteEndereco.setBounds(113, 202, 301, 20);
		textoClienteEndereco.setColumns(10);
		getContentPane().add(textoClienteEndereco);
		
		textoClienteTelefone = new JTextField();
		textoClienteTelefone.setBounds(113, 239, 188, 20);
		textoClienteTelefone.setColumns(10);
		getContentPane().add(textoClienteTelefone);
		
		textoClienteEmail = new JTextField();
		textoClienteEmail.setBounds(113, 272, 301, 20);
		textoClienteEmail.setColumns(10);
		getContentPane().add(textoClienteEmail);
		
		JButton botaoRemover = new JButton("");
		botaoRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// chamando o método para remover um cliente
				remover();
			}
		});
		botaoRemover.setBounds(383, 348, 89, 80);
		botaoRemover.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/delete.png")));
		botaoRemover.setToolTipText("remover");
		botaoRemover.setPreferredSize(new Dimension(80, 80));
		getContentPane().add(botaoRemover);
		
		JButton botaoAlterar = new JButton("");
		botaoAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// chamando o método para alterar clientes
				alterar();
			}
		});
		botaoAlterar.setBounds(213, 348, 89, 80);
		botaoAlterar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/update.png")));
		botaoAlterar.setToolTipText("alterar");
		botaoAlterar.setPreferredSize(new Dimension(80, 80));
		getContentPane().add(botaoAlterar);
		
		botaoAdicionar = new JButton("");
		botaoAdicionar.setBounds(51, 348, 89, 80);
		botaoAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// metodo para adicionar clientes
				adicionar();
			}
		});
		botaoAdicionar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/create.png")));
		botaoAdicionar.setToolTipText("adicionar");
		botaoAdicionar.setPreferredSize(new Dimension(80, 80));
		getContentPane().add(botaoAdicionar);
		
		textoClientePesquisar = new JTextField();
		textoClientePesquisar.setBounds(10, 15, 226, 20);
		//o evento abaixo é do tipo "enquanto for digitado"
		textoClientePesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				// chamar o método pesquisarClientes
				pesquisarCleinte();
			}
		});
		getContentPane().add(textoClientePesquisar);
		textoClientePesquisar.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(246, 0, 37, 41);
		lblNewLabel_1.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/search.png")));
		getContentPane().add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 402, 89);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(tabelaCliente);
		tabelaCliente.addMouseListener(new MouseAdapter() {
			// evento que será usado para setar os campos da tabela (clicando com o mouse)
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//chamando o método para setar os campos
				setarCampos();
			}
		});
		tabelaCliente.setName("");
		tabelaCliente.setCellSelectionEnabled(true);
		tabelaCliente.setSurrendersFocusOnKeystroke(true);
		tabelaCliente.setColumnSelectionAllowed(true);
		tabelaCliente.setFillsViewportHeight(true);
		tabelaCliente.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		
		JLabel lblIdcliente = new JLabel("Id cliente");
		lblIdcliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIdcliente.setBounds(20, 134, 80, 26);
		getContentPane().add(lblIdcliente);
		
		textoClienteId = new JTextField();
		textoClienteId.setEnabled(false);
		textoClienteId.setColumns(10);
		textoClienteId.setBounds(113, 134, 72, 20);
		getContentPane().add(textoClienteId);
		
		
		

	}
	
	// método para adicionar clientes
		private void adicionar() {
			String sql = "Insert into tbclientes(nomecliente,enderecocliente,telefonecliente,emailcliente) values (?,?,?,?)";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, textoClienteNome.getText());
				pst.setString(2, textoClienteEndereco.getText());
				pst.setString(3, textoClienteTelefone.getText());
				pst.setString(4, textoClienteEmail.getText());
			// validação dos campos obrigatórios
				if ((textoClienteNome.getText().isEmpty()) || (textoClienteTelefone.getText().isEmpty()) || (textoClienteEmail.getText().isEmpty())){
					JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
				} else {

				
			// a linha abaixo atualiza a tabela usuario com os dados do formulario
			// a estrutura abaixo é usada para confirmar a inserção de dados na tabela
				int adicionado = pst.executeUpdate();
			// a linha abaixo serve de apoio ao entendimento da lógica
			//System.out.println(adicionado);
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso");
					// as linhas "limpas" os campos
					textoClienteNome.setText(null);
					textoClienteEndereco.setText(null);
					textoClienteTelefone.setText(null);
					textoClienteEmail.setText(null);
					
				}
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
				
			}
			
		}
		// método para pesquisar clientes pelo nome com filtro
		
		private void pesquisarCleinte() {
			String sql = "select * from tbclientes where nomecliente like ?";
			try {
				pst = conexao.prepareStatement(sql);
				// passando o conteúdo da caixa de pesquisa para o ?
				// atençao ao "%" - continuação da string sql reposável pela busca avançada
				pst.setString(1, textoClientePesquisar.getText() + "%");
				rs = pst.executeQuery();
				// a linha abaixo usa a biblioteca rs2xml.jar para preecher a tabela
				tabelaCliente.setModel(DbUtils.resultSetToTableModel(rs));
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
		// método para setar os campos do formulário com o conteúdo da tabela
		public void setarCampos() {
			
			
			int setar = tabelaCliente.getSelectedRow();
			textoClienteId.setText(tabelaCliente.getModel().getValueAt(setar, 0).toString());
			textoClienteNome.setText(tabelaCliente.getModel().getValueAt(setar, 1).toString());
			textoClienteEndereco.setText(tabelaCliente.getModel().getValueAt(setar, 2).toString());
			textoClienteTelefone.setText(tabelaCliente.getModel().getValueAt(setar, 3).toString());
			textoClienteEmail.setText(tabelaCliente.getModel().getValueAt(setar, 4).toString());
			
			// a linha abaixo desabilita o botao adicionar
			botaoAdicionar.setEnabled(false);
		}
		
		// criando o método para alterar dados do cliente 
		private void alterar() {
			String sql ="update tbclientes set nomecliente=?, enderecocliente=?, telefonecliente=?, emailcliente=? where idcliente=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, textoClienteNome.getText());
				pst.setString(2, textoClienteEndereco.getText());
				pst.setString(3, textoClienteTelefone.getText());
				pst.setString(4, textoClienteEmail.getText());
				pst.setString(5, textoClienteId.getText());
				
				if ((textoClienteNome.getText().isEmpty()) || (textoClienteTelefone.getText().isEmpty())){
					JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
				} else {

				
			// a linha abaixo atualiza a tabela usuario com os dados do formulario
			// a estrutura abaixo é usada para confirmar a alteração de dados na tabela
				int adicionado = pst.executeUpdate();
			// a linha abaixo serve de apoio ao entendimento da lógica
			//System.out.println(adicionado);
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso");
					
					// as linhas "limpam" os campos
					
					textoClienteNome.setText(null);
					textoClienteEndereco.setText(null);
					textoClienteTelefone.setText(null);
					textoClienteEmail.setText(null);
					botaoAdicionar.setEnabled(true);
				}
				}
				
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
				e.printStackTrace();
				
			}
		}
		
		// a estrutura abaixo confirma a remoção do cliente
		
		private void remover() {
			
			int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja removar este cliente ?", " Atencão", JOptionPane.YES_NO_OPTION);
			if(confirma == JOptionPane.YES_OPTION) {
				String sql = "delete from tbclientes where idcliente=?";
				try {
					pst = conexao.prepareStatement(sql);
					pst.setString(1, textoClienteId.getText());
					int apagado = pst.executeUpdate();
					if(apagado > 0) {
						JOptionPane.showMessageDialog(null, "Cliente Removido com sucesso");
						// as linhas "limpam" os campos
						textoClienteNome.setText(null);
						textoClienteEndereco.setText(null);
						textoClienteTelefone.setText(null);
						textoClienteEmail.setText(null);
						botaoAdicionar.setEnabled(true);
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
					e.printStackTrace();
					
				}
			}
		}
}
















