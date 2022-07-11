package dev.bankapp.authentication;

import java.util.ArrayList;
import java.util.List;

import dev.bankapp.utils.Logger;
import dev.bankapp.utils.LoggingLevels;

public class User {
	
	
	private String username;
	/// User ID
	private int accountNumber;
	private String password;
	private List<String> checkTransactions = new ArrayList<>();
	Logger log = Logger.getLogger();

	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword(User user) {
		return user.password;
	}
	
	public void addToList(String string) {
		checkTransactions.add(string);
	}
	
	public List<String> getList(){
		return this.checkTransactions;
	}
	
	public boolean getTransactions(User user){
		log.log("User choose to see sessions transactions", LoggingLevels.TRACE);
		System.out.println();
		System.out.println("******************************************************************");
		System.out.println();
		for(int i = 0; i < checkTransactions.size(); i++) {
		
			System.out.println(checkTransactions.get(i));
		}
		System.out.println();
		System.out.println("******************************************************************");
		System.out.println();
		return true;
	}
}


