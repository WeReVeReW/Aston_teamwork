package org.example.objects;

public class Phone extends Product {
    private String color;

    public Phone(int id, double price, String name, String color) {
        super(id,price, name);
        this.color = color;
    }

    @Override
    public String getExtraField() {
        return color;
    }

    @Override
    public String toString() {
        return "Phone{id=" + id + ", name='" + name + "', price=" + price + ", color='" + color + "'}";
    }
}
