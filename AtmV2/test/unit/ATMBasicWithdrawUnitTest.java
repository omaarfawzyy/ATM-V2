package unit;

import entities.ATM;

public class ATMBasicWithdrawUnitTest {

    public static void main(String[] args) {
        System.out.println("=== ATM Basic Withdraw Unit Test Started ===");

        ATM atm = new ATM();
        atm.addCash(50, 1);

        int initialCash = atm.getTotalCash();
        int amount = 50;

        boolean can = atm.canWithdraw(amount);
        boolean withdrew = atm.withdraw(amount);
        int finalCash = atm.getTotalCash();

        if (can && withdrew && finalCash == initialCash - amount) {
            System.out.println("✅ UNIT TEST PASSED");
        } else {
            System.out.println("❌ UNIT TEST FAILED");
            System.out.println("canWithdraw: " + can);
            System.out.println("withdraw: " + withdrew);
            System.out.println("initial cash: " + initialCash);
            System.out.println("final cash: " + finalCash);
        }

        System.out.println("=== ATM Basic Withdraw Unit Test Finished ===");
    }
}
