package org.example;

public class Cigarette implements VisitableProduct{
    private String name;
    private float price;
    
    public Cigarette(String name , float price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPriceWithTax(TaxVisitor taxVisitor) {
        return taxVisitor.calculateTaxesForCigarette(this);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
}
