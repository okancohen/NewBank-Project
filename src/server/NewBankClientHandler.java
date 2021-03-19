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
			int option= Integer.parseInt(selection);



			boolean again = true;
			while(again) {
				again = false;
				switch (option) {
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
							}
						} else {
							out.println("Log In Failed");
						}

						break;

					// ------------- END OF OPTION 1 -------------------

					case 2:
						out.println("Work in progress...");
						break;

					default:
						again =true;
						break;

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

}
