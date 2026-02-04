package integration;

import entities.ATM;
import entities.User;
import repositories.json.JsonATMRepository;
import repositories.json.JsonUserRepository;
import services.ATMService;
import services.BankService;
import services.UserService;

public class ATMInsufficientCashIntegrationTest {

    public static void main(String[] args) {

        System.out.println("=== ATM Insufficient Cash Integration Test Started ===");

        // Repositories
        var userRepository = new JsonUserRepository();
        var atmRepository = new JsonATMRepository();

        // Services
        var atmService = new ATMService(atmRepository);
        var bankService = new BankService(userRepository);
        var userService = new UserService(bankService);

        // Load ATM WITHOUT adding cash
        ATM atm = atmService.getATM();
        int initialCash = atm.getTotalCash();

        // Create user
        User user = new User(
                "9999",
                "TestUser",
                "0000",
                "1000",
                1000.0
        );
        userRepository.save(user);

        double initialBalance = user.getBalance();
        int amount = initialCash + 1000;

        // ✅ CORRECT FLOW
        if (atm.canWithdraw(amount)) {
            atm.withdraw(amount);
            userService.withdraw(user, amount);
        }

        // Verification
        if (user.getBalance() == initialBalance &&
                atm.getTotalCash() == initialCash) {

            System.out.println("✅ INTEGRATION TEST PASSED");
        } else {
            System.out.println("❌ INTEGRATION TEST FAILED");
            System.out.println("User balance before: " + initialBalance);
            System.out.println("User balance after: " + user.getBalance());
            System.out.println("ATM cash before: " + initialCash);
            System.out.println("ATM cash after: " + atm.getTotalCash());
        }

        System.out.println("=== ATM Insufficient Cash Integration Test Finished ===");
    }
}
