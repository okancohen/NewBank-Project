package server;

public class Account {
	
	private String accountName;
	private double openingBalance;
	private double mySavingsGoal;
	private String address;
	private String phoneNumber;
	private String emailAddress;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}

	public Account(String accountName, double openingBalance, double mySavingsGoal) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.mySavingsGoal = mySavingsGoal;
	}
	//Overloaded constructor just for the email addition to existing accounts in db
	public Account(String accountName, double openingBalance, double mySavingsGoal, String emailAddress) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.mySavingsGoal = mySavingsGoal;
		this.emailAddress = emailAddress;
	}

	//Overloaded constructor to deal with extra information provided when account is opened by customer
	public Account(String accountName, double openingBalance, double mySavingsGoal, String address, String phoneNumber, String emailAddress) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.mySavingsGoal = mySavingsGoal;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	public String toString() {
		return (accountName + ": " + openingBalance);
	}


	public void depositMoney(double amount ){
		this.openingBalance += amount;
	}

	public void withdrawMoney(double amount){
		if (this.openingBalance <= amount) {
			//TODO add text output when not possible to withdraw
			//Print statement doesn't work as it is returning to New Bank (check this out. its lovely.)
		} else {
				this.openingBalance -= amount;
		}
	}

	public String getAccountName(){
		return this.accountName;
	}


	public double getBalance(){ return this.openingBalance;}


	public String getEmailAddress() {
		return emailAddress;
	}

}
