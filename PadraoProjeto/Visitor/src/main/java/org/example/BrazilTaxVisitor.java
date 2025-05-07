package org.example;

public class BrazilTaxVisitor implements TaxVisitor {
    @Override
    public double calculateTaxesForFood(Food food){
        return food.getPrice() * (1.05);
    }

    @Override
    public double calculateTaxesForCigarette(Cigarette cigarette) {
        return cigarette.getPrice() * 2.5;
    }

    @Override
    public double calculateTaxesForAlcoholicDrink(AlcoholicDrink alcoholicDrink) {
        return alcoholicDrink.getPrice() * 1.5;
    }

    @Override
    public double calculateTaxesForProduct(Product product) {
        return product.getPrice();
    }
}
