package app.model;

public class Stock {
    private final int id;
    private final String name;
    private double price;
    private int number;

    public Stock(int id, String name, double price, int number) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public boolean changeNumber(int delta) {
        if (number + delta < 0) {
            return false;
        }

        number += delta;

        return true;
    }

    public boolean changePrice(double delta) {
        if (price + delta < 0) {
            return false;
        }

        price += delta;

        return true;
    }
}
