package org.example.objects;

public class Book extends Product {
    private String genre;

    public Book(int id, double price, String name, String genre) {
        super(id, price, name);
        this.genre = genre;
    }

    @Override
    public String getExtraField() {
        return genre;
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", name='" + name + "', price=" + price + ", genre='" + genre + "'}";
    }
}

