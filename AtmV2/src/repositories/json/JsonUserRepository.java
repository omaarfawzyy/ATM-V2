package repositories.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.User;
import repositories.UserRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUserRepository implements UserRepository {

    private static final String FILE_PATH = "Data/users.json";
    private final Gson gson = new Gson();

    @Override
    public User findByCardAndPin(String card, String pin) {
        try {
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(new FileReader(FILE_PATH), listType);

            if (users == null) return null;

            for (User user : users) {
                if (user.getCardNumber().equals(card)
                        && user.getPin().equals(pin)) {
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByCard(String card) {
        try {
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(new FileReader(FILE_PATH), listType);

            if (users == null) return null;

            for (User user : users) {
                if (user.getCardNumber().equals(card)) {
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(User updatedUser) {
        try {
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(new FileReader(FILE_PATH), listType);
            if (users == null) {
                users = new java.util.ArrayList<>();
            }

            boolean updated = false;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId().equals(updatedUser.getId())) {
                    users.set(i, updatedUser);
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                users.add(updatedUser);
            }

            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                gson.toJson(users, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
