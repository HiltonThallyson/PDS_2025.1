package org.example;

abstract class VisitableProduct {
    private String name;
    private double price;

    VisitableProduct(String name, double price) {
        this.name = name;
        this.price = price;
    }
    abstract double getPriceWithTax(TaxVisitor taxVisitor);
    
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setName(String name) {
        this.name = name;
    }
}
