package org.example.builders;

import org.example.objects.Product;

public class ProductBuilder<T extends ProductBuilder<T>> {
    protected int id;
    protected String name;
    protected double price;

    @SuppressWarnings("unchecked")
    public T setId(int id) {
        this.id = id;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setName(String name) {
        this.name = name;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setPrice(double price) {
        this.price = price;
        return (T) this;
    }

    public Product build() {
        return null;  // Переопределяется в дочерних классах
    }

    public ProductBuilder<T> setExtraField(String s) {
        return null; // Переопределяется в дочерних классах
    }
}
