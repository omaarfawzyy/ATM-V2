package entities;

public class User {

    private String id;
    private String name;
    private String cardNumber;
    private String pin;
    private double balance;

    public User(String id, String name, String cardNumber, String pin, double balance) {
        this.id = id;
        this.name = name;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
