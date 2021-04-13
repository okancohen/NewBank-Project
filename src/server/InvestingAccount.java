//Re Fr19 and FR10 - clients can access savings/investing accounts that they can use to generate interest
//this is an approach where an entire new class is created to accomodate the special features regarding investment/savings returns and such
//Feel free to suggest better approaches to this feature
//Kept 'mySavingsGoal' from account.java as a reminder to incorporate savings goal eventually into the investing account

package server;

public class InvestingAccount extends Account{


	private final String investmentType;
	//initial options: Guaranteed Savings Account Returns, Low-risk investment portfolio, Medium-risk investment portfolio

	private double startAmount;

	// come back to savings goal to incorporate it later : private double mySavingsGoal;

	private double returnRate;
	//investment rate of return - assuming it's fixed and guaranteed for now
	//initial options - per year rate of return:
	//Guaranteed Savings Account Returns = 1%
	//Low-risk investment portfolio: %2
	//Medium-risk investment portfolio: %2.75
	
	//add preset variables to given return rates as explained in investment types below
	
	private double guaranteed;
	
	//guaranteed rate at 1% 0.01
	
	private double lowRisk;
	
	//low risk rate at 2% 0.02
	
	private double mediumRisk;
	
	//medium risk at 2.75% 0.0275

	public InvestingAccount(String investmentType, double startAmount) {
		super("INVESTMENT", startAmount);
		this.investmentType = investmentType;
		this.startAmount =startAmount; // keep for future projections
	}

	public InvestingAccount(String investmentType, double startAmount, double returnRate) {
		super("INVESTMENT", startAmount);
		this.investmentType = investmentType;
		this.returnRate = returnRate;
		this.startAmount =startAmount; // keep for future projections
	}
	
	public void setReturnRate(double rate){
		this.returnRate = rate;
		//add if-statement or case/s to incorporate the three potential cases around here

		
		if (rate == guaranteed) {
   			 this.returnRate == 0.0100;
		} else if (rate == lowRisk) {
   			 this.returnRate == 0.0200;
		} else if (rate == mediumRisk) {
			this.returnRate == 0.0275;
		}

	
	}


	public String printString() {
		return (investmentType + ": " + getBalance());
	}


	public String viewInvestmentTypes() {
		return ("Thanks for choosing to invest and grow your Savings with NewBank.\n" +
				"Here are the investment options we currently offer: \n\n" +
				"1. Guaranteed Savings Account Returns = 1% \n" +
				"2. Low-risk investment portfolio: %2 \n" +
				"3. Medium-risk investment portfolio: %2.75 \n");
	}

	//option for client to view potential returns from an investment choice over a provided a number of years (add investment type as an argument later - currently this only answers for investment type 1)
	public void viewPotentialReturns(double years){
		this.startAmount = this.startAmount * ((1+0.01) * years);
	}


	public String getInvestmentType(){
		return this.investmentType;
	}


}
