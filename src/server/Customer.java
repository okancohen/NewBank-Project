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
			s += a.toString() + "\n";
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}

	// Set a password for the customer to access accounts.
	public void setPassword(String password){
		int length = password.length();
		boolean hasUpperCase = !password.equals(password.toLowerCase());
		boolean hasLowercase = !password.equals(password.toUpperCase());

		if (length < 6 ) {
			System.out.println("Your password is too short. Your password must contain six or more characters.");
		}
		else if (!hasUpperCase) {
			System.out.println("Your password needs at least one upper case character");
		}

		else if (!hasLowercase) {
			System.out.println("Your password needs at least one lower case letter");
		}

			//TODO able to read password
		else {
			this.password = password;
		}
	}

	// check the password is correct when entered.
	public boolean correctPassword(String password){
		return this.password.equals(password);
	}

	public ArrayList<Account> getAccounts(){return accounts;}



}
