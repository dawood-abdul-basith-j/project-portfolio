package model;

import java.time.LocalDateTime;

public class Transaction {
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    private String transactionId;
    private String accountId;
    private double amount;
    private TransactionType type;
    private LocalDateTime timestamp;

    public Transaction(String transactionId, String accountId, double amount, TransactionType type) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() { return transactionId; }
    public String getAccountId() { return accountId; }
    public double getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "Transaction[" + transactionId + "] " + timestamp + " - " + type + ": $" + amount + " (Acc: " + accountId + ")";
    }
}
