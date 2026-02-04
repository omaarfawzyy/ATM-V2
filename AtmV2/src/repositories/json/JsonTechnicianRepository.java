package repositories.json;

import entities.Technician;
import repositories.TechnicianRepository;

public class JsonTechnicianRepository implements TechnicianRepository {

    private final Technician tech =
            new Technician("tech", "9999");

    @Override
    public Technician findByIdAndPin(String id, String pin) {
        return tech.getId().equals(id) && tech.getPin().equals(pin)
                ? tech
                : null;
    }
}
