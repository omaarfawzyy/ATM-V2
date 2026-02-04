package repositories.json;

import entities.ATM;
import repositories.ATMRepository;
import utils.JsonUtil;

import java.io.File;

public class JsonATMRepository implements ATMRepository {

    private static final String FILE_PATH = "Data/atm.json";

    @Override
    public ATM load() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            ATM atm = new ATM();      // ONLY FIRST RUN
            save(atm);
            return atm;
        }

        ATM atm = JsonUtil.read(FILE_PATH, ATM.class);

        if (atm == null) {
            atm = new ATM();
            save(atm);
        }

        return atm;
    }

    @Override
    public void save(ATM atm) {
        JsonUtil.write(FILE_PATH, atm);
    }
}
