package org.example;

public class Product implements VisitableProduct {
    private String name;
    private float price;

    public Product() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public double getPriceWithTax(TaxVisitor taxVisitor) {
        return taxVisitor.calculateTaxesForProduct(this);
    }
}
