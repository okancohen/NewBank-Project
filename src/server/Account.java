package server;

public class Account {
	
	private String accountName;
	private double openingBalance;
	private double mySavingsGoal;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}

	public Account(String accountName, double openingBalance, double mySavingsGoal) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.mySavingsGoal = mySavingsGoal;
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






	

}
