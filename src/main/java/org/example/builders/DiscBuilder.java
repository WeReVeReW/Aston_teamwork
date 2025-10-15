package org.example.builders;

import org.example.objects.Disc;

public class DiscBuilder extends ProductBuilder<DiscBuilder> {
    private int year;

    public DiscBuilder setExtraField(int year) {
        this.year = year;
        return this;
    }

    @Override
    public Disc build() {
        return new Disc(id, price, name, year);
    }
}
