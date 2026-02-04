package services;

import entities.ATM;

public class TechnicianService {

    public TechnicianService() {
    }

    public void addPaper(ATM atm, int amount) {
        atm.addPaper(amount);
    }

    public void addInk(ATM atm, int amount) {
        atm.addInk(amount);
    }
}
