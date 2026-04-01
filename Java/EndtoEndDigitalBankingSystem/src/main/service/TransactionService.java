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

    public void deposit(String accountId, double amount) throws InvalidAmountException {
        Optional<Account> accOpt = accountRepository.findByAccountNumber(accountId);
        if (accOpt.isPresent()) {
            Account acc = accOpt.get();
            acc.deposit(amount);
            transactionRepository.save(new Transaction(IDGenerator.generateTransactionId(), accountId, amount, Transaction.TransactionType.DEPOSIT));
        } else {
            throw new IllegalArgumentException("Account not found.");
        }
    }

    public void withdraw(String accountId, double amount) throws InsufficientBalanceException, IllegalArgumentException {
        Optional<Account> accOpt = accountRepository.findByAccountNumber(accountId);
        if (accOpt.isPresent()) {
            Account acc = accOpt.get();
            acc.withdraw(amount);
            transactionRepository.save(new Transaction(IDGenerator.generateTransactionId(), accountId, amount, Transaction.TransactionType.WITHDRAWAL));
        } else {
            throw new IllegalArgumentException("Account not found.");
        }
    }

    public void transfer(String fromAccountId, String toAccountId, double amount) throws InsufficientBalanceException, InvalidAmountException, IllegalArgumentException {
        Optional<Account> fromOpt = accountRepository.findByAccountNumber(fromAccountId);
        Optional<Account> toOpt = accountRepository.findByAccountNumber(toAccountId);
        
        if (fromOpt.isPresent() && toOpt.isPresent()) {
            Account fromAcc = fromOpt.get();
            Account toAcc = toOpt.get();
            
            fromAcc.withdraw(amount);
            toAcc.deposit(amount);
            
            transactionRepository.save(new Transaction(IDGenerator.generateTransactionId(), fromAccountId, amount, Transaction.TransactionType.TRANSFER));
            transactionRepository.save(new Transaction(IDGenerator.generateTransactionId(), toAccountId, amount, Transaction.TransactionType.TRANSFER));
        } else {
            throw new IllegalArgumentException("One or both accounts not found.");
        }
    }

    public List<Transaction> getTransactionHistory(String accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}
