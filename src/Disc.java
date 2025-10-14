public class Disc extends Product {
    private int year;

    public Disc(int id, String name, double price, int year) {
        super(id, name, price);
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Disc{id=" + id + ", name='" + name + "', price=" + price + ", year=" + year + "}";
    }
}
