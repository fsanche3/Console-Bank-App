package dev.bankapp.authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dev.bankapp.utils.ConDAO;

public class Login {


	String username1;
	String password1;
	List<String> cred = new ArrayList<>();
	java.sql.Statement statement = null;
	ConDAO con = new ConDAO();

	public boolean login(Scanner scan) {

		boolean pass = false;

		System.out.println();
		System.out.print("Enter your username: ");
		this.username1 = scan.next();
		System.out.print("Enter your password: ");
		this.password1 = scan.next();
		System.out.println();

		try {
			Connection connection = con.getConnection();

			PreparedStatement ps;
			String sql = "select username,password from bankuser where username = " + "'" + username1 + "'"
					+ " and password = " + "'" + password1 + "'" + "  ;";
			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			if (rs.next() == true) {
				pass = true;
			}
			connection.close();

		} catch (SQLException e) {
			System.out.print("DB Connection Error");
			e.printStackTrace();
		}

		return pass;
	}


	public List<String> getUser() {

		this.cred.add(0, username1);
		this.cred.add(1, password1);

		return this.cred;
	}


}
