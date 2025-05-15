package org.example;

public class USTaxVisitor implements TaxVisitor {
    @Override
    public double calculateTaxesForFood(Food food){
        return food.getPrice() * 2;
    }

    @Override
    public double calculateTaxesForCigarette(Cigarette cigarette) {
        return cigarette.getPrice() * 5;
    }

    @Override
    public double calculateTaxesForAlcoholicDrink(AlcoholicDrink alcoholicDrink) {
        return alcoholicDrink.getPrice() * 10;
    }
}
