package test.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.CustomerID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NewBankTest {

    public server.NewBank bank = new server.NewBank();
    private CustomerID customer;

    @BeforeEach
    public void setCustomer() throws Exception{
        this.customer = bank.checkLogInDetails("Bhagy", "password");
    }

    @Test
    @DisplayName("Check if accounts present")
    public void testCheckIfAccount(){
        assertTrue(bank.checkIfAccount("Bhagy"), "Should be true");
    }

    @Test
    @DisplayName("Check if Deposit works")
    public void testDeposit(){
        assertEquals(bank.processRequest(this.customer, "DEPOSIT"), "Into which account would you like to deposit");
    }


    @Test
    @DisplayName("Check if withdrawal works")
    public void testWithdrawal(){
        assertEquals(bank.processRequest(this.customer, "WITHDRAW"), "From which account would you like to withdraw");
    }

    @Test
    @DisplayName("Check if Transfer works")
    public void testTransfer(){
        assertEquals(bank.processRequest(this.customer, "TRANSFER"), "Which Account do you want to transfer money from?");
    }

    @Test
    @DisplayName("Check if CURRENT works")
    public void testCurrent(){
        bank.processRequest(this.customer, "DEPOSIT");
        assertEquals(bank.processRequest(this.customer, "CURRENT"), "How much would you like to deposit");
    }


    @Test
    @DisplayName("Check if SAVINGS works")
    public void testSavings(){
        bank.processRequest(this.customer, "DEPOSIT");
        assertEquals(bank.processRequest(this.customer, "SAVINGS"), "How much would you like to deposit");
    }


    @Test
    @DisplayName("Check if AMOUNT works when depositing")
    public void testAMOUNT_Deposit(){
        bank.processRequest(this.customer, "DEPOSIT");
        bank.processRequest(this.customer, "SAVINGS");
        String s = bank.processRequest(this.customer, "10");

        assertTrue(s.contains("810"), "AMOUNT WORKS");

    }








}
