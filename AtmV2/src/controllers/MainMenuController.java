package controllers;

import entities.Technician;
import entities.User;
import services.AuthenticationService;
import services.ATMService;
import services.TechnicianService;
import services.UserService;
import services.TransactionService;

import java.util.Scanner;

public class MainMenuController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ATMService atmService;
    private final TechnicianService technicianService;
    private final TransactionService transactionService;

    private final Scanner scanner = new Scanner(System.in);

    public MainMenuController(
            AuthenticationService authenticationService,
            UserService userService,
            ATMService atmService,
            TechnicianService technicianService,
            TransactionService transactionService
    ) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.atmService = atmService;
        this.technicianService = technicianService;
        this.transactionService = transactionService;
    }

    public void start() {
        while (true) {
            System.out.println("1. User");
            System.out.println("2. Technician");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> userLogin();
                case "2" -> technicianLogin();
                case "0" -> System.exit(0);
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void userLogin() {
        System.out.print("Card Number: ");
        String cardNumber = scanner.nextLine();

        System.out.print("PIN: ");
        String pin = scanner.nextLine();

        User user = authenticationService.authenticateUser(cardNumber, pin);

        if (user == null) {
            System.out.println("Authentication failed");
            return;
        }

        new UserMenuController(
                userService,
                atmService,
                transactionService,
                user
        ).start();
    }

    private void technicianLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Technician technician =
                authenticationService.authenticateTechnician(username, password);

        if (technician == null) {
            System.out.println("Authentication failed");
            return;
        }

        new TechnicianMenuController(
                atmService,
                technicianService
        ).start();
    }
}
