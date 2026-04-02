package model;

import exception.InsufficientBalanceException;

public class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount() {
        super();
        this.overdraftLimit = 500.0; // default 500 overdraft
    }

    public CurrentAccount(String accountNumber, String userId, double balance, double overdraftLimit) {
        super(accountNumber, userId, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > 0 && (this.balance + this.overdraftLimit) >= amount) {
            this.balance -= amount;
        } else {
            throw new InsufficientBalanceException("Amount exceeds overdraft limit in Current Account.");
        }
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}
