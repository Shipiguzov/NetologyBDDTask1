package data;

public enum Cards {
    CARD1("5559 0000 0000 0001", 10000), CARD2 ("5559 0000 0000 0002", 10000);

    private final String number;
    private final int balance;

    Cards(String number, int balance) {
        this.number = number;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public int getBalance() {
        return balance;
    }
}
