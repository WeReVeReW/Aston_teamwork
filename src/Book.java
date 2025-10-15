public class Book extends Product {
    private String genre;

    public Book(int id, String name, double price, String genre) {
        super(id, name, price);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", name='" + name + "', price=" + price + ", genre='" + genre + "'}";
    }
}

