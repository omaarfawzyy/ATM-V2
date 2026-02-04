package services;

import entities.User;
public class UserService {

    private final BankService bankService;

    public UserService(BankService bankService) {
        this.bankService = bankService;
    }

    public double getBalance(User user) {
        double balance = bankService.getBalance(user.getCardNumber());
        if (balance >= 0) {
            user.setBalance(balance);
            return balance;
        }
        return user.getBalance();
    }

    public void withdraw(User user, double amount) {
        if (!bankService.debit(user.getCardNumber(), amount)) return;
        double balance = bankService.getBalance(user.getCardNumber());
        if (balance >= 0) {
            user.setBalance(balance);
        }
    }

    public void deposit(User user, double amount) {
        if (!bankService.credit(user.getCardNumber(), amount)) return;
        double balance = bankService.getBalance(user.getCardNumber());
        if (balance >= 0) {
            user.setBalance(balance);
        }
    }
}
