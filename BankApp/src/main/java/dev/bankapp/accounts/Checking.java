package dev.bankapp.accounts;

import java.sql.Statement;
import java.text.DecimalFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import dev.bankapp.authentication.User;
import dev.bankapp.utils.ConDAO;

public class Checking extends Account {

	User user;
	int accountnum;
	java.sql.Statement statement = null;
	double totalBalance = 0.0;
	ConDAO con = new ConDAO();

	public Checking() {
	}

	public Checking(User user) {
		this.user = user;
	}

	@Override
	public boolean deposit(Scanner scan) {

		boolean deposited = true;
		// Account number validation
		System.out.print("Enter Account number: ");
		int accountnum = scan.nextInt();
		System.out.println();
		System.out.print("Enter amount you want deposited: ");
		double amount = scan.nextDouble();
		if (amount < 0) {
			System.out.println("Negative deposits arent allowed");
			System.out.print("Enter amount you want deposited: ");
			amount = scan.nextDouble();
		}

		try {
			Connection connection = con.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement ps;
			String sql1 = "select balance from checkings where accountnum = " + "'" + accountnum + "'" + ";";
			ps = connection.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == false) {
				System.out.println();
				System.out.println("The number entered is not associated with a Checkings account number");
				System.out.println();
				deposited = false;
				return deposited;
			} else {
				totalBalance = rs.getDouble("balance");
			}
			DecimalFormat df = new DecimalFormat(".00");

			totalBalance = totalBalance + amount;
			String sql = "update checkings set balance = balance + " + df.format(amount) + " where accountnum = " + "'"
					+ accountnum + "'" + " ;";
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			System.out.println();
			System.out.println("*******************************************");
			System.out.println();
			user.addToList("Deposited $" + df.format(amount) + " into Checkings Account# " + accountnum + "");
			connection.commit();
			System.out.println("Amount deposited: $" + df.format(amount) + "");
			System.out.println("New Balance for Account #" + accountnum + ": $" + df.format(totalBalance) + "");
			System.out.println();
			System.out.println("*******************************************");
			System.out.println();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deposited;
	}

	@Override
	public boolean withdrawl(Scanner scan) {
		boolean withdrawn = true;
		System.out.print("Enter Account number: ");
		int accountnum = scan.nextInt();
		System.out.println();
		System.out.print("Enter amount you want Withdrawn: ");
		double amount = scan.nextDouble();
		if (amount < 0) {
			System.out.println("Please enter a positive number");
			System.out.print("Enter amount you want Withdrawn: ");
			amount = scan.nextDouble();
		}

		try {
			Connection connection = con.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement ps;
			String sql1 = "select balance from checkings where accountnum = " + "'" + accountnum + "'" + ";";
			ps = connection.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == false) {
				System.out.println();
				System.out.println("The number entered is not associated with a Checkings account number");
				System.out.println();
				withdrawn = false;
				return withdrawn;
			} else {
				// may have to localize totalBalnce
				totalBalance = rs.getDouble("balance");
			}

			if (totalBalance < amount) {
				System.out.println("This account has insuffecient funds to support your withdrawl");
				System.out.println("Total balance: $" + totalBalance);
				System.out.println();
				return false;
			} else {
				DecimalFormat df = new DecimalFormat(".00");

				// Perform withdrawl
				String sql = "update checkings set balance = balance - " + df.format(amount) + " where accountnum = "
						+ "'" + accountnum + "'" + " ;";
				statement = connection.createStatement();
				statement.executeUpdate(sql);
				totalBalance = totalBalance - amount;
				connection.commit();
				System.out.println();
				System.out.println("*******************************************");
				System.out.println();
				System.out.println("Amount Withdrawn: $" + df.format(amount) + "");
				System.out.println("New Balance for Account #" + accountnum + ": $" + df.format(totalBalance) + "");
				System.out.println();
				System.out.println("*******************************************");
				System.out.println();
				user.addToList("Withdrew $" + df.format(amount) + " From Checkings Account# " + accountnum + "");

				withdrawn = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return withdrawn;
	}

	public boolean createCheckingsAccount(Scanner scan, User user) {
		boolean created = false;
		int userid = 0;
		// Getting user account number
		System.out.println();
		System.out.print("Enter account number: ");
		int num = scan.nextInt();
		System.out.println();

		try {
			Connection connection = con.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement ps;
			String sql = "select * from checkings where accountnum = " + "'" + num + "'" + ";";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == false) {
				PreparedStatement ps1;
				String sql1 = "select userid from bankuser where username = " + "'" + user.getUsername() + "'" + ";";
				ps1 = connection.prepareStatement(sql1);
				ResultSet rs1 = ps1.executeQuery();
				while (rs1.next()) {
					userid = rs1.getInt("userid");
				}
				sql = "insert into checkings(balance, userid, accountnum) values(" + "'" + balance + "'" + "," + "'"
						+ userid + "'" + "," + "'" + num + "'" + ");";
				statement = connection.createStatement();
				statement.executeUpdate(sql);
				System.out.println();
				System.out.println("*******************************************");
				System.out.println();
				System.out.println("Your Checkings Account has been created!");
				System.out.println("Your account number is: " + num + "");
				System.out.println();
				System.out.println("*******************************************");
				System.out.println();
				created = true;
				user.addToList("Created New Checking Account# " + num + "");
				connection.commit();
			} else {
				System.out.print("This Account Number is already being used!");
				System.out.println();
				created = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return created;
	}

	@Override
	public void getBalanceAccounts(User user) {

		try {
			Connection connection = con.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement ps;
			String sql = "select * from checkings join bankuser on bankuser.userid = checkings.userid where username ="
					+ "'" + user.getUsername() + "'" + ";";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			System.out.println();
			System.out.println("*******************************************");
			System.out.println();
			System.out.println("Account#____Balance");

			while (rs.next()) {
				int accountnum = rs.getInt("accountnum");
				double balance = rs.getDouble("balance");

				System.out.println("" + accountnum + "___________$" + balance + "");
			}
			System.out.println();
			System.out.println("*******************************************");
			System.out.println();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
