package br.com.infox.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.DesktopPaneUI;

import java.awt.GridLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JDesktopPane;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;

import javax.swing.border.BevelBorder;
import java.awt.Dimension;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import br.com.infox.dal.ModuloConexao;

import com.jgoodies.forms.layout.FormSpecs;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.SpringLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;


public class TelaPrincipal extends JFrame {
	
	public static JMenu menuRelatorio;
	public static JMenuItem menuCadastroUsuarios;
	public static JLabel lblUsuario;
	public static JDesktopPane desktop;
	
	Connection conexao = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
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
	public TelaPrincipal() {
		
		conexao = ModuloConexao.conector();
		
		setTitle("Sistema Para controle de OS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 536);
		
		
		JMenuBar Menu = new JMenuBar();
		setJMenuBar(Menu);
		
		JMenu menuCadastro = new JMenu("Cadastro");
		Menu.add(menuCadastro);
		
		JMenuItem menuCadastroOs = new JMenuItem("OS");
		menuCadastroOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// chamando a tela Os
				TelaOS os = new TelaOS();
				os.setVisible(true);
				desktop.add(os);
			}
		});
		menuCadastro.add(menuCadastroOs);
		
		menuCadastroUsuarios = new JMenuItem("Usuarios");
		menuCadastroUsuarios.setEnabled(false);
		menuCadastro.add(menuCadastroUsuarios);
		
		menuRelatorio = new JMenu("Relat\u00F3rio");
		menuRelatorio.setEnabled(false);
		Menu.add(menuRelatorio);
		
		JMenuItem menuRelatorioCliente = new JMenuItem("Clientes");
		menuRelatorioCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// gerando um relatório de clientes
				int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão deste relatório?", "Atenção",JOptionPane.YES_NO_OPTION);
				if(confirma == JOptionPane.YES_OPTION) {
					//imprimindo relatório com o framework JasperReports
					try {
						// Usando a classe JasperPrint para preparar a impressão de um relatório
						JasperPrint print = JasperFillManager.fillReport("C:/reports/clientes.jasper", null, conexao);
						
						// a linha abaixo exibe o relatório através da classe JasperViewer
						JasperViewer.viewReport(print,false);
						
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					}
				}
			}
		});
		menuRelatorio.add(menuRelatorioCliente);
		
		JMenuItem menuRelatorioServicos = new JMenuItem("Servi\u00E7os");
		menuRelatorioServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// gerando um relatório de serviços
				int confirma = JOptionPane.showConfirmDialog(null, "Confirma a emissão deste relatório?", "Atenção",JOptionPane.YES_NO_OPTION);
				if(confirma == JOptionPane.YES_OPTION) {
					//imprimindo o relatório com o framework JasperReports
					try {
						// Usando a classe JasperPrint para preparar a impressão de um relatório
						JasperPrint print = JasperFillManager.fillReport("C:/reports/servicos.jasper", null, conexao);
						
						// a linha abaixo exibe o relatório através da classe JasperViewer
						JasperViewer.viewReport(print,false);
						
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					}
				}
			}
		});
		menuRelatorio.add(menuRelatorioServicos);
		
		JMenu menuAjuda = new JMenu("Ajuda");
		Menu.add(menuAjuda);
		
		JMenuItem menuAjudaSobre = new JMenuItem("Sobre");
		menuAjudaSobre.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// chamando a tela sobre
				TelaSobre sobre  = new TelaSobre();
				sobre.setVisible(true);
			}
		});
		
		menuAjuda.add(menuAjudaSobre);
		
		JMenu menuOpcoes = new JMenu("Op\u00E7\u00F5es");
		Menu.add(menuOpcoes);
		
		JMenuItem menuOpcoesSair = new JMenuItem("Sair");
		menuOpcoesSair.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// exibe uma caixa de diálogo
				int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção", JOptionPane.YES_NO_OPTION);
				if(sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		
		menuOpcoes.add(menuOpcoesSair);
		getContentPane().setLayout(null);
		
		desktop = new JDesktopPane();
		desktop.setBackground(Color.DARK_GRAY);
		desktop.setBounds(0, 0, 640, 480);
		getContentPane().add(desktop);
		
		JLabel lblIcone = new JLabel("");
		lblIcone.setIcon(new ImageIcon("C:\\Users\\Anderson\\Pictures\\Saved Pictures\\índice.png"));
		lblIcone.setBounds(644, 259, 264, 221);
		getContentPane().add(lblIcone);
		
		lblUsuario = new JLabel("Usu\u00E1rio");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblUsuario.setBounds(644, 31, 230, 28);
		getContentPane().add(lblUsuario);
		
		JLabel lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblData.setBounds(644, 100, 230, 28);
		getContentPane().add(lblData);
		
		// código que define ação na tela quando ele surge
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				// as linhas abaixo substituem a label lblData pela data atual do
				// sistema ao iniciar o form
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
				lblData.setText(formatador.format(data));
				
			}
		});
		
		JMenuItem menuCadastroCliente = new JMenuItem("Cliente");
		menuCadastroCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// chamando a tela cliente
				TelaCliente cliente = new TelaCliente();
				cliente.setVisible(true);
				desktop.add(cliente);
			}
		});
		menuCadastro.add(menuCadastroCliente);
		
			menuCadastroUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// as linha abaixo vão abrir o form TelaUsuario dentro do desktop pane
				TelaUsuario usuario = new TelaUsuario();
				usuario.setVisible(true);
				desktop.add(usuario);
			}
		});
		
		}
	
	
	
	public boolean getMenuRelatorioEnabled() {
		return menuRelatorio.isEnabled();
	}
	public void setMenuRelatorioEnabled(boolean enabled) {
		menuRelatorio.setEnabled(enabled);
	}
	public boolean getMenuCadastroUsuariosEnabled() {
		return menuCadastroUsuarios.isEnabled();
	}
	public void setMenuCadastroUsuariosEnabled(boolean enabled_1) {
		menuCadastroUsuarios.setEnabled(enabled_1);
	}
	public boolean getLblUsuarioEnabled() {
		return lblUsuario.isEnabled();
	}
	public void setLblUsuarioEnabled(boolean enabled_2) {
		lblUsuario.setEnabled(enabled_2);
	}
}
