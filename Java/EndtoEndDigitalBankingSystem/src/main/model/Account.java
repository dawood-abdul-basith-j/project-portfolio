package model;

public abstract class Account {

    protected String accountNumber;
    protected String userId;
    protected double balance;

    public Account() {
        this.accountNumber = "";
        this.userId = "";
        this.balance = 0.0;
    }

    public Account(String accountNumber, String userId, double balance) {
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.balance = balance;
    }

    public void deposit(double amount) throws exception.InvalidAmountException {
        if (amount > 0) {
            this.balance += amount;
        } else {
            throw new exception.InvalidAmountException("Deposit amount must be greater than zero.");
        }
    }

    public abstract void withdraw(double amount) throws exception.InsufficientBalanceException;

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getUserId() {
        return userId;
    }
}