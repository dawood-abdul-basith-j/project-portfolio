package repository;

import model.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionRepository {
    private List<Transaction> transactionDb = new ArrayList<>();

    public void save(Transaction transaction) {
        transactionDb.add(transaction);
    }

    public List<Transaction> findByAccountId(String accountId) {
        return transactionDb.stream()
                .filter(txn -> txn.getAccountId().equals(accountId))
                .collect(Collectors.toList());
    }
}
