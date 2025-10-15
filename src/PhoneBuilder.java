public class PhoneBuilder extends ProductBuilder<PhoneBuilder> {
    private String color;

    public PhoneBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public Phone build() {
        return new Phone(id, name, price, color);
    }
}

