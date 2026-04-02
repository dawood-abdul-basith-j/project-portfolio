package service;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import repository.AccountRepository;
import repository.TransactionRepository;
import model.Account;
import model.Transaction;
import util.IDGenerator;

import java.util.List;
import java.util.Optional;

public class TransactionService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    private void validateInput(double amount, double limit, String typeName) throws InvalidAmountException {
        if (amount <= 0 || Double.isInfinite(amount) || Double.isNaN(amount)) {
            throw new InvalidAmountException("Amount must be a positive valid number.");
        }
        if (amount > limit) {
            throw new InvalidAmountException(typeName + " limit exceeded. Max allowed is Rs." + limit);
        }
    }

    public void deposit(String userId, String accountId, double amount) throws InvalidAmountException {
        validateInput(amount, 100000.0, "Deposit");
        Optional<Account> accOpt = accountRepository.findByAccountNumber(accountId);
        if (accOpt.isPresent()) {
            Account acc = accOpt.get();
            if (!acc.getUserId().equals(userId)) {
                throw new IllegalArgumentException("Unauthorized access to account.");
            }
            acc.deposit(amount);
            transactionRepository.save(new Transaction(IDGenerator.generateTransactionId(), accountId, amount, Transaction.TransactionType.DEPOSIT));
        } else {
            throw new IllegalArgumentException("Account not found.");
        }
    }

    public void withdraw(String userId, String accountId, double amount) throws InsufficientBalanceException, InvalidAmountException, IllegalArgumentException {
        validateInput(amount, 5000.0, "Withdrawal");
        Optional<Account> accOpt = accountRepository.findByAccountNumber(accountId);
        if (accOpt.isPresent()) {
            Account acc = accOpt.get();
            if (!acc.getUserId().equals(userId)) {
                throw new IllegalArgumentException("Unauthorized access to account.");
            }
            acc.withdraw(amount);
            // Save as negative amount to reflect a deduction in the history
            transactionRepository.save(new Transaction(IDGenerator.generateTransactionId(), accountId, -amount, Transaction.TransactionType.WITHDRAWAL));
        } else {
            throw new IllegalArgumentException("Account not found.");
        }
    }

    public void transfer(String userId, String fromAccountId, String toAccountId, double amount) throws InsufficientBalanceException, InvalidAmountException, IllegalArgumentException {
        validateInput(amount, 50000.0, "Transfer");
        
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer funds to the same account.");
        }
        
        Optional<Account> fromOpt = accountRepository.findByAccountNumber(fromAccountId);
        Optional<Account> toOpt = accountRepository.findByAccountNumber(toAccountId);
        
        if (fromOpt.isPresent() && toOpt.isPresent()) {
            Account fromAcc = fromOpt.get();
            Account toAcc = toOpt.get();
            
            if (!fromAcc.getUserId().equals(userId)) {
                throw new IllegalArgumentException("Unauthorized access to source account.");
            }
            
            fromAcc.withdraw(amount);
            
            toAcc.deposit(amount);
            
            // For the sender, record the transfer as negative
            transactionRepository.save(new Transaction(IDGenerator.generateTransactionId(), fromAccountId, -amount, Transaction.TransactionType.TRANSFER));
            // For the receiver, record the transfer as positive
            transactionRepository.save(new Transaction(IDGenerator.generateTransactionId(), toAccountId, amount, Transaction.TransactionType.TRANSFER));
        } else {
            throw new IllegalArgumentException("One or both accounts not found.");
        }
    }

    public List<Transaction> getTransactionHistory(String userId, String accountId) {
        Optional<Account> accOpt = accountRepository.findByAccountNumber(accountId);
        if (accOpt.isPresent() && accOpt.get().getUserId().equals(userId)) {
            return transactionRepository.findByAccountId(accountId);
        } else {
            throw new IllegalArgumentException("Account not found or unauthorized access.");
        }
    }
}
