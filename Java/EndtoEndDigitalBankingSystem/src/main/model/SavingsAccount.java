package model;

import exception.InsufficientBalanceException;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount() {
        super();
        this.interestRate = 0.04; // default 4%
    }

    public SavingsAccount(String accountNumber, String userId, double balance, double interestRate) {
        super(accountNumber, userId, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
        } else {
            throw new InsufficientBalanceException("Insufficient balance in Savings Account.");
        }
    }

    public void applyInterest() {
        if (this.balance > 0) {
            double interest = this.balance * this.interestRate;
            this.balance += interest;
        }
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
