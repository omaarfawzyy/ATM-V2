package entities;

import java.util.*;

public class ATM {

    private final Map<Integer, Integer> cash = new HashMap<>();
    private int paper = 100;
    private int ink = 100;
    private String firmwareVersion = "1.0.0";

    public ATM() {
        cash.put(10, 0);
        cash.put(20, 0);
        cash.put(50, 0);
        cash.put(100, 0);
    }

    public Map<Integer, Integer> getCash() {
        return cash;
    }

    public void addCash(int note, int count) {
        if (!cash.containsKey(note)) return;
        if (count <= 0) return;
        cash.put(note, cash.get(note) + count);
    }

    public boolean canWithdraw(double amount) {
        int intAmount = toValidAmount(amount);
        if (intAmount <= 0) return false;
        return getTotalCash() >= intAmount && canMakeAmount(intAmount);
    }

    private int toValidAmount(double amount) {
        int intAmount = (int) amount;
        if (amount != intAmount) return -1;
        if (intAmount <= 0) return -1;
        if (intAmount % 10 != 0) return -1;
        return intAmount;
    }

    private int[] getNotesDescending() {
        return cash.keySet().stream()
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
    }

    // ✅ check combinations WITHOUT modifying cash
    private boolean canMakeAmount(int amount) {
        return computeDispense(amount) != null;
    }

    private int[] computeDispense(int amount) {
        int[] notes = getNotesDescending();
        int[] counts = new int[notes.length];
        for (int i = 0; i < notes.length; i++) {
            counts[i] = cash.get(notes[i]);
        }

        int[] used = new int[notes.length];
        java.util.HashSet<Long> memoFail = new java.util.HashSet<>();
        if (findCombination(0, amount, notes, counts, used, memoFail)) {
            return used;
        }
        return null;
    }

    private boolean findCombination(
            int idx,
            int remaining,
            int[] notes,
            int[] counts,
            int[] used,
            java.util.HashSet<Long> memoFail
    ) {
        if (remaining == 0) return true;
        if (idx >= notes.length) return false;

        long key = (((long) idx) << 32) | (remaining & 0xffffffffL);
        if (memoFail.contains(key)) return false;

        int note = notes[idx];
        int maxUse = Math.min(counts[idx], remaining / note);

        for (int use = maxUse; use >= 0; use--) {
            used[idx] = use;
            int nextRemaining = remaining - (use * note);
            if (findCombination(idx + 1, nextRemaining, notes, counts, used, memoFail)) {
                return true;
            }
        }

        used[idx] = 0;
        memoFail.add(key);
        return false;
    }

    // ✅ withdraw ONLY if exact combination exists
    public boolean withdraw(double amount) {
        int intAmount = toValidAmount(amount);
        if (intAmount <= 0) return false;

        int[] used = computeDispense(intAmount);
        if (used == null) return false;

        int[] notes = getNotesDescending();
        for (int i = 0; i < notes.length; i++) {
            int note = notes[i];
            int available = cash.get(note);
            cash.put(note, available - used[i]);
        }

        return true;
    }

    public int getTotalCash() {
        int total = 0;
        for (var e : cash.entrySet()) {
            total += e.getKey() * e.getValue();
        }
        return total;
    }

    public void printCash() {
        System.out.println("Current cash:");
        System.out.println("10€ x " + cash.get(10));
        System.out.println("20€ x " + cash.get(20));
        System.out.println("50€ x " + cash.get(50));
        System.out.println("100€ x " + cash.get(100));
    }

    public boolean canPrintReceipt() {
        return paper > 0 && ink > 0;
    }

    public void useReceiptResources() {
        if (paper <= 0 || ink <= 0) return;
        paper--;
        ink--;
    }

    public int getPaper() {
        return paper;
    }

    public void addPaper(int amount) {
        if (amount <= 0) return;
        paper += amount;
    }

    public int getInk() {
        return ink;
    }

    public void addInk(int amount) {
        if (amount <= 0) return;
        ink += amount;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }
}
