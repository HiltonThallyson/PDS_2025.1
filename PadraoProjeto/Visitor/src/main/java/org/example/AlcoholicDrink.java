package org.example;

public class AlcoholicDrink extends VisitableProduct{
    public AlcoholicDrink(String name , float price) {
        super(name, price);
    }

    @Override
    public double getPriceWithTax(TaxVisitor taxVisitor) {
        return taxVisitor.calculateTaxesForAlcoholicDrink(this);
    }
}
