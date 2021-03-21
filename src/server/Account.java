package server;

public class Account {
	
	private String accountName;
	private double openingBalance;
	private double mySavingsGoal;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}
	
	public String toString() {
		return (accountName + ": " + openingBalance);
	}


	public void depositMoney(double amount ){
		this.openingBalance += amount;
	}

	public void withdrawMoney(double amount){
		this.openingBalance -= amount;
	}

	public String getAccountName(String accountName){
		return this.accountName;
	}

	pubic void setSavingsGoal(double savingsGoal){
		this.mySavingsGoal == savingsGoal;	
	}



	

}
