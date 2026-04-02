import model.Account;
import model.Transaction;
import model.User;
import repository.AccountRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import service.AccountService;
import service.AuthService;
import service.TransactionService;
import util.InputUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static AuthService authService;
    private static AccountService accountService;
    private static TransactionService transactionService;
    private static User loggedInUser = null;

    public static void main(String[] args) {
        // Initialize Components
        UserRepository userRepository = new UserRepository();
        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        authService = new AuthService(userRepository);
        accountService = new AccountService(accountRepository);
        transactionService = new TransactionService(accountRepository, transactionRepository);

        String bankName = "Digital Banking System";
        try (java.io.FileInputStream fis = new java.io.FileInputStream("../resources/config.properties")) {
            java.util.Properties props = new java.util.Properties();
            props.load(fis);
            bankName = props.getProperty("bank.name", bankName);
        } catch (Exception e) {
            // Ignore if config file is missing
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Welcome to " + bankName + " ===");

        while (true) {
            if (loggedInUser == null) {
                showMainMenu(scanner);
            } else {
                showUserMenu(scanner);
            }
        }
    }

    private static void showMainMenu(Scanner scanner) {
        System.out.println("\n1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        int choice = InputUtil.readInt(scanner, "Enter choice: ");

        switch (choice) {
            case 1:
                registerUser(scanner);
                break;
            case 2:
                loginUser(scanner);
                break;
            case 3:
                System.out.println("Exiting... Thank you for banking with us!");
                System.out.println();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void showUserMenu(Scanner scanner) {
        System.out.println("\n--- User Dashboard: " + loggedInUser.getName() + " ---");
        System.out.println();
        System.out.println("1. View My Accounts");
        System.out.println("2. Open New Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Transfer Funds");
        System.out.println("6. View Transaction History");
        System.out.println("7. Apply Interest (Savings Only)");
        System.out.println("8. Logout");
        int choice = InputUtil.readInt(scanner, "Enter choice: ");

        switch (choice) {
            case 1:
                viewAccounts();
                break;
            case 2:
                openAccount(scanner);
                break;
            case 3:
                deposit(scanner);
                break;
            case 4:
                withdraw(scanner);
                break;
            case 5:
                transfer(scanner);
                break;
            case 6:
                viewHistory(scanner);
                break;
            case 7:
                applyInterest(scanner);
                break;
            case 8:
                loggedInUser = null;
                System.out.println("Logged out successfully.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void registerUser(Scanner scanner) {
        String name = InputUtil.readString(scanner, "Enter Name: ");
        String email = InputUtil.readString(scanner, "Enter Email: ");
        String password = InputUtil.readString(scanner, "Enter Password: ");

        try {
            User user = authService.register(name, email, password);
            System.out.println("Registration successful! Your User ID is: " + user.getUserId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void loginUser(Scanner scanner) {
        String email = InputUtil.readString(scanner, "Enter Email: ");
        String password = InputUtil.readString(scanner, "Enter Password: ");

        try {
            loggedInUser = authService.login(email, password);
            System.out.println("Login successful!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAccounts() {
        List<Account> accounts = accountService.getUserAccounts(loggedInUser.getUserId());
        if (accounts.isEmpty()) {
            System.out.println("You don't have any accounts yet.");
            return;
        }
        System.out.println("--- Your Accounts ---");
        for (Account acc : accounts) {
            System.out.println("Acc No: " + acc.getAccountNumber() + " | Type: " + acc.getClass().getSimpleName()
                    + " | Balance: Rs." + acc.getBalance());
        }
    }

    private static void openAccount(Scanner scanner) {
        System.out.println("Select Account Type:\n\n1. Savings\n2. Current");
        int type = InputUtil.readInt(scanner, "Enter type (1/2): ");

        try {
            Account newAcc = accountService.createAccount(loggedInUser.getUserId(), type);
            System.out.println("Account opened successfully! Account Number: " + newAcc.getAccountNumber());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deposit(Scanner scanner) {
        String accNo = InputUtil.readString(scanner, "Enter Account Number: ");
        double amount = InputUtil.readDouble(scanner, "Enter Amount to Deposit: ");

        try {
            transactionService.deposit(loggedInUser.getUserId(), accNo, amount);
            System.out.println("Amount deposited successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void withdraw(Scanner scanner) {
        String accNo = InputUtil.readString(scanner, "Enter Account Number: ");
        double amount = InputUtil.readDouble(scanner, "Enter Amount to Withdraw: ");

        try {
            transactionService.withdraw(loggedInUser.getUserId(), accNo, amount);
            System.out.println("Amount withdrawn successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void transfer(Scanner scanner) {
        String fromAcc = InputUtil.readString(scanner, "Enter Source Account Number: ");
        String toAcc = InputUtil.readString(scanner, "Enter Destination Account Number: ");
        double amount = InputUtil.readDouble(scanner, "Enter Amount to Transfer: ");

        try {
            transactionService.transfer(loggedInUser.getUserId(), fromAcc, toAcc, amount);
            System.out.println("Transfer successful!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewHistory(Scanner scanner) {
        String accNo = InputUtil.readString(scanner, "Enter Account Number: ");
        try {
            List<Transaction> history = transactionService.getTransactionHistory(loggedInUser.getUserId(), accNo);
            if (history.isEmpty()) {
                System.out.println("No transactions found.");
                return;
            }

            System.out.println();
            System.out.println("--- Transaction History ---");
            System.out.println();
            for (Transaction txn : history) {
                System.out.println(txn);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void applyInterest(Scanner scanner) {
        String accNo = InputUtil.readString(scanner, "Enter Savings Account Number: ");
        try {
            accountService.applyInterestToAccount(loggedInUser.getUserId(), accNo);
            System.out.println("Interest applied successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
