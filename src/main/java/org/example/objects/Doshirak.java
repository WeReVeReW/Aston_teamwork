package org.example.objects;

public class Doshirak extends Product {
    private String taste;

    public Doshirak(int id, double price, String name, String taste) {
        super(id, price, name);
        this.taste = taste;
    }

    @Override
    public String getExtraField() {
        return taste;
    }

    @Override
    public String toString() {
        return "Doshirak{id=" + id + ", name='" + name + "', price=" + price + ", taste=" + taste + "}";
    }
}
