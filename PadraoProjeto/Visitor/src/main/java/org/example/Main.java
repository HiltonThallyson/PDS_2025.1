package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Food food = new Food(10);
        Cigarette cigarette = new Cigarette(5);
        AlcoholicDrink alcoholicDrink = new AlcoholicDrink(5);

        ArrayList<Product> cart = new ArrayList<Product>();
        cart.add(food);
        cart.add(cigarette);
        cart.add(alcoholicDrink);

        double totalPrice = calculateTotalPrice(cart);
        System.out.println("Total price: " + totalPrice);

        TaxVisitor taxVisitor = new BrazilTaxVisitor();
        double totalPriceWithTax = 0;
        for (Product product : cart) {
            totalPriceWithTax += product.getPriceWithTax(taxVisitor);
        }
        System.out.println("Total price with tax: " + totalPriceWithTax);
    }

    static double calculateTotalPrice(ArrayList<Product> cart) {
        double totalPrice = 0;
        for (Product product : cart) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    static double calculatePriceWithTax(ArrayList<Product> cart, TaxVisitor taxVisitor) {
        double totalPriceWithTax = 0;
        for (Product product : cart) {
            totalPriceWithTax += product.getPriceWithTax(taxVisitor);
        }
        return totalPriceWithTax;
    }
}