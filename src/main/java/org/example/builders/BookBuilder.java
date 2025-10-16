package org.example.builders;

import org.example.objects.Book;

public class BookBuilder extends ProductBuilder<BookBuilder> {
    private String genre;

    public BookBuilder setExtraField(String genre) {
        this.genre = genre;
        return this;
    }

    @Override
    public Book build() {
        return new Book(id, price, name, genre);
    }
}

