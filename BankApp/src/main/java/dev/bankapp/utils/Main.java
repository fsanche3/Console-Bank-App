package dev.bankapp.utils;

import java.awt.datatransfer.SystemFlavorMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dev.bankapp.accounts.Checking;
import dev.bankapp.accounts.GetBalance;
import dev.bankapp.accounts.Saving;
import dev.bankapp.accounts.WireFunds;
import dev.bankapp.authentication.Login;
import dev.bankapp.authentication.SignUp;
import dev.bankapp.authentication.User;



public class Main {

	public static void main(String[] args) {

		List<String> cred = new ArrayList<>();
		boolean result;
		User user;
		SignUp su = new SignUp();
		Login login = new Login();
		Scanner scan = new Scanner(System.in);
		
		System.out.println();
		System.out.println("--====Welcome to the Console Bank!====--------------------------------------------------------");
		System.out.print("Choose 1 for Login Choose 2 to Sign Up: ");
		int choice = scan.nextInt();
		System.out.println();
		
		switch (choice) {
		case 2:
			System.out.println("--===Sign Up===------------------------------------------------------");
			result = su.signUserUp(scan);
			if (result == false) {
				System.out.println("Try a different username");
				System.out.println();
				System.out.println("--===Sign Up===--------------------------------------------------");
				result = su.signUserUp(scan);
				if (result == false) {
					break;
				}
				break;
			}
				break;
		case 1:
			System.out.println("--===Login===--------------------------------------------------------");
			 result = login.login(scan);
			 if(result == false) {
				System.out.println("Login failed: Please try a different username or password."); 
				result = login.login(scan);
				if(result == false) {
					System.out.println("Login failed: Please try a different username or password."); 
					 result = login.login(scan);
					 if(result == false) {
						 System.out.print("You are out of chances..");
						 break;
					 }
				}	}
			 System.out.println();
			 cred = login.getUser();
			 user = new User(cred.get(0),cred.get(1));
			 System.out.println();
				System.out.println("Welcome to Console Bank "+user.getUsername()+"!");
				System.out.println();
				showMenu(scan, user);
			break;
			default:
			System.out.print("\n Not a Valid Input");
			break;
		}
		 


		scan.close();
	}
	
	
	private static void showMenu(Scanner scan, User user) {
		GetBalance action;
		WireFunds wf;
		System.out.println();
		System.out.println("--===Menu===-------------------------------------------------------------");
		System.out.println("1. Checkings Account");
		System.out.println("2. Savings Account");
		System.out.println("3. Balances");
		System.out.println("4. Transactions History");
		System.out.println("5. Wire Money");
		System.out.println("6. Quit");
		System.out.print("Choice: ");
		int choice = scan.nextInt();

		switch (choice) {
		case 1:
			 checkingsAccountMenu(scan, user);
			 break;
		case 2:
			savingsAccountMenu(scan, user);
			break;
		case 3:
			action = new GetBalance(user);
			action.getBalances(scan, user);
			showMenu(scan, user);
			break;
		case 4:
			user.getTransactions(user);
			showMenu(scan, user); 
			break;
		case 5:
			wf = new WireFunds();
			wf.Transfer(user, scan);
			showMenu(scan, user);
			break;
		case 6:
			System.out.println();
			System.out.println("*****************************************************************");
			System.out.println();
			System.out.println("Thanks for using Console Bank by Franklyn Sanchez");
			System.out.println();
			System.out.println("*****************************************************************");
			System.out.println();
			break;

		default:
			System.out.println("Choose a number above");
			System.out.println();
			break;
		}
	}

	private static void checkingsAccountMenu(Scanner scan, User user) {
		 Checking checking;
		 boolean result;
		System.out.println();
		System.out.println("--===Checkings Account Menu===-------------------------------------------");
		System.out.println("1. Create An Account");
		System.out.println("2. Deposit");
		System.out.println("3. Withdrawl");
		System.out.println("4. View Balance/Account(s)");
		System.out.println("5. Back");
		System.out.print("Choice: ");

		int choice = scan.nextInt();
		// Add here from authentication method to wire funds. this one has to be done differently split up your code into testable methods.
		switch(choice) {
		case 1: 
			 checking = new Checking(user);
			 checking.createCheckingsAccount(scan, user);
			 checkingsAccountMenu(scan, user);
			break;
		case 2:
			checking = new Checking(user);
			result = checking.deposit(scan);
			if(result == false) {
				checking.deposit(scan);
			}
			checkingsAccountMenu(scan,user);
			break;
		case 3: 
			checking = new Checking(user);
			result = checking.withdrawl(scan);
			if(result == false) {
				checking.withdrawl(scan);
			}
			checkingsAccountMenu(scan,user);
			break;
		case 4:
			checking = new Checking(user);
			checking.getBalanceAccounts(user);	
			checkingsAccountMenu(scan, user);
			break;
		case 5:
			showMenu(scan, user);
			break;
		default:
			System.out.print("Choose a number above");
			System.out.println();
			checkingsAccountMenu(scan,user);
			break;
		}
	}
	
	private static void savingsAccountMenu(Scanner scan, User user) {
		 Saving savings;
		 boolean result;
		System.out.println();
		System.out.println("--===Savings Account Menu===---------------------------------------------");
		System.out.println("1. Create An Account");
		System.out.println("2. Deposit");
		System.out.println("3. Withdrawl");
		System.out.println("4. View Balance/Account(s)");
		System.out.println("5. Apply Annual Intrest Rate");
		System.out.println("6. Back");
		System.out.print("Choice: ");

		int choice = scan.nextInt();
		// Add Apply instrest feature;
		switch(choice) {
		case 1: 
			savings = new Saving(user);
			savings.createSavingsAccount(scan, user);
			savingsAccountMenu(scan,user);
			break;
		case 2:
			savings = new Saving(user);
			result = savings.deposit(scan);
			if(result == false) {
				savings.deposit(scan);
			}
			
			savingsAccountMenu(scan,user);
			break;
		case 3:
			savings = new Saving(user);
			result = savings.withdrawl(scan);
			if(result == false) {
				savings.withdrawl(scan);
			}
			savingsAccountMenu(scan,user);
			break;
		case 4:
			savings = new Saving(user);
			savings.getBalanceAccounts(user);	
			savingsAccountMenu(scan, user);
			break;
		case 5:
			savings = new Saving(user);
			savings.applyIntrest(scan, user);
			savingsAccountMenu(scan, user);
			break;
		case 6:
			showMenu(scan,user);
			break;
		default:
			System.out.println("Choose a number above.");
			System.out.println();
			savingsAccountMenu(scan,user);
			break;
		}
	}
	
	

	

}
