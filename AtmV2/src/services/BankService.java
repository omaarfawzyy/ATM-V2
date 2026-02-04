package services;

import entities.User;
import repositories.UserRepository;

public class BankService {

    private final UserRepository userRepository;

    public BankService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String card, String pin) {
        return userRepository.findByCardAndPin(card, pin);
    }

    public double getBalance(String card) {
        User user = userRepository.findByCard(card);
        return user == null ? -1 : user.getBalance();
    }

    public boolean debit(String card, double amount) {
        if (amount <= 0) return false;

        User user = userRepository.findByCard(card);
        if (user == null) return false;
        if (user.getBalance() < amount) return false;

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);
        return true;
    }

    public boolean credit(String card, double amount) {
        if (amount <= 0) return false;

        User user = userRepository.findByCard(card);
        if (user == null) return false;

        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
        return true;
    }
}
