package dev.bankapp.authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import dev.bankapp.utils.ConDAO;
import dev.bankapp.utils.Logger;
import dev.bankapp.utils.LoggingLevels;


public class SignUp {

	String username1;
	String password1;
	java.sql.Statement statement = null;
	ConDAO con = new ConDAO();
	Logger logger = Logger.getLogger();

	public boolean signUserUp(Scanner scan) {

		boolean pass = false;

		System.out.println();
		System.out.print("Enter your new username: ");
		username1 = scan.next();
		System.out.print("Enter your new password: ");
		password1 = scan.next();
		System.out.println();

		try {
			Connection connection = con.getConnection();
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
				logger.log("User failed to sign up (Username already taken)", LoggingLevels.TRACE);
				System.out.print("This Username is already being used!");
				System.out.println();
			}

			connection.close();

		} catch (SQLException e) {
			logger.log("DB Connection Error", LoggingLevels.ERROR);
			e.printStackTrace();
		}

		return pass;
	}

}
