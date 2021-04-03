package server;

public class Account {
	
	private String accountName;
	private double openingBalance;
	private double mySavingsGoal;
	private double overdraft;

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
	
	public String toString() {
		return (accountName + ": " + openingBalance);
	}


	public void depositMoney(double amount ){
		this.openingBalance += amount;
	}

	public void changeOverdraft(double amount ){
		this.overdraft = amount;
	} //TODO make this useable

	public void withdrawMoney(double amount){
		if (this.openingBalance + this.overdraft <= amount) {
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






	

}
