package org.example;

public class Food extends VisitableProduct{
    public Food(String name,float price) {
        super(name, price);
    }

    @Override
    public double getPriceWithTax(TaxVisitor taxVisitor) {
        return taxVisitor.calculateTaxesForFood(this);
    }
}
