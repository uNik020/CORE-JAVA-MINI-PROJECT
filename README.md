# CORE-JAVA-MINI-PROJECT

**BANKING SYSTEM**                    
    This is a simple core java mini project built using java8 and Eclipse IDE. It is totally based on Terminal . No UI available.



  _**Overview**_
  
    This project is a simple Banking System implemented in Java, using a MySQL database for data storage. It allows users to register, log in, open accounts, and perform various banking transactions like debit,  credit, and money transfers. Additionally, an admin interface is provided to manage users, view accounts, and monitor transactions.

**Features**
  
1.User Features
  
    Registration and Login: Users can register and log in to their accounts.
    Account Management: Users can open a new bank account if they don't already have one.
  
2.Transactions:
    
    Debit Money: Users can withdraw money from their accounts.
    Credit Money: Users can deposit money into their accounts.
    Transfer Money: Users can transfer money to another account.
    Check Balance: Users can check their account balance.
    
  3.Admin Features
    
    Admin Registration and Login: Admins can register and log in to the admin panel.
      View Users: Admins can view all registered users.
      View Accounts: Admins can view all bank accounts.
      View Transactions: Admins can view all transactions.
      Search Transactions: Admins can search for transactions by user email or account number.

**Database Schema**

The system uses a MySQL database with the following tables:
    
   1.User: 
    
      Stores user information (email, full_name, password, etc.).
      Accounts: Stores account information (account_number, email, balance, etc.).
      Transactions: Stores transaction details (id, email, account_number, transaction_type, amount, etc.).
    
   2.Setup and Installation
    
   Clone the Repository:
    sh
    Copy code
    git clone https://github.com/uNik020/Banking-System.git
    cd banking-system

**Setup the Database:**
    
    Create a MySQL database named banking_system.
    Create the User, Accounts, and Transactions tables as per the schema.


Configure Database Connection:

    Update the database URL, username, and password in BankingApp.java.

Compile and Run:

Compile the Java files:

    sh
    Copy code
    javac -d bin src/com/BankingSystem/*.java

**Run the application:**

    sh
    Copy code
    java -cp bin com.BankingSystem.BankingApp

**Usage**

    Running the Application
    Launch the application from the terminal. The main menu will provide options for user and admin functionalities.

**User Operations**

    Register or log in as a user to access account management and transaction features.

**Admin Operations**

    Register or log in as an admin to manage users, view accounts, and monitor transactions.

**Contribution**

    Feel free to fork this project, submit issues and pull requests. Contributions are welcome!

**License**
    
      This project is licensed under the MIT License.
