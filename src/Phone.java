public class Phone extends Product {
    private String color;

    public Phone(int id, String name, double price, String color) {
        super(id, name, price);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Phone{id=" + id + ", name='" + name + "', price=" + price + ", color='" + color + "'}";
    }
}
