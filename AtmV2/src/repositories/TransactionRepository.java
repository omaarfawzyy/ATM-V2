package repositories;

import entities.Transaction;
import java.util.List;

public interface TransactionRepository {
    List<Transaction> loadAll();
    void save(Transaction transaction);
}
