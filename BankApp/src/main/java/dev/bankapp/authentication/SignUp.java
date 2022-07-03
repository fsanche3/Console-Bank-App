package dev.bankapp.authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class SignUp {

	String username1;
	String password1;
	java.sql.Statement statement = null;

	public boolean signUserUp(Scanner scan) {

		boolean pass = false;

		System.out.println();
		System.out.print("Enter your new username: ");
		username1 = scan.next();
		System.out.print("Enter your new password: ");
		password1 = scan.next();
		System.out.println();

		String jbdcURL = System.getenv("DB_URL");
		String username = System.getenv("DB_USER");
		String password = System.getenv("DB_PASS");

		try {
			Connection connection = DriverManager.getConnection(jbdcURL, username, password);

			PreparedStatement ps;
			String sql = "select username from bankuser where username = "+"'"+username1+"'"+"; ";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			if (rs.next() == false) {
				User user = new User(username1, password1);
				sql = "insert into bankuser(username, password) values(" + "'" + username1 + "'" + "," + "'" + password1
						+ "'" + ");";
				statement = connection.createStatement();
				statement.executeUpdate(sql);
				pass = true;
				System.out.println("*******************************************");
				System.out.println();
				System.out.println("Your account has been created!");
				System.out.println();
				System.out.println("*******************************************");
				System.out.println();


			} else {
				System.out.print("This Username is already being used!");
				System.out.println();
			}

			connection.close();

		} catch (SQLException e) {
			System.out.print("DB Connection Error");
			e.printStackTrace();
		}

		return pass;
	}

}
