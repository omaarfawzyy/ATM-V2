package entities;

import java.time.LocalDateTime;

public class Transaction {

    private final String type;          // WITHDRAW / DEPOSIT
    private final double amount;
    private final double balanceAfter;
    private final String cardNumber;
    private final String time;

    public Transaction(String type, double amount, double balanceAfter, String cardNumber) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.cardNumber = cardNumber;
        this.time = LocalDateTime.now().toString();
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getTime() {
        return time;
    }

    public String getReceipt() {
        return """
        ===== ATM RECEIPT =====
        Type: %s
        Amount: %.2f €
        Balance After: %.2f €
        Time: %s
        =======================
        """.formatted(type, amount, balanceAfter, time);
    }
}
