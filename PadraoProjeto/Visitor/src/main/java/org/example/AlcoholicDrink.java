package org.example;

public class AlcoholicDrink extends Product{
    public AlcoholicDrink(float price) {
        setName("AlcoholicDrink");
        setPrice(price);
    }

    @Override
    public double getPriceWithTax(TaxVisitor taxVisitor) {
        return taxVisitor.calculateTaxesForAlcoholicDrink(this);
    }
}
