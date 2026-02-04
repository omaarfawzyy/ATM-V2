package controllers;

import entities.ATM;
import services.ATMService;
import services.TechnicianService;

import java.util.Scanner;

public class TechnicianMenuController {

    private final ATMService atmService;
    private final TechnicianService technicianService;
    private final Scanner scanner = new Scanner(System.in);

    public TechnicianMenuController(ATMService atmService, TechnicianService technicianService) {
        this.atmService = atmService;
        this.technicianService = technicianService;
    }

    public void start() {
        ATM atm = atmService.getATM();

        while (true) {
            System.out.println("1. Add Cash");
            System.out.println("2. Add Paper");
            System.out.println("3. Add Ink");
            System.out.println("4. Update Firmware");
            System.out.println("0. Logout");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addCash(atm);
                case "2" -> addPaper(atm);
                case "3" -> addInk(atm);
                case "4" -> updateFirmware(atm);
                case "0" -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void addCash(ATM atm) {
        atm.printCash();

        System.out.print("Enter note value: ");
        String noteInput = scanner.nextLine();

        if (!noteInput.matches("\\d+")) {
            System.out.println("Invalid note value.");
            return;
        }

        int note = Integer.parseInt(noteInput);

        // ✅ BLOCK unsupported notes
        if (note != 10 && note != 20 && note != 50 && note != 100) {
            System.out.println("ATM supports only 10€, 20€, 50€, 100€ notes.");
            return;
        }

        System.out.print("Enter quantity: ");
        String qtyInput = scanner.nextLine();

        if (!qtyInput.matches("\\d+")) {
            System.out.println("Invalid quantity.");
            return;
        }

        int quantity = Integer.parseInt(qtyInput);

        atm.addCash(note, quantity);
        atmService.save();

        System.out.println("Cash added successfully.");
    }

    private void addPaper(ATM atm) {
        System.out.println("Current paper available: " + atm.getPaper());
        System.out.print("Add paper amount: ");

        String input = scanner.nextLine();
        if (!input.matches("\\d+")) {
            System.out.println("Invalid amount.");
            return;
        }

        int amount = Integer.parseInt(input);
        technicianService.addPaper(atm, amount);
        atmService.save();

        System.out.println("Paper added successfully.");
    }

    private void addInk(ATM atm) {
        System.out.println("Current ink available: " + atm.getInk());
        System.out.print("Add ink amount: ");

        String input = scanner.nextLine();
        if (!input.matches("\\d+")) {
            System.out.println("Invalid amount.");
            return;
        }

        int amount = Integer.parseInt(input);
        technicianService.addInk(atm, amount);
        atmService.save();

        System.out.println("Ink added successfully.");
    }

    private void updateFirmware(ATM atm) {
        System.out.println("Current firmware version: " + atm.getFirmwareVersion());
        System.out.print("Enter new firmware version (x.y.z): ");

        String version = scanner.nextLine();

        if (!version.matches("\\d+\\.\\d+\\.\\d+")) {
            System.out.println("Invalid firmware version format.");
            return;
        }

        atm.setFirmwareVersion(version);
        atmService.save();
        System.out.println("Firmware updated successfully.");
    }
}
