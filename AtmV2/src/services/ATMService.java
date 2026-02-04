package services;

import entities.ATM;
import repositories.ATMRepository;

public class ATMService {

    private final ATMRepository atmRepository;
    private final ATM atm; // cached instance

    public ATMService(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
        this.atm = atmRepository.load(); // ✅ FIXED LINE
    }

    public ATM getATM() {
        return atm;
    }

    public void save() {
        atmRepository.save(atm);
    }
}
