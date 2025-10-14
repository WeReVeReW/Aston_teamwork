public class DoshirakBuilder extends ProductBuilder<DoshirakBuilder> {
    private int taste;

    public DoshirakBuilder setTaste(int taste) {
        this.taste = taste;
        return this;
    }

    @Override
    public Doshirak build() {
        return new Doshirak(id, name, price, taste);
    }
}

