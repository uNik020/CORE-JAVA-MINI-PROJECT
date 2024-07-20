package com.BankingSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import static java.lang.Class.forName;

public class BankingApp {

	private static final String url = "jdbc:mysql://localhost:3306/banking_system";
	private static final String username = "root";
	private static final String password = "root@123";

	public static void main(String[] args) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection connection = DriverManager.getConnection(url, username, password);
	        Scanner scanner = new Scanner(System.in);
	        User user = new User(connection, scanner);
	        Accounts accounts = new Accounts(connection, scanner);
	        AccountsManager accountManager = new AccountsManager(connection, scanner);
	        Admin admin = new Admin(connection, scanner);

	        while (true) {
	            System.out.println("*** WELCOME TO BANKING SYSTEM ***");
	            System.out.println();
	            System.out.println("1. User");
	            System.out.println("2. Admin");
	            System.out.println("3. Exit");
	            System.out.println("Enter your choice: ");
	            int mainChoice = scanner.nextInt();

	            switch (mainChoice) {
	                case 1:
	                    // User Menu
	                    while (true) {
	                        System.out.println();
	                        System.out.println("1. Register");
	                        System.out.println("2. Login");
	                        System.out.println("3. Back");
	                        System.out.println("Enter your choice: ");
	                        int choice1 = scanner.nextInt();
	                        switch (choice1) {
	                            case 1:
	                                user.register();
	                                break;
	                            case 2:
	                                String email = user.login();
	                                if (email != null) {
	                                    System.out.println();
	                                    System.out.println("User Logged In!");
	                                    long account_number;
	                                    if (!accounts.account_exist(email)) {
	                                        System.out.println();
	                                        System.out.println("1. Open a new Bank Account");
	                                        System.out.println("2. Back");
	                                        if (scanner.nextInt() == 1) {
	                                            account_number = accounts.open_account(email);
	                                            System.out.println("Account Created Successfully");
	                                            System.out.println("Your Account Number is: " + account_number);
	                                        } else {
	                                            break;
	                                        }
	                                    }
	                                    account_number = accounts.getAccount_number(email);
	                                    int choice2 = 0;
	                                    while (choice2 != 5) {
	                                        System.out.println();
	                                        System.out.println("1. Debit Money");
	                                        System.out.println("2. Credit Money");
	                                        System.out.println("3. Transfer Money");
	                                        System.out.println("4. Check Balance");
	                                        System.out.println("5. Log Out");
	                                        System.out.println("Enter your choice: ");
	                                        choice2 = scanner.nextInt();
	                                        switch (choice2) {
	                                            case 1:
	                                                accountManager.debit_money(account_number);
	                                                break;
	                                            case 2:
	                                                accountManager.credit_money(account_number);
	                                                break;
	                                            case 3:
	                                                accountManager.transfer_money(account_number);
	                                                break;
	                                            case 4:
	                                                accountManager.getBalance(account_number);
	                                                break;
	                                            case 5:
	                                                System.out.println("User Logged out!");
	                                                break;
	                                            default:
	                                                System.out.println("Enter Valid Choice!");
	                                                break;
	                                        }
	                                    }
	                                } else {
	                                    System.out.println("Incorrect Email or Password!");
	                                    System.out.println("Please Try Again with valid credentials!");
	                                }
	                                break;
	                            case 3:
	                                // Back to the main menu
	                                break;
	                            default:
	                                System.out.println("Enter Valid Choice");
	                                break;
	                        }
	                        if (choice1 == 3) {
	                            break;
	                        }
	                    }
	                    break;
	                case 2:
	                    // Admin Menu
	                    while (true) {
	                        System.out.println();
	                        System.out.println("1. Register Admin");
	                        System.out.println("2. Admin Login");
	                        System.out.println("3. Back");
	                        System.out.println("Enter your choice: ");
	                        int choice1 = scanner.nextInt();
	                        switch (choice1) {
	                            case 1:
	                                admin.registerAdmin();
	                                break;
	                            case 2:
	                                String email = admin.admin_login();
	                                if (email != null) {
	                                    System.out.println();
	                                    System.out.println("Admin Logged In!");
	                                    while (true) {
	                                        System.out.println();
	                                        System.out.println("1. View Users");
	                                        System.out.println("2. View Accounts");
	                                        System.out.println("3. View Transactions");
	                                        System.out.println("4. Search Transaction by Email");
	                                        System.out.println("5. Search Transaction by account number");
	                                        System.out.println("6. Back");
	                                        System.out.println("Enter your choice: ");
	                                        int choice = scanner.nextInt();
	                                        switch (choice) {
	                                            case 1:
	                                                admin.viewUsers();
	                                                break;
	                                            case 2:
	                                                admin.viewAccounts();
	                                                break;
	                                            case 3:
	                                                admin.viewTransactions();
	                                                break;

												case 4:
													System.out.println("Enter email: ");
													email = scanner.next();
													admin.searchTransactionsByEmail(email);
													break;
												case 5:
													System.out.println("Enter Account Number: ");
													long accountNumber = scanner.nextLong();
													admin.searchTransactionsByAccountNumber(accountNumber);
													break;

	                                            case 6:
	                                                // Back to the main menu
	                                                break;
	                                            default:
	                                                System.out.println("Enter Valid Choice");
	                                                break;
	                                        }
	                                        if (choice == 6) {
	                                            break;
	                                        }
	                                    }
	                                    break;
	                                } else {
	                                    System.out.println("Incorrect Email or Password!");
	                                    System.out.println("Please Try Again with valid credentials!");
	                                }
	                                break;
	                            case 3:
	                                // Back to the main menu
	                                break;
	                            default:
	                                System.out.println("Enter Valid Choice");
	                                break;
	                        }
	                        if (choice1 == 3) {
	                            break;
	                        }
	                    }
	                    break;
	                case 3:
	                    // Exit the system
	                    System.out.println("THANK YOU FOR USING BANKING SYSTEM");
	                    System.out.println("Exiting the System...");
	                    return; // Terminate the program
	                default:
	                    System.out.println("Enter Valid Choice");
	                    break;
	            }
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

}

