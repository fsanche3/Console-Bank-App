package dev.bankapp.authentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Scanner;

import dev.bankapp.utils.ConDAO;
import dev.bankapp.utils.Logger;
import dev.bankapp.utils.LoggingLevels;

public class UserInput {
	Connection connect = null;
	double totalBalance = 0.0;
	Logger log = Logger.getLogger();

	
	public UserInput() {
	}
	
	public int[] getSender(User user, Scanner scan) {
		int[] account = new int[3];
		int[] n = {-1};
		System.out.print("Send from Checkings(1) or Savings(2): ");
		int choice = scan.nextInt();
		System.out.println();
		System.out.print("Enter Account#: ");
		int accountNum = scan.nextInt();
		ConDAO con = new ConDAO();
		int accId = -1;
		switch(choice) {
			case 1:
				log.log("User choose to send from a checkings account", LoggingLevels.TRACE);
				try {
					connect = con.getConnection();
					PreparedStatement ps;
					String sql1 = "select checkingid from checkings join bankuser on bankuser.userid = checkings.userid where accountnum = " + "'" + accountNum + "'"+" and bankuser.username= "+ "'"+user.getUsername()+ "'" +";";
					ps = connect.prepareStatement(sql1);
					ResultSet rs = ps.executeQuery();

					if (rs.next() == false) {
						System.out.println();
						System.out.println("The number entered is not associated with a Checkings account");
						System.out.println();
						return n;
					} 	else {
						accId = rs.getInt("checkingid");
					}
				} catch (SQLException e) {
					log.log("DB Connection Error", LoggingLevels.ERROR);
					e.printStackTrace();
				}
				break;
			case 2:
				log.log("User choose to send from a savings account", LoggingLevels.TRACE);

				try {
					connect = con.getConnection();
					PreparedStatement ps;
					String sql1 = "select savingid from savings join bankuser on bankuser.userid = savings.userid where accountnum = " + "'" + accountNum + "'"+" and bankuser.username= "+ "'"+user.getUsername()+ "'" +";";
					ps = connect.prepareStatement(sql1);
					ResultSet rs = ps.executeQuery();

					if (rs.next() == false) {
						System.out.println();
						System.out.println("The number entered is not associated with a Savings account");
						System.out.println();
						return n;
					} else {
						accId = rs.getInt("savingid");
					}
				} catch (SQLException e) {
					log.log("DB Connection Error", LoggingLevels.ERROR);
					e.printStackTrace();
				}
				break;
			default:
				log.log("User entered an option thats not specified", LoggingLevels.TRACE);
				System.out.println("Your Account Entry was invalid please try again");
				return n;
		}
		account[0] = choice;
		account[1] = accountNum;
		account[2] = accId;
		return account;
	}
	
	public double getAmount(User user, String account, int accountNum, Scanner scan) {
		double totalBalance = 0.0;
		System.out.println();
		System.out.print("Enter amount you would like sent: ");
		double amount = scan.nextDouble();
        DecimalFormat df = new DecimalFormat(".00");
		ConDAO con = new ConDAO();
		try {
			connect =  con.getConnection();
			PreparedStatement ps;
			String sql1 = "select balance from "+account+" join bankuser on bankuser.userid = "+account+".userid where "+account+".accountnum = "+ "'"+accountNum+"'"+";";
			ps = connect.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				totalBalance = rs.getDouble("balance");
			}
			if(totalBalance < amount) {
				log.log("User didnt have enough funds", LoggingLevels.TRACE);
				System.out.println("Not enough funds");
				System.out.println("Account Balance: $"+df.format(totalBalance)+"");
				return -1.0;
			}
		} catch (SQLException e) {
			log.log("DB Connection Error", LoggingLevels.ERROR);
			e.printStackTrace();
		}
	return amount;	
	}
	
	public int getRecipient(Scanner scan) {
		System.out.println();
		System.out.print("Enter Recipient Account#: ");
		int accountNum = scan.nextInt();
		System.out.println();
		ConDAO con = new ConDAO();
		try {
			connect =  con.getConnection();
			PreparedStatement ps;
			String sql1 = "select * from checkings where accountnum = "+"'"+accountNum+"'"+";";
			ps = connect.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			if(rs.next() == false) {
				log.log("User entered an account that cannot be found", LoggingLevels.TRACE);
				System.out.println("Could not find an eligible Account for transactions");
				return -1;
			}
				
		} catch (SQLException e) {
			log.log("DB Connection Error", LoggingLevels.ERROR);
			e.printStackTrace();
		}
		
		return accountNum;
	}

}
