package integration;

import entities.ATM;
import entities.User;
import repositories.json.JsonATMRepository;
import repositories.json.JsonUserRepository;
import services.ATMService;
import services.BankService;
import services.UserService;

public class ATMIntegrationTest {

    public static void main(String[] args) {

        System.out.println("=== ATM Integration Test Started ===");

        // Repositories
        var userRepository = new JsonUserRepository();
        var atmRepository = new JsonATMRepository();

        // Services
        var atmService = new ATMService(atmRepository);
        var bankService = new BankService(userRepository);
        var userService = new UserService(bankService);

        // Load ATM and add cash
        ATM atm = atmService.getATM();
        atm.addCash(100, 5); // 500 total

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

        int amount = 100;

        // ✅ CORRECT FLOW
        if (atm.canWithdraw(amount)) {
            atm.withdraw(amount);
            userService.withdraw(user, amount);
        }

        int finalCash = atm.getTotalCash();

        // Verification
        if (user.getBalance() == 900 && finalCash == initialCash - 100) {
            System.out.println("✅ INTEGRATION TEST PASSED");
        } else {
            System.out.println("❌ INTEGRATION TEST FAILED");
            System.out.println("User balance: " + user.getBalance());
            System.out.println("ATM cash: " + finalCash);
        }

        System.out.println("=== ATM Integration Test Finished ===");
    }
}
