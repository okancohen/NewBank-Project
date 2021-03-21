package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
			// ask for user name

			out.println(" ---- Wellcome to NewBank ---- \n");
			out.println("Do you want to:                \n");
			out.println("1. Login to your account       \n");
			out.println("2. Create a new account        \n");
			out.println(" ----------------------------- \n");
			out.println("Please enter your selection:   \n");

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
							if(response.equals("FAIL")){
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

					out.println("Please enter the type of account you want ");
					String accountName = in.readLine();

					out.println("Please enter the initial amount you with to deposit");
					String openingBalance = in.readLine();
					double balance = Double.parseDouble(openingBalance);

					out.println("Please add a password to your account");
					String pw = in.readLine();


					Account newAccount = new Account(accountName,balance);


					break;

				default:
					break;

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

			if(selection.equals("1") || selection.equals("2")) {
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

	/** Takes checks if the client already has an ccount, or the username is already being used.
	 * @param newUserName
	 * @return newUserName
	 */
	private String checkNewUserName(String newUserName){

		if(bank.checkIfAccount(newUserName)) {
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




}



