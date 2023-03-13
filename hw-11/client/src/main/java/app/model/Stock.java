package app.model;

public class Stock {
    private final int id;
    private final double price;
    private final int number;

    public Stock(int id, double price, int number) {
        this.id = id;
        this.price = price;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }
}
