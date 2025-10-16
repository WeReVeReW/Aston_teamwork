package org.example.objects;

public class Disc extends Product {
    private int year;

    public Disc(int id, double price, String name, int year) {
        super(id, price, name);
        this.year = year;
    }

    @Override
    public String getExtraField() {
        return String.valueOf(year);
    }

    @Override
    public String toString() {
        return "Disc{id=" + id + ", name='" + name + "', price=" + price + ", year=" + year + "}";
    }
}
