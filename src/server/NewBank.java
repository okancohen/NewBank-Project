package server;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	private String action = "";
	private String acc = "";
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	private double amount;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.0));
		bhagy.addAccount(new Account("Savings", 800.0));
		bhagy.addAccount(new Account("Checking", 600.0));
		bhagy.setPassword("password");
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer();
		christina.addAccount(new Account("Savings", 1500.0));
		customers.put("Christina", christina);
		
		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);
	}
	
	public static NewBank getBank() {
		return bank;
	}
	
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName) && customers.get(userName).correctPassword(password)){
			return new CustomerID(userName);
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {

			if(action.length()>1 && acc.length()>1 && isNumeric(request)) {
				amount = Double.parseDouble(request);
				request ="AMOUNT";
			}


			switch(request) {
				case "SHOWMYACCOUNTS" :
					return showMyAccounts(customer);

				case "DEPOSIT":
					action+="deposit";
					return "Into which account would you like to " + action;
				case "MAIN":
					acc+="main";
					return "How much would you like to " + action + " into your " + acc + " account";
				case "AMOUNT":
					return applyToAccount(customer, action, acc, amount);


			default : return "FAIL";
			}
		}
		return "FAIL";
	}
	
	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}


	// simply check if customer already had an account.
	public boolean checkIfAccount(String customer){
		return customers.containsKey(customer);
	}

	// check if the request is in a numeric form.
	private boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		return pattern.matcher(strNum).matches();
	}





	private String applyToAccount(CustomerID customer, String action, String acc, Double amount){
		Account account = customers.get(customer.getKey()).getAccounts()
				.stream()
				.filter(a -> Objects.equals(a.getAccountName().toLowerCase(), acc.toLowerCase()))
				.findAny()
				.orElse(null);

		if (action.equals("deposit")){
			account.depositMoney(amount);
			action = "deposited into";
		}else if (action.equals("withdraw")){
			account.withdrawMoney(amount);
			action = "withdrawn from";
		}

		return "Your have " + action +  " " + amount + " your " + acc + " account + \nYour balance is now " + account.getBalance();
	}







}
