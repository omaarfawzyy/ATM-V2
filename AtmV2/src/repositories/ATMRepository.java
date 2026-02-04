package repositories;

import entities.ATM;

public interface ATMRepository {
    ATM load();
    void save(ATM atm);
}
