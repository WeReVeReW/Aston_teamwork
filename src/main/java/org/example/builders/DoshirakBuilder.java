package org.example.builders;

import org.example.objects.Doshirak;

public class DoshirakBuilder extends ProductBuilder<DoshirakBuilder> {
    private String taste;

    public DoshirakBuilder setExtraField(String taste) {
        this.taste = taste;
        return this;
    }

    @Override
    public Doshirak build() {
        return new Doshirak(id, price, name, taste);
    }
}

