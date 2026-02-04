package repositories;

import entities.Technician;

public interface TechnicianRepository {
    Technician findByIdAndPin(String id, String pin);
}
