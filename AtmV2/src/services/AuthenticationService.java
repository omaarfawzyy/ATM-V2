package services;

import entities.Technician;
import entities.User;
import repositories.TechnicianRepository;

public class AuthenticationService {

    private final BankService bankService;
    private final TechnicianRepository techRepo;

    public AuthenticationService(BankService bankService,
                                 TechnicianRepository techRepo) {
        this.bankService = bankService;
        this.techRepo = techRepo;
    }

    public User authenticateUser(String card, String pin) {
        return bankService.authenticate(card, pin);
    }

    public Technician authenticateTechnician(String id, String pin) {
        return techRepo.findByIdAndPin(id, pin);
    }
}
