package org.example;

public class Cigarette extends VisitableProduct{
    public Cigarette(String name , float price) {
        super(name, price);
    }

    @Override
    public double getPriceWithTax(TaxVisitor taxVisitor) {
        return taxVisitor.calculateTaxesForCigarette(this);
    }
}
