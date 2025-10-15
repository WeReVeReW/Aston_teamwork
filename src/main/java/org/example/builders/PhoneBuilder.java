package org.example.builders;

import org.example.objects.Phone;

public class PhoneBuilder extends ProductBuilder<PhoneBuilder> {
    private String color;

    public PhoneBuilder setExtraField(String color) {
        this.color = color;
        return this;
    }

    @Override
    public Phone build() {
        return new Phone(id, price, name, color);
    }
}

