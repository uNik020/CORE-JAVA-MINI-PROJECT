package com.BankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class User {
	 private Connection connection;
	    private Scanner scanner;

	    public User(Connection connection, Scanner scanner){
	        this.connection = connection;
	        this.scanner = scanner;
	    }

	    public void register(){
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
	        
	        if(user_exist(email)) {
	            System.out.println("User Already Exists for this Email Address!!");
	            return;
	        }
	        String register_query = "INSERT INTO User(full_name, email, password) VALUES(?, ?, ?)";
	        try {
	            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
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

	    public String login(){
	        scanner.nextLine();
	        System.out.print("Email: ");
	        String email = scanner.nextLine();
	        System.out.print("Password: ");
	        String password = scanner.nextLine();
	        String login_query = "SELECT * FROM User WHERE email = ? AND password = ?";
	        try{
	            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
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
	    
	    public boolean user_exist(String email){
	        String query = "SELECT * FROM user WHERE email = ?";
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
	}
