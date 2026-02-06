package controllers;

import entities.ATM;
import entities.User;
import services.ATMService;
import services.TransactionService;
import services.UserService;

import java.util.Scanner;

public class UserMenuController {

    private final UserService userService;
    private final ATMService atmService;
    private final TransactionService transactionService;
    private final User user;
    private final Scanner scanner = new Scanner(System.in);

    public UserMenuController(
            UserService userService,
            ATMService atmService,
            TransactionService transactionService,
            User user
    ) {
        this.userService = userService;
        this.atmService = atmService;
        this.transactionService = transactionService;
        this.user = user;
    }

    public void start() {
        ATM atm = atmService.getATM();

        while (true) {
            System.out.println("\n=== USER MENU ===");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. View Transactions");
            System.out.println("0. Logout");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> checkBalance();
                case "2" -> withdraw(atm);
                case "3" -> deposit(atm);
                case "4" -> viewTransactions();
                case "0" -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void checkBalance() {
        double balance = userService.getBalance(user);
        System.out.println("Current balance: " + balance + " €");
    }

    private void withdraw(ATM atm) {
        System.out.print("Enter amount (€): ");
        String input = scanner.nextLine();

        if (!input.matches("\\d+")) {
            System.out.println("Invalid amount.");
            return;
        }

        int amount = Integer.parseInt(input);

        // ✅ allow combinations, only multiples of 10
        if (amount <= 0 || amount % 10 != 0) {
            System.out.println("ATM supports withdrawals in multiples of 10€.");
            return;
        }

        double balance = userService.getBalance(user);
        if (balance < amount) {
            System.out.println("Insufficient balance.");
            return;
        }

        if (!atm.canWithdraw(amount)) {
            System.out.println("ATM has insufficient cash.");
            return;
        }

        boolean wantsReceipt;
        while (true) {
            System.out.print("Print receipt? (y/n): ");
            String print = scanner.nextLine().trim().toLowerCase();
            if (print.equals("y") || print.equals("n")) {
                wantsReceipt = print.equals("y");
                break;
            }
            System.out.println("Please enter only 'y' or 'n'.");
        }

        if (wantsReceipt && !atm.canPrintReceipt()) {
            while (true) {
                String reason;
                if (atm.getPaper() <= 0 && atm.getInk() <= 0) {
                    reason = "no paper or ink";
                } else if (atm.getPaper() <= 0) {
                    reason = "no paper";
                } else {
                    reason = "no ink";
                }
                System.out.print("Receipt unavailable (" + reason + "). Continue without receipt? (y/n): ");
                String cont = scanner.nextLine().trim().toLowerCase();
                if (cont.equals("y")) {
                    wantsReceipt = false;
                    break;
                }
                if (cont.equals("n")) {
                    System.out.println("Transaction cancelled.");
                    return;
                }
                System.out.println("Please enter only 'y' or 'n'.");
            }
        }

        if (!atm.withdraw(amount)) {
            System.out.println("ATM cannot dispense this amount.");
            return;
        }
        userService.withdraw(user, amount);

        var transaction = transactionService.record("WITHDRAW", amount, user.getBalance(), user.getCardNumber());
        if (wantsReceipt) {
            atm.useReceiptResources();
            transactionService.printReceipt(transaction);
        }

        atmService.save();
    }


    private void deposit(ATM atm) {
        System.out.print("Enter amount (€): ");
        String input = scanner.nextLine();

        if (!input.matches("\\d+(\\.\\d+)?")) {
            System.out.println("Invalid amount.");
            return;
        }

        double amount = Double.parseDouble(input);

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        boolean wantsReceipt;
        while (true) {
            System.out.print("Print receipt? (y/n): ");
            String print = scanner.nextLine().trim().toLowerCase();

            if (print.equals("y") || print.equals("n")) {
                wantsReceipt = print.equals("y");
                break;
            }
            System.out.println("Please enter 'y' or 'n' only.");
        }

        if (wantsReceipt && !atm.canPrintReceipt()) {
            while (true) {
                String reason;
                if (atm.getPaper() <= 0 && atm.getInk() <= 0) {
                    reason = "no paper or ink";
                } else if (atm.getPaper() <= 0) {
                    reason = "no paper";
                } else {
                    reason = "no ink";
                }
                System.out.print("Receipt unavailable (" + reason + "). Continue without receipt? (y/n): ");
                String cont = scanner.nextLine().trim().toLowerCase();
                if (cont.equals("y")) {
                    wantsReceipt = false;
                    break;
                }
                if (cont.equals("n")) {
                    System.out.println("Transaction cancelled.");
                    return;
                }
                System.out.println("Please enter 'y' or 'n'.");
            }
        }

        userService.deposit(user, amount);
        var transaction = transactionService.record("DEPOSIT", amount, user.getBalance(), user.getCardNumber());
        if (wantsReceipt) {
            atm.useReceiptResources();
            transactionService.printReceipt(transaction);
        }

        atmService.save();
    }

    private void viewTransactions() {
        var transactions = transactionService.getByCard(user.getCardNumber());
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.println("=== TRANSACTION HISTORY ===");
        for (var t : transactions) {
            System.out.println(
                    "Time: " + t.getTime()
                            + " | Type: " + t.getType()
                            + " | Amount: " + t.getAmount()
                            + " | Balance: " + t.getBalanceAfter()
            );
        }
    }
}
