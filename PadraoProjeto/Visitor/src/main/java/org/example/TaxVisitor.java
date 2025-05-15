package org.example;

public interface TaxVisitor {
    double calculateTaxesForFood(Food food);
    double calculateTaxesForCigarette(Cigarette cigarette);
    double calculateTaxesForAlcoholicDrink(AlcoholicDrink alcoholicDrink);
}
