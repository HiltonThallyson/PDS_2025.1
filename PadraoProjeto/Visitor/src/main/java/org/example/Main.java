package org.example;


public class Main {
    public static void main(String[] args) {
        Food food = new Food("Torta", 10);
        Cigarette cigarette = new Cigarette("Malboro", 5);
        AlcoholicDrink alcoholicDrink = new AlcoholicDrink("RedLabel", 5);

        Cart cart = new Cart();
        cart.addProduct(food);
        cart.addProduct(cigarette);
        cart.addProduct(alcoholicDrink);

        double totalPrice = cart.calculateTotalPrice();
        System.out.println("Total price: " + totalPrice);

        TaxVisitor brTaxVisitor = new BrazilTaxVisitor();
        TaxVisitor usTaxVisitor = new USTaxVisitor();

       
        double totalPriceWithTaxBrazil = 0;
        double totalPriceWithTaxUS = 0;
        
        totalPriceWithTaxBrazil = cart.calculateTotalPriceWithTax(brTaxVisitor);
        totalPriceWithTaxUS = cart.calculateTotalPriceWithTax(usTaxVisitor);

        System.out.println("Total price with tax in Brazil: " + totalPriceWithTaxBrazil);
        System.out.println("Total price with tax in USA: " + totalPriceWithTaxUS);
    }
    
}