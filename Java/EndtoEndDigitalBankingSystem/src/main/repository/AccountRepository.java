package repository;

import model.Account;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountRepository {
    private Map<String, Account> accountDb = new HashMap<>();

    public void save(Account account) {
        accountDb.put(account.getAccountNumber(), account);
    }

    public Optional<Account> findByAccountNumber(String accountNumber) {
        return Optional.ofNullable(accountDb.get(accountNumber));
    }

    public List<Account> findAccountsByUserId(String userId) {
        return accountDb.values().stream()
                .filter(acc -> acc.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
