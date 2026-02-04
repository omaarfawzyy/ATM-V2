package repositories.json;

import entities.Transaction;
import repositories.TransactionRepository;
import utils.JsonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonTransactionRepository implements TransactionRepository {

    private static final String FILE_PATH = "Data/transactions.json";

    @Override
    public List<Transaction> loadAll() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        Transaction[] arr = JsonUtil.read(FILE_PATH, Transaction[].class);
        return arr == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(arr));
    }

    @Override
    public void save(Transaction transaction) {
        List<Transaction> all = loadAll();
        all.add(transaction);
        JsonUtil.write(FILE_PATH, all);
    }
}
