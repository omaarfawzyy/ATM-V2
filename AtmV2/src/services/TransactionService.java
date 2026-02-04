package services;

import entities.Transaction;
import repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction record(String type, double amount, double balanceAfter, String cardNumber) {
        Transaction transaction = new Transaction(type, amount, balanceAfter, cardNumber);
        transactionRepository.save(transaction);
        return transaction;
    }

    public void printReceipt(Transaction transaction) {
        System.out.println(transaction.getReceipt());
    }

    public List<Transaction> getByCard(String cardNumber) {
        List<Transaction> all = transactionRepository.loadAll();
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : all) {
            if (cardNumber != null && cardNumber.equals(t.getCardNumber())) {
                filtered.add(t);
            }
        }
        return filtered;
    }
}
