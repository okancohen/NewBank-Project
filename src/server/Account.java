package server;

public class Account {
	
	private String accountName;
	private double openingBalance;
	private double mySavingsGoal;

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
		this.openingBalance -= amount;
	}

	public String getAccountName(){
		return this.accountName;
	}


	public double getBalance(){ return this.openingBalance;}
	
	//show current savings goal
	
	public double getSavingsGoal(){ return this.mySavingsGoal;}
	
	//change or update mySavingsGoal
	
	public void updateMySavingsGoal(double newGoal){
		this.mySavingsGoal == newGoal;
	}
	
	//Find remaining amount to deposit before savingsGoal is reached
	
	public double remainderToSavingsGoal(){
		return this.mySavingsGoal - openingBalance;
	}




	

}
