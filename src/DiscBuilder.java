public class DiscBuilder extends ProductBuilder<DiscBuilder> {
    private int year;

    public DiscBuilder setYear(int year) {
        this.year = year;
        return this;
    }

    @Override
    public Disc build() {
        return new Disc(id, name, price, year);
    }
}
