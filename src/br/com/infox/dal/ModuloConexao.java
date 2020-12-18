package br.com.infox.dal;

import java.sql.*;

import javax.swing.JOptionPane;

public class ModuloConexao {

	// m�todo rensponsavel por estabelecer a conex�o com o banco
	public static Connection conector() {
		
		java.sql.Connection conexao = null;
		
		// a linha abaixo "chama" o driver
		String driver = "com.mysql.cj.jdbc.Driver";
		
		// armazenado informa��es referente ao banco
		String url = "jdbc:mysql://localhost:3306/dbinfox?serverTimezone=UTC";
		String user = "root";
		String password = "";
		
		// Estabelecendo a conexao com o banco
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, user, password);
			return conexao;
		} catch (Exception e) {
			//A linha abaixo serve para esclarecer o erro
			//System.out.println(e);
			return null;
		}
		
	}
	
}
