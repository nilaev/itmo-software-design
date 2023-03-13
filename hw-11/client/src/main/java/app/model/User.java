package app.model;

import java.util.Map;

public class User {
    private final int id;
    private final String name;
    private double money;
    private final Map<Integer, Integer> stocks;

    public User(int id, String name, double money, Map<Integer, Integer> stocks) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.stocks = stocks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public Map<Integer, Integer> getStocks() {
        return stocks;
    }

    public boolean changeMoney(double delta) {
        if (money + delta < 0) return false;
        money += delta;
        return true;
    }
}
