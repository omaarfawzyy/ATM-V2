package repositories;

import entities.User;

public interface UserRepository {
    User findByCardAndPin(String card, String pin);
    User findByCard(String card);
    void save(User user);
}
