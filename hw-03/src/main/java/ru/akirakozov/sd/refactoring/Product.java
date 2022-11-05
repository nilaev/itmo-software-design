package ru.akirakozov.sd.refactoring;

public class Product {

    private String name;
    private long price;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product))
            return false;
        Product that = (Product) o;
        return this.name.equals(that.name) && this.price == that.price;
    }

}
