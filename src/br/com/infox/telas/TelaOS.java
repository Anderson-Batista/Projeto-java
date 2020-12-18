package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Panel;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Cursor;

import java.sql.*;
import java.util.HashMap;

import br.com.infox.dal.ModuloConexao;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class TelaOS extends JInternalFrame {
	private JTextField textoOs;
	private JTextField textoData;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JTextField textoClientePesquisar;
	private JTextField textoClienteId;
	private JTable tabelaClientes;
	private JTextField textoOsEquipamento;
	private JTextField TextoOsDefeito;
	private JTextField textoOsServico;
	private JTextField textoOsTecnico;
	private JTextField textoOsValor;
	private JRadioButton rbtOrcamento;
	private JComboBox cboOsSituacao;
	private JRadioButton rbtOs;
	private JButton botaoOsAdicionar;
	
	
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	// a linha abaixo cria uma variável para adicionar otexto de acordo com o radion button selecionado
	private String tipo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaOS frame = new TelaOS();
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
	public TelaOS() {
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {
				// Ao abrir o form marcar o radion button orçamento
				rbtOrcamento.setSelected(true);
				tipo = "Orçamento";
			}
		});
		
		conexao = ModuloConexao.conector();
		
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		setTitle("OS");
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setBounds(0, 0, 640, 480);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(22, 11, 245, 105);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("N\u00B0 OS");
		lblNewLabel.setBounds(10, 11, 51, 24);
		panel.add(lblNewLabel);
		
		JLabel lblData = new JLabel("Data");
		lblData.setBounds(96, 11, 51, 24);
		panel.add(lblData);
		
		textoOs = new JTextField();
		textoOs.setEditable(false);
		textoOs.setBounds(10, 46, 63, 20);
		panel.add(textoOs);
		textoOs.setColumns(10);
		
		textoData = new JTextField();
		textoData.setFont(new Font("Tahoma", Font.BOLD, 10));
		textoData.setEditable(false);
		textoData.setColumns(10);
		textoData.setBounds(96, 46, 143, 20);
		panel.add(textoData);
		
		rbtOrcamento = new JRadioButton("Or\u00E7amento");
		rbtOrcamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// atribuindo um texto a varivel tipo se selecionado
				tipo = "Orçamento";
			}
		});
		buttonGroup_1.add(rbtOrcamento);
		rbtOrcamento.setBounds(6, 73, 109, 23);
		panel.add(rbtOrcamento);
		
		rbtOs = new JRadioButton("OS");
		rbtOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// a linha abaixo atribui um texto a variável tipo se selecionada
				tipo = "OS";
			}
		});
		buttonGroup_1.add(rbtOs);
		rbtOs.setBounds(130, 73, 109, 23);
		panel.add(rbtOs);
		
		JLabel lblNewLabel_1 = new JLabel("Situa\u00E7\u00E3o");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(22, 123, 55, 26);
		getContentPane().add(lblNewLabel_1);
		
		cboOsSituacao = new JComboBox();
		cboOsSituacao.setModel(new DefaultComboBoxModel(new String[] {"Na Bancada", "Entrega OK", "Or\u00E7amento REPROVADO", "Aguardando Aprova\u00E7\u00F5es", "Aguardando Pe\u00E7as", "Abandonado Pelo Cliente", "Retornou"}));
		cboOsSituacao.setBounds(81, 127, 186, 20);
		getContentPane().add(cboOsSituacao);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(277, 11, 337, 170);
		getContentPane().add(panel_1);
		
		textoClientePesquisar = new JTextField();
		textoClientePesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				// chamando o método pesquisar cliente
				pesquisarCliente();
			}
		});
		textoClientePesquisar.setBounds(10, 30, 180, 20);
		panel_1.add(textoClientePesquisar);
		textoClientePesquisar.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/search.png")));
		lblNewLabel_2.setBounds(200, 18, 32, 32);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("*Id");
		lblNewLabel_3.setBounds(239, 30, 46, 20);
		panel_1.add(lblNewLabel_3);
		
		textoClienteId = new JTextField();
		textoClienteId.setBounds(267, 30, 60, 20);
		panel_1.add(textoClienteId);
		textoClienteId.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 317, 98);
		panel_1.add(scrollPane);
		
		tabelaClientes = new JTable();
		tabelaClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// chamando o método setar campos
				setarCampos();
			}
		});
		scrollPane.setViewportView(tabelaClientes);
		tabelaClientes.setModel(new DefaultTableModel(
			new Object[][] {
				{null, "", null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Id", "Nome", "Telefone"
			}
		));
		
		JLabel lblequipamento = new JLabel("*Equipamento");
		lblequipamento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblequipamento.setBounds(22, 188, 79, 26);
		getContentPane().add(lblequipamento);
		
		JLabel lbldefeito = new JLabel("*Defeito");
		lbldefeito.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbldefeito.setBounds(22, 214, 55, 26);
		getContentPane().add(lbldefeito);
		
		JLabel lblServio = new JLabel("Servi\u00E7o");
		lblServio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblServio.setBounds(22, 237, 55, 26);
		getContentPane().add(lblServio);
		
		JLabel lblTcnico = new JLabel("T\u00E9cnico");
		lblTcnico.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTcnico.setBounds(22, 262, 55, 26);
		getContentPane().add(lblTcnico);
		
		textoOsEquipamento = new JTextField();
		textoOsEquipamento.setBounds(111, 192, 452, 20);
		getContentPane().add(textoOsEquipamento);
		textoOsEquipamento.setColumns(10);
		
		TextoOsDefeito = new JTextField();
		TextoOsDefeito.setColumns(10);
		TextoOsDefeito.setBounds(111, 218, 452, 20);
		getContentPane().add(TextoOsDefeito);
		
		textoOsServico = new JTextField();
		textoOsServico.setColumns(10);
		textoOsServico.setBounds(111, 241, 452, 20);
		getContentPane().add(textoOsServico);
		
		textoOsTecnico = new JTextField();
		textoOsTecnico.setColumns(10);
		textoOsTecnico.setBounds(111, 266, 184, 20);
		getContentPane().add(textoOsTecnico);
		
		JLabel lblValorTotal = new JLabel("Valor Total");
		lblValorTotal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblValorTotal.setBounds(305, 262, 64, 26);
		getContentPane().add(lblValorTotal);
		
		textoOsValor = new JTextField();
		textoOsValor.setText("0");
		textoOsValor.setColumns(10);
		textoOsValor.setBounds(379, 266, 184, 20);
		getContentPane().add(textoOsValor);
		
		botaoOsAdicionar = new JButton("");
		botaoOsAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// chamar o método emitir os
				emitirOs();
			}
		});
		botaoOsAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botaoOsAdicionar.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/create.png")));
		botaoOsAdicionar.setToolTipText("adicionar");
		botaoOsAdicionar.setPreferredSize(new Dimension(80, 80));
		botaoOsAdicionar.setBounds(79, 337, 89, 80);
		getContentPane().add(botaoOsAdicionar);
		
		JButton botaoOsPesquisar = new JButton("");
		botaoOsPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//chamando o método pesquisar OS
				pesquisarOs();
			}
		});
		botaoOsPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botaoOsPesquisar.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/read.png")));
		botaoOsPesquisar.setToolTipText("pesquisar");
		botaoOsPesquisar.setPreferredSize(new Dimension(80, 80));
		botaoOsPesquisar.setBounds(178, 337, 89, 80);
		getContentPane().add(botaoOsPesquisar);
		
		JButton botaoOsAlterar = new JButton("");
		botaoOsAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// chamando alterarOS
				alterarOs();
			}
		});
		botaoOsAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botaoOsAlterar.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/update.png")));
		botaoOsAlterar.setToolTipText("alterar");
		botaoOsAlterar.setPreferredSize(new Dimension(80, 80));
		botaoOsAlterar.setBounds(280, 337, 89, 80);
		getContentPane().add(botaoOsAlterar);
		
		JButton botaoOsRemover = new JButton("");
		botaoOsRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//chamando o método para excluir uma os
				excluirOs();
			}
		});
		botaoOsRemover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botaoOsRemover.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/delete.png")));
		botaoOsRemover.setToolTipText("excluir");
		botaoOsRemover.setPreferredSize(new Dimension(80, 80));
		botaoOsRemover.setBounds(379, 337, 89, 80);
		getContentPane().add(botaoOsRemover);
		
		JButton botaoOsImprimir = new JButton("");
		botaoOsImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// chamando o método para imprimir uma os
				imprimirOs();
			}
		});
		botaoOsImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botaoOsImprimir.setToolTipText("Imprimir OS");
		botaoOsImprimir.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/print.png")));
		botaoOsImprimir.setBounds(478, 337, 80, 80);
		getContentPane().add(botaoOsImprimir);

	}
	
	private void pesquisarCliente() {
		String sql = "select idcliente as Id, nomecliente as Nome, telefonecliente as Telefone from tbclientes where nomecliente like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, textoClientePesquisar.getText() + "%");
			rs = pst.executeQuery();
			tabelaClientes.setModel(DbUtils.resultSetToTableModel(rs));
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	
	private void setarCampos() {
		int setar = tabelaClientes.getSelectedRow();
		textoClienteId.setText(tabelaClientes.getModel().getValueAt(setar, 0).toString());
	}
	
	// método para cadastrar uma OS
	private void emitirOs() {
		String sql = "insert into tbos(tipo,situacao,equipamento,defeito,servico,tecnico,valor,idos) values(?,?,?,?,?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, tipo);
			pst.setString(2, cboOsSituacao.getSelectedItem().toString());
			pst.setString(3, textoOsEquipamento.getText());
			pst.setString(4, TextoOsDefeito.getText());
			pst.setString(5, textoOsServico.getText());
			pst.setString(6, textoOsTecnico.getText());
			//.replace substitui a vírgula pelo ponto
			pst.setString(7, textoOsValor.getText().replace(",", "."));
			pst.setString(8, textoClienteId.getText());
			
			// validação dos campos obrigatórios
			if ((textoClienteId.getText().isEmpty()) || (textoOsEquipamento.getText().isEmpty()) || (TextoOsDefeito.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
			} else {
				int adicionado = pst.executeUpdate();
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Ordem de serviço emitida com sucesso");
					
					textoClienteId.setText(null);
					textoOsEquipamento.setText(null);
					TextoOsDefeito.setText(null);
					textoOsServico.setText(null);
					textoOsTecnico.setText(null);
					textoOsValor.setText(null);
				}
			}
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	// método para pesquisar OS
	private void pesquisarOs() {
		// a linha abaixo cria uma caixa de entrada JOption pane
		String numeroOs = JOptionPane.showInputDialog("Número da Os");
		String sql = "select * from tbos where os = " + numeroOs;
		try {
			pst=conexao.prepareStatement(sql);
			rs=pst.executeQuery();
			if (rs.next()) {
				textoOs.setText(rs.getString(1));
				textoData.setText(rs.getString(2));
				// setando os radios buttons
				String rbtTipo=rs.getString(3);
				if(rbtTipo.equals("OS")) {
					rbtOs.setSelected(true);
					tipo = "OS";
				}else {
					rbtOrcamento.setSelected(true);
					tipo = "Orçamento";
				}
				cboOsSituacao.setSelectedItem(rs.getString(4));
				textoOsEquipamento.setText(rs.getString(5));
				TextoOsDefeito.setText(rs.getString(6));
				textoOsServico.setText(rs.getString(7));
				textoOsTecnico.setText(rs.getString(8));
				textoOsValor.setText(rs.getString(9));
				textoClienteId.setText(rs.getString(10));
				// evitadno problemas
				botaoOsAdicionar.setEnabled(false);
				textoClientePesquisar.setEnabled(false);
				tabelaClientes.setVisible(false);
				
			} else {
				JOptionPane.showMessageDialog(null, "OS não cadastrada");
			}
		} catch (java.sql.SQLSyntaxErrorException e) {
			JOptionPane.showMessageDialog(null, "Os inválida");
			//System.out.println(e);
		}catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
		
	}
	
//método para alterar uma OS
	
	private void alterarOs() {
		String sql = "update tbos set tipo=?, situacao=?, equipamento=?, defeito=?,servico=?,tecnico=?,valor=? where os=?";
		
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, tipo);
			pst.setString(2, cboOsSituacao.getSelectedItem().toString());
			pst.setString(3, textoOsEquipamento.getText());
			pst.setString(4, TextoOsDefeito.getText());
			pst.setString(5, textoOsServico.getText());
			pst.setString(6, textoOsTecnico.getText());
			//.replace substitui a vírgula pelo ponto
			pst.setString(7, textoOsValor.getText().replace(",", "."));
			pst.setString(8, textoOs.getText());
			
			// validação dos campos obrigatórios
			if ((textoClienteId.getText().isEmpty()) || (textoOsEquipamento.getText().isEmpty()) || (TextoOsDefeito.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
			} else {
				int adicionado = pst.executeUpdate();
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Ordem de serviço alterada com sucesso");
					
					textoOs.setText(null);
					textoData.setText(null);
					textoClienteId.setText(null);
					textoOsEquipamento.setText(null);
					TextoOsDefeito.setText(null);
					textoOsServico.setText(null);
					textoOsTecnico.setText(null);
					textoOsValor.setText(null);
					// habilitar os objetos
					botaoOsAdicionar.setEnabled(true);
					textoClientePesquisar.setEnabled(true);
					tabelaClientes.setEnabled(true);
					
				}
			}
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
// método para excluir uma Os
	
	private void excluirOs() {
		int confirma=JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta Ordem de Serviço", "Atenção", JOptionPane.YES_NO_OPTION);
		if(confirma == JOptionPane.YES_OPTION) {
			String sql = "delete from tbos where os=?";
			try {
				pst=conexao.prepareStatement(sql);
				pst.setString(1, textoOs.getText());
				int apagado = pst.executeUpdate();
				if(apagado > 0) {
					JOptionPane.showMessageDialog(null, "Ordem de srviço excluída com sucesso");
					
					textoOs.setText(null);
					textoData.setText(null);
					textoClienteId.setText(null);
					textoOsEquipamento.setText(null);
					TextoOsDefeito.setText(null);
					textoOsServico.setText(null);
					textoOsTecnico.setText(null);
					textoOsValor.setText(null);
					// habilitar os objetos
					botaoOsAdicionar.setEnabled(true);
					textoClientePesquisar.setEnabled(true);
					tabelaClientes.setEnabled(true);
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	// método para imprimir uma OS
	private void imprimirOs() {
		
		// imprimindo uma OS
		
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desta OS?", "Atenção",JOptionPane.YES_NO_OPTION);
		if(confirma == JOptionPane.YES_OPTION) {
			
			//imprimindo relatório com o framework JasperReports
			try {
				// usando a classe HashMap para criar um filtro
				HashMap filtro = new HashMap();
				filtro.put("os",Integer.parseInt(textoOs.getText()));
				// Usando a classe JasperPrint para preparar a impressão de um relatório
				JasperPrint print = JasperFillManager.fillReport("C:/reports/os.jasper", filtro, conexao);
				
				// a linha abaixo exibe o relatório através da classe JasperViewer
				JasperViewer.viewReport(print,false);
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
}







