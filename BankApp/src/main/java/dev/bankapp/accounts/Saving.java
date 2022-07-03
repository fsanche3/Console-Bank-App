package dev.bankapp.accounts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Scanner;

import dev.bankapp.authentication.User;
import dev.bankapp.utils.ConDAO;


public class Saving extends Account {

	User user;
	java.sql.Statement statement = null;
	double totalBalance = 0.0;
	double rate = 0.0;
	ConDAO con = new ConDAO();

	public Saving(User user) {
		this.user = user;
	}
	
	public void applyIntrest(Scanner scan, User user) {
		
		System.out.print("Enter The Account# For Earned Intrest: ");
		int accountnum = scan.nextInt();
		
		try {
			Connection connection = con.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement ps;
			String sql1 = "select balance, rate from savings where accountnum = " + "'" + accountnum + "'" + ";";
			ps = connection.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == false) {
				System.out.println();
				System.out.println("The number entered is not associated with a Savings account");
				System.out.println();
				return;
			} else {
				// may have to localize totalBalnce
				totalBalance = rs.getDouble("balance");
				rate = rs.getDouble("rate");
			}
	        DecimalFormat df = new DecimalFormat(".00");
			double rateUpdate = totalBalance * rate;
			totalBalance = totalBalance + rateUpdate;
			String sql = "update savings set balance = " + df.format(totalBalance) + " where accountnum = " + "'"
					+ accountnum + "'" + " ;";
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			connection.commit();
			System.out.println();
			System.out.println("*******************************************");
			System.out.println();
			System.out.println("Intreat earned: $" + rateUpdate + "");
			System.out.println("New Balance for Account #" + accountnum + ": $" + df.format(totalBalance) + "");
			user.addToList("Intrest earned $"+df.format(rateUpdate)+" For Savings Account# "+accountnum+"");
			System.out.println();
			System.out.println("*******************************************");
			System.out.println();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
			String sql1 = "select balance from savings where accountnum = " + "'" + accountnum + "'" + ";";
			ps = connection.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == false) {
				System.out.println();
				System.out.println("The number entered is not associated with a Savings account");
				System.out.println();
				withdrawn = false;
				return withdrawn;
			} else {
				// may have to localize totalBalnce
				totalBalance = rs.getDouble("balance");
			}
	        DecimalFormat df = new DecimalFormat(".00");

			if (totalBalance < amount) {
				System.out.println("This account has insuffecient funds to support your withdrawl");
				System.out.println("Total balance: $" + totalBalance);
				System.out.println();
				return false;
			} else {
				// Perform withdrawl
				String sql = "update savings set balance = balance - " + df.format(amount) + " where accountnum = " + "'"
						+ accountnum + "'" + " ;";
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

				user.addToList("Withdrew $"+df.format(amount)+" From Savings Account# "+accountnum+"");

				System.out.println();
				withdrawn = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return withdrawn;
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
			String sql1 = "select balance, rate from savings where accountnum = " + "'" + accountnum + "'" + ";";
			ps = connection.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == false) {
				System.out.println();
				System.out.println("The number entered is not associated with a Savings account");
				System.out.println();
				deposited = false;
				return deposited;
			} else {
				totalBalance = rs.getDouble("balance");
				rate = rs.getDouble("rate");
			}
		
			totalBalance = totalBalance + amount;
			double balance = totalBalance;
			double rateUpdate = totalBalance * rate;
			totalBalance = totalBalance + rateUpdate;
	        DecimalFormat df = new DecimalFormat(".00");

			
			String sql = "update savings set balance = balance + " + df.format(amount) + " where accountnum = " + "'"
					+ accountnum + "'" + " ;";
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			connection.commit();
			System.out.println();
			System.out.println("*******************************************");
			System.out.println();
			System.out.println("Amount deposited: $" + df.format(amount) + "");
			System.out.println("New Balance for Account #"+accountnum + ": $" + df.format(balance) + "");
			System.out.println("Total when Intrest is applied:$"+totalBalance+" ");
			System.out.println();
			System.out.println("*******************************************");
			System.out.println();
			user.addToList("Deposited $"+df.format(amount)+" into Savings Account# "+accountnum+"");


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deposited;
			}

	@Override
	public void getBalanceAccounts(User user) {
		try {
			Connection connection = con.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement ps;
			String sql = "select * from savings join bankuser on bankuser.userid = savings.userid where username ="
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
			// Rollback and check for rows affected FOR DML!!??!!
			e.printStackTrace();
		}
	}

	public void createSavingsAccount(Scanner scan, User user) {
		int userid = 0;
		System.out.println();
		System.out.print("Enter account number: ");
		int num = scan.nextInt();
		System.out.println();
		System.out.print("Enter Annual Intrest Rate(decimal): ");
		double rate = scan.nextDouble();
		// Database connection & Query creation 
		try {
			Connection connection = con.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement ps;
			// Checkinng if accountnum being used stores in rs.next()
			String sql = "select * from savings where accountnum = " + "'" + num + "'" + ";";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				PreparedStatement ps1;
				// Retrieving userID using user username
				String sql1 = "select userid from bankuser where username = " + "'" + user.getUsername() + "'" + ";";
				ps1 = connection.prepareStatement(sql1);
				ResultSet rs1 = ps1.executeQuery();
				while (rs1.next()) {
					userid = rs1.getInt("userid");
				}
				// Creating account into database
				sql = "insert into savings(balance, rate, userid, accountnum) values(" + "'" + balance + "'" + "," + "'"
						+ rate + "'" + "," + "'" + userid + "'" + "," + "'" + num + "'" + ");";
				statement = connection.createStatement();
				statement.executeUpdate(sql);
				System.out.println();
				System.out.println("*******************************************");
				System.out.println();
				System.out.println("Your Savings Account has been created!");
				System.out.println("Your account number is: " + num + "");
				user.addToList("Created New Saving Account# "+num+"");
				System.out.println();
				System.out.println("*******************************************");
				System.out.println();

				// Commiting changes to DB
				connection.commit();
			} else {
				System.out.print("This Account Number is already being used!");
				System.out.println();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
