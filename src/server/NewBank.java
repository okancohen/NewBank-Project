package server;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	private ArrayList<String> action = new ArrayList<String>();
	private ArrayList<String> acc = new ArrayList<String>();
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	private ArrayList<Double> amount =new ArrayList<Double>();
	private Integer transfer_sequence = 0;


	public NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Current", 1000.0));
		bhagy.addAccount(new Account("Savings", 800,5000, "bhagy@gmail.com"));
		bhagy.setPassword("password");
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer();
		christina.addAccount(new Account("Savings", 1500.0, 3000, "christina@gmail.com")); // add savings goal for christina's savings account //
		customers.put("Christina", christina);
		
		Customer john = new Customer();
		john.addAccount(new Account("Current", 250,0, "john@gmail.com"));
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

	// Method for adding new customer to bank db
	public void addCustomerToBank(String accountName, Customer customer){
		customers.put(accountName, customer);
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {


			/* Check if needs to convert to a numeric amount */
			if((action.size() == acc.size()) && isNumeric(request)) {
				amount.add(Double.parseDouble(request));
				request ="AMOUNT";
			}

			if((action.size() != acc.size()) && isNumeric(request)){
				return "FAIL";
			}


			if(transfer_sequence.equals(1)){
				acc.add(request.toLowerCase());
				action.add("transfer2");
				transfer_sequence+=1;
				return "Which Account would you like to transfer money to?";
			}

			if(transfer_sequence==2){
				acc.add(request.toLowerCase());
				transfer_sequence+=1;
				return "How much money would you like to transfer?";
			}


			switch(request) {
				case "SHOWMYACCOUNTS" :
					return showMyAccounts(customer);

				case "YES" :
					return "What do you want to do?";

				case "NO" :
					return "EXIT";

				case "DEPOSIT":
					action.add("deposit");
					return "Into which account would you like to " + action.get(action.size()-1);

				case "WITHDRAW":
					action.add("withdraw");
					return "From which account would you like to " + action.get(action.size()-1);

				case "TRANSFER":
					action.add("transfer1");
					transfer_sequence +=1;
					return "Which Account do you want to transfer money from?";

				case "CURRENT":
					acc.add("current");
					return "How much would you like to " + action.get(action.size()-1);

				case "SAVINGS":
					acc.add("savings");
					return "How much would you like to " + action.get(action.size()-1);

				case "AMOUNT":
					// if the scenario is a transfer of money....
					if(transfer_sequence==3){
						try{
							transfer_sequence=0;
							return transferMoney(customer,acc.get(acc.size() - 2), acc.get(acc.size() - 1) , amount.get(amount.size()-1));
						}catch(Exception e){
							return "FAIL";
						}
					}else { // if not, use this function...
						try {
							return applyToAccount(customer, action.get(action.size() - 1), acc.get(acc.size() - 1), amount.get(amount.size() - 1));
						} catch (Exception e) {
							return "FAIL";
						}
					}

				case "SHOWMYTRANSACTIONS":
						String s= "\n";
						for (int row =0; row < action.size(); row++) {
							try {
								s += (String.format("%20s %20s %20s", acc.get(row), action.get(row), amount.get(row)) + "\n");
							} catch (Exception e){
								break;
							}
						}
						return s  + "\n Do you want to use another service? " ;





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

		return "You have " + action +  " " + amount + " your " + acc + " account\n " +
				"Your balance is now " + account.getBalance() + "\n\n" +
				"Do you want to use another service?";
	}




	private String transferMoney(CustomerID customer, String AccountFrom, String AccountTo, Double Amount){
		Account accountFrom = customers.get(customer.getKey()).getAccounts()
				.stream()
				.filter(a -> Objects.equals(a.getAccountName().toLowerCase(), AccountFrom.toLowerCase()))
				.findAny()
				.orElse(null);

		Account accountTo = customers.get(customer.getKey()).getAccounts()
				.stream()
				.filter(a -> Objects.equals(a.getAccountName().toLowerCase(), AccountTo.toLowerCase()))
				.findAny()
				.orElse(null);

		accountFrom.withdrawMoney(Amount);
		accountTo.depositMoney(Amount);

		return Amount + " transferred from your " + AccountFrom + " to your " + AccountTo + " account.\n" +
				"Your balance in your " + AccountFrom + " account is now " + accountFrom.getBalance() + "\n" +
				"and your balance in your" + AccountTo + " account is " + accountTo.getBalance() + "\n\n" +
				"Do you want to use another service?";

	}

	public boolean checkIfEmail(String emailAddress){
		//This method needs to check if email address already taken
		for (Map.Entry<String, Customer> entry : customers.entrySet()){
			//In each HashMap entry grab the customer object
			Customer tempCustomer = entry.getValue();
			//When you have the Customer object, grab all accounts from that customer
			ArrayList<Account> accountIterator = tempCustomer.getAccounts();
			//Check each account whether the email address is the same as the param
			for (Account accounts : accountIterator){
				if(emailAddress.equals(accounts.getEmailAddress())){
					return true;
				}
			}
		}
		return false;
	}











}
