package com.BankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Admin {
    private Connection connection;
    private Scanner scanner;

    public Admin(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    

    public void registerAdmin(){
        
        System.out.print("Full Name: ");
        String full_name = scanner.nextLine();
        String email = scanner.nextLine();
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please try again.");
            }
        }
        
        String password;
        String confirmPassword;
        while (true) {
            System.out.print("Password: ");
            password = scanner.nextLine();
            
            //pass validation
            if (!isValidPassword(password)) {
                System.out.println("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character. Please try again.");
                continue;
            }
            
            System.out.print("Confirm Password: ");
            confirmPassword = scanner.nextLine();
            if (password.equals(confirmPassword)) {
                break;
            } else {
                System.out.println("Passwords do not match. Please try again.");
            }
        }
        
        if(admin_exist(email)) {
            System.out.println("Admin Already Exists for this Email Address!!");
            return;
        }
        String registerAdmin_query = "INSERT INTO admin(full_name, email, password) VALUES(?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(registerAdmin_query);
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Registration Successfull!");
            } else {
                System.out.println("Registration Failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String admin_login(){
        scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        String loginAdmin_query = "SELECT * FROM admin WHERE email = ? AND password = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(loginAdmin_query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return email;
            }else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    private boolean isValidEmail(String email) {
        // Simple regex for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    
    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long, contain at least one uppercase letter,
        // one lowercase letter, one digit, and one special character
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }
    
    public boolean admin_exist(String email){
        String query = "SELECT * FROM admin WHERE email = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    // Helper method to print the ResultSet in a table format
    private void printTable(ResultSet resultSet, String[] columns) throws SQLException {
        // Calculate the maximum length of each column
        int[] columnLengths = new int[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnLengths[i] = columns[i].length();
        }

        while (resultSet.next()) {
            for (int i = 0; i < columns.length; i++) {
                String value = resultSet.getString(columns[i]);
                columnLengths[i] = Math.max(columnLengths[i], value != null ? value.length() : 4); // Account for "null" strings
            }
        }

        // Print the column headers
        for (int i = 0; i < columns.length; i++) {
            System.out.print(padRight(columns[i], columnLengths[i] + 2));
        }
        System.out.println();

        // Print a separator line
        for (int i = 0; i < columns.length; i++) {
            System.out.print(padRight("", columnLengths[i] + 2).replace(" ", "-"));
        }
        System.out.println();

        // Print the rows
        resultSet.beforeFirst(); // Move the cursor back to the start
        while (resultSet.next()) {
            for (int i = 0; i < columns.length; i++) {
                String value = resultSet.getString(columns[i]);
                System.out.print(padRight(value != null ? value : "null", columnLengths[i] + 2));
            }
            System.out.println();
        }
    }

    // Helper method to pad strings to the right
    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    // Method to view all users
    public void viewUsers() throws SQLException {
        String query = "SELECT email, full_name FROM User";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            String[] columns = {"email", "full_name"};
            printTable(resultSet, columns);
        }
    }

    // Method to view all accounts
    public void viewAccounts() throws SQLException {
        String query = "SELECT account_number, email, balance FROM Accounts";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            String[] columns = {"account_number", "email", "balance"};
            printTable(resultSet, columns);
        }
    }

    // Method to view all transactions
    public void viewTransactions() throws SQLException {
        String query = "SELECT id, email, account_number, transaction_type, amount, transfer_account_from, transfer_account_to, timestamp FROM Transactions";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            String[] columns = {"id", "email", "account_number", "transaction_type", "amount", "transfer_account_from", "transfer_account_to", "timestamp"};
            printTable(resultSet, columns);
        }
    }

    // Method to search transactions by email
    public void searchTransactionsByEmail(String email) throws SQLException {
        String query = "SELECT id, account_number, transaction_type, amount, transfer_account_from, transfer_account_to, timestamp FROM Transactions WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            String[] columns = {"id", "account_number", "transaction_type", "amount", "transfer_account_from", "transfer_account_to", "timestamp"};
            printTable(resultSet, columns);
        }
    }

    // Method to search transactions by account number
    public void searchTransactionsByAccountNumber(long accountNumber) throws SQLException {
        String query = "SELECT id, email, transaction_type, amount, transfer_account_from, transfer_account_to, timestamp FROM Transactions WHERE account_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            preparedStatement.setLong(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            String[] columns = {"id", "email", "transaction_type", "amount", "transfer_account_from", "transfer_account_to", "timestamp"};
            printTable(resultSet, columns);
        }
    }

}
