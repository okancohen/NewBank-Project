package server;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	private String password;
	
	public Customer() {
		accounts = new ArrayList<>();
	}
	
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}

	// Set a password for the customer to access accounts.
	public void setPassword(String password){
		this.password = password;
	}

	// check the password is correct when entered.
	public boolean correctPassword(String password){
		return this.password.equals(password);
	}
}
