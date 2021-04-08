//Re Fr19 and FR10 - clients can access savings/investing accounts that they can use to generate interest
//this is an approach where an entire new class is created to accomodate the special features regarding investment/savings returns and such
//Feel free to suggest better approaches to this feature
//Kept 'mySavingsGoal' from account.java as a reminder to incorporate savings goal eventually into the investing account

package server;

public class investingAccount {
	
	private String investmentType;
  //initial options: Guaranteed Savings Account Returns, Low-risk investment portfolio, Medium-risk investment portfolio
	
  private double startAmount;
  
	// come back to savings goal to incorporate it later : private double mySavingsGoal;
  
  private double returnRate;
  //investment rate of return - assuming it's fixed and guaranteed for now
  //initial options - per year rate of return: 
  //Guaranteed Savings Account Returns = 1%
  //Low-risk investment portfolio: %2
  //Medium-risk investment portfolio: %2.75

	public void Account(String investmentType, double startAmount, double returnRate) {
		this.investmentType = investmentType;
		this.startAmount = startAmount;
	}


	public String toString() {
		return (investmentType + ": " + startAmount);
	}


	public void investMoney(double amount ){
		this.startAmount += amount;
	}
  
  public String viewInvestmentTypes() {
    
    return ("Thanks for choosing to invest and grow your Savings with NewBank. Here are the investment options we currently offer:" + "1. Guaranteed Savings Account Returns = 1% \n"
  + "2. Low-risk investment portfolio: %2 \n" + "3. Medium-risk investment portfolio: %2.75 \n");
  }
  
  //option for client to view potential returns from an investment choice over a provided a number of years (add investment type as an argument later - currently this only answers for investment type 1)
  public void viewPotentialReturns(double years){
    this.startAmount = this.startAmount * ((1+0.01) * years);
  }


	public String getInvestmentType(){
		return this.investmentType;
	}


	public double getBalance(){ return this.startAmount;}

}
