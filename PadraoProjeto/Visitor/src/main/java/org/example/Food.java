package org.example;

public class Food extends Product{
    public Food(float price) {
        setName("Food");
        setPrice(price);
    }

    @Override
    public double getPriceWithTax(TaxVisitor taxVisitor) {
        return taxVisitor.calculateTaxesForFood(this);
    }
}
