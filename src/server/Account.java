package server;

public class Account {
	
	private String accountName;
	private double openingBalance;
	private double mySavingsGoal;
	private double overdraft;
	private String address;
	private String phoneNumber;
	private String emailAddress;


	public Account(String accountName){
		this.accountName = accountName;
	}

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.overdraft = 0;
	}

	public Account(String accountName, double openingBalance, double mySavingsGoal) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.mySavingsGoal = mySavingsGoal;
		this.overdraft = 0;
	}

	public Account(String accountName, double openingBalance, double mySavingsGoal, double overdraft) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.mySavingsGoal = mySavingsGoal;
		this.overdraft = overdraft;
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

	public void changeOverdraft(double amount ){
		this.overdraft = amount;
	} //TODO make this use-able

	public void withdrawMoney(double amount){
		if (this.openingBalance + this.overdraft <= amount) {
			//TODO add text output when not possible to withdraw
			//{System.out.println("TEST STRING");}
			//Print statement doesn't work as it is returning to New Bank (check this out. its lovely.)
		} else {
				this.openingBalance -= amount;
		}
	}

	public String getAccountName(){
		return this.accountName;
	}


	public double getBalance(){ return this.openingBalance;}
	
	//show current savings goal
	
	public double getSavingsGoal(){ return this.mySavingsGoal;}
	
	//change or update mySavingsGoal
	
	public void updateMySavingsGoal(double newGoal){
		this.mySavingsGoal = newGoal;
	}
	
	//Find remaining amount to deposit before savingsGoal is reached
	
	public double remainderToSavingsGoal(){
		return this.mySavingsGoal - openingBalance;
	}

	public String getEmailAddress() {
		return emailAddress;
	}


}
