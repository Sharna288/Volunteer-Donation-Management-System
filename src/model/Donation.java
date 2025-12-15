package model;

public class Donation {
    private String type;
    private String amount;
    private String bloodGroup;

    public Donation(String type, String amount, String bloodGroup) {
        this.type = type;
        this.amount = amount;
        this.bloodGroup = bloodGroup;
    }

    // Getters
    public String getType() { return type; }
    public String getAmount() { return amount; }
    public String getBloodGroup() { return bloodGroup; }
}
