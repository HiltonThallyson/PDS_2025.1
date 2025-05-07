package org.example;

public class Cigarette extends Product{
    public Cigarette(float price) {
        setName("Cigarette");
        setPrice(price);
    }

    @Override
    public double getPriceWithTax(TaxVisitor taxVisitor) {
        return taxVisitor.calculateTaxesForCigarette(this);
    }
}
