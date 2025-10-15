public class Doshirak extends Product {
    private int taste;

    public Doshirak(int id, String name, double price, int taste) {
        super(id, name, price);
        this.taste = taste;
    }

    public int getTaste() {
        return taste;
    }

    @Override
    public String toString() {
        return "Doshirak{id=" + id + ", name='" + name + "', price=" + price + ", taste=" + taste + "}";
    }
}
