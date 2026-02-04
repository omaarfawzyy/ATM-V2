package entities;

public class Technician {

    private final String id;
    private final String pin;

    public Technician(String id, String pin) {
        this.id = id;
        this.pin = pin;
    }

    public String getId() {
        return id;
    }

    public String getPin() {
        return pin;
    }
}
