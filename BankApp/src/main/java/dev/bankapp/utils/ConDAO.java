package dev.bankapp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConDAO {

	private static String jbdcURL = System.getenv("DB_URL");
	private static String username = System.getenv("DB_USER");
	private static String password = System.getenv("DB_PASS");
	private static ConDAO con;
	public ConDAO() {}
	
	public static synchronized ConDAO getConDAO() {
		if(con == null) {
			con = new ConDAO();
		}
		return con;
	}
	public Connection getConnection() throws SQLException{
		Connection con = null;
		con = DriverManager.getConnection(jbdcURL, username, password);
		return con;
	}
}
