package dev.bankapp.accounts;

import java.util.Scanner;

import dev.bankapp.authentication.User;

public abstract class Account {
	
	public double balance;
	
	
	public abstract boolean deposit(Scanner scan);
	
	public abstract boolean withdrawl(Scanner scan);
			
	public abstract void getBalanceAccounts(User user);
	
	
	public void setBalance(double amount) {
		balance = amount;
	}
	

	
	
	

}
