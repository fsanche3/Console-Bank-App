package dev.bankapp.accounts;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Scanner;

import dev.bankapp.authentication.User;
import dev.bankapp.authentication.UserInput;
import dev.bankapp.utils.ConDAO;

public class WireFunds {
	
	UserInput UI = new UserInput();
	Connection connect = null;
	ConDAO con = new ConDAO();
	java.sql.Statement statement = null;

	public WireFunds() {}
	
	public void Transfer(User user, Scanner scan) {
		// Getting Account Selection and #
		int choice, accNum, accID;
		String acc = "";
		int[] accInfo = UI.getSender(user, scan);
		if(accInfo[0] == -1) {return;} 
		else {
			 choice = accInfo[0];
			 accNum = accInfo[1];
			 accID = accInfo[2];
		}
		switch(choice) {
			case 1:
				 acc = "checkings";
				break;
			case 2: 
				 acc = "savings";
				break;
		}
		// Retrieving amount after checked account total
		double amount = UI.getAmount(user, acc, accNum, scan);
			if(amount == -1.0) {return;}
		// Retrieving recipient account Num
		int reciever = UI.getRecipient(scan);
			if(reciever == -1) {return;}
		
		// Sender has account with $ --> recipient has an account thats elidgble 
			try {
				// Update money coming out of account
		        DecimalFormat df = new DecimalFormat(".00");
				connect = con.getConnection();
				connect.setAutoCommit(false);
				String sql1 = "update "+acc+" set balance = balance - "+ "'"+df.format(amount)+"'" +" where accountnum = "+"'"+accNum+"'"+ ";";
				statement = connect.createStatement();
				statement.executeUpdate(sql1);
				// Update money coming into a account
				String sql2 = "update checkings set balance = balance + "+ "'"+df.format(amount)+"'" +" where accountnum = "+"'"+reciever+"'"+";";
				statement = connect.createStatement();
				statement.executeUpdate(sql2);
				// Add to User transaction List
				user.addToList("Sent $"+df.format(amount)+" from "+acc+" Account#:"+accNum+" to Account#:"+reciever+"");
				System.out.println("*******************************************");
				System.out.println();
				System.out.println("$"+amount+" Has been sent to Account#"+reciever+""); 
				System.out.println();
				System.out.println("*******************************************");
				System.out.println();
				connect.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	
	
	
	
	
	
}
