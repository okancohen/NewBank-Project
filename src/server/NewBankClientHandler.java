package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewBankClientHandler extends Thread{
	
	private NewBank bank;
	private BufferedReader in;
	private PrintWriter out;
	
	
	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}


	public void run() {
		// keep getting requests from the client and processing them
		try {

			out.println(" ---- Welcome to NewBank ---- \n");
			// Print the menu of options
			printMenu();
			
			// wrap options within loop.
			// optionLoop - label for the break statement
			optionLoop: while(true) {

				String selection = in.readLine();
				Integer option = getSelection(selection);

				switch (option) {
					// -------------  OPTION 1 -------------------
					case 1:
						out.println("Enter Username");
						String userName = in.readLine();
						// ask for password
						out.println("Enter Password");
						String password = in.readLine();
						out.println("Checking Details...");
						// authenticate user and get customer ID token from bank for use in subsequent requests
						CustomerID customer = bank.checkLogInDetails(userName, password);
						// if the user is authenticated then get requests from the user and process themm
						if (customer != null) {
							out.println("Log In Successful. What do you want to do?");
							while (true) {
								String request = in.readLine();
								System.out.println("Request from " + customer.getKey());
								String response = bank.processRequest(customer, request);
								out.println(response);
								if (response.equals("FAIL")) {
									out.println("Sorry - your request was not recognised");
									out.println("Please try again");
								}
							}
						} else {
							out.println("Log In Failed");
						}

						break;

					// ------------- OPTION 2 -------------------
					case 2:
						// Get a new account name
						out.println("Please enter a username for yourself");
						String newUserName = in.readLine();

						String newCustomer = checkNewUserName(newUserName);

						//Grab user contact information (start with email address for now - FR9)

						out.println("Please enter your email address");
						String newEmailAddress = in.readLine();

						//check if email address valid - i.e not used before - unfinished: complete this method below and re-comment this

						String newCustomerEmail = checkNewEmail(newEmailAddress);

						//Add more contact information as needed - email satisfactory for now

						out.println("Please enter the type of account you want ");
						String accountName = in.readLine();

						out.println("Please enter the initial amount you wish to deposit");
						String openingBalance = in.readLine();
						double balance = Double.parseDouble(openingBalance);

						out.println("Please add a password to your account");
						String pw = in.readLine();

						out.println("Please add your home address to your account");
						String address = in.readLine();

						out.println("Please add your phone number to your account");
						String phoneNumber = in.readLine();

						//Account newAccount = new Account(accountName, balance, 0, address, phoneNumber);
						Customer joiningCustomer = new Customer();
						joiningCustomer.addAccount(new Account(accountName, balance, 0, address, phoneNumber, newCustomerEmail));
						bank.addCustomerToBank(accountName, joiningCustomer);

						break;

					// ------------- OPTION 3 - Learn more about NewBank Services -------------------
					case 3:
						// Get a new account name
						out.println("Welcome to NewBank! NewBank is one of the country's largest banks and we are now proud to start offering our services online.");
						out.println("You can now complete a range of online services from account creation to transfers and more! \n " +
								"We look forward to having you as a client.  \n" +
								"For more information you can call us directly at 800-xxx-xxxx \n " +
								" ----------------------------- \n" +
								"Please select another option from the menu: \n");
						// restart the welcome statement
						printMenu();

						break;


					default:
						break optionLoop;

				}

			}

		} catch (IOException e) {

			e.printStackTrace();
			//System.exit(0);


		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}


	/** Takes the clients input, determines if the selection is appropriate and then either returns the selection
	 * as an integer value or asks the client to make another selection attempt.
	 * @param selection
	 * @return option (interger)
	 */

	private Integer getSelection(String selection){

			if(selection.equals("1") || selection.equals("2") || selection.equals("3")) {
				Integer option = Integer.parseInt(selection);
				return option;
			}else {
				out.println("Sorry - your selection was not recognised\n");
				out.println("Please try again");
					try {
						selection = in.readLine();
						return getSelection(selection);
					} catch (IOException e) {
						e.printStackTrace();
						Thread.currentThread().interrupt();
					}

			}

		return null;
	}

	/** Takes checks if the client already has an account, or the username is already being used.
	 * @param newUserName
	 * @return newUserName
	 */
	private String checkNewUserName(String newUserName) {

		if (bank.checkIfAccount(newUserName)) {
			out.println("This username already exists");
			out.println("Please try provide a different username");
			try {
				newUserName = in.readLine();
				return checkNewUserName(newUserName);
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}

		return newUserName;
	}
		
	//Checks if email address is valid (was not previously used for regsitration - ie does not exist in database)
		
	private String checkNewEmail(String newEmailAddress){
		
		//go back to class and add the respective method
		
		if(bank.checkIfEmail(newEmailAddress)) {
			out.println("This email address is already used to create a NewBank Account");
			out.println("Please provide a different email address or log in using your username and password");
			try {
				newEmailAddress = in.readLine();
				return checkNewEmail(newEmailAddress);
			} catch (IOException em) {
				em.printStackTrace();
				Thread.currentThread().interrupt();
			
			} 
		} else {

			String REGEX = "^(.+)@(.+)$";
			Pattern emailPattern = Pattern.compile(REGEX);
			Matcher validEmail = emailPattern.matcher(newEmailAddress);

			while(!validEmail.matches()){
				try{
					out.println("The inserted email is not valid, please enter a new one");
					newEmailAddress = in.readLine();
					validEmail = emailPattern.matcher(newEmailAddress);
				} catch (IOException emailInput){
					emailInput.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}
		return newEmailAddress;
	}



	private void printMenu(){


		out.println("Do you want to:                \n");
		out.println("1. Login to your account       \n");
		out.println("2. Create a new account        \n");
		//FR12 'More Info' for potential clients
		out.println("3. Learn more about NewBank services        \n");
		out.println(" ----------------------------- \n");
		out.println("Please enter your selection:   \n");


	}


}



