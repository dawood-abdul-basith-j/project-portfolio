package service;

import repository.AccountRepository;
import model.Account;
import model.CurrentAccount;
import model.SavingsAccount;
import util.IDGenerator;

import java.util.List;

public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(String userId, int typeSelection) throws IllegalArgumentException {
        String accountId = IDGenerator.generateAccountId();
        Account newAccount;
        if (typeSelection == 1) {
            newAccount = new SavingsAccount(accountId, userId, 0.0, 0.04);
        } else if (typeSelection == 2) {
            newAccount = new CurrentAccount(accountId, userId, 0.0, 500.0);
        } else {
            throw new IllegalArgumentException("Invalid account type selection.");
        }
        accountRepository.save(newAccount);
        return newAccount;
    }

    public List<Account> getUserAccounts(String userId) {
        return accountRepository.findAccountsByUserId(userId);
    }
}
