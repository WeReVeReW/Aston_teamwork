public class BookBuilder extends ProductBuilder<BookBuilder> {
    private String genre;

    public BookBuilder setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    @Override
    public Book build() {
        return new Book(id, name, price, genre);
    }
}

