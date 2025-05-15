package org.example;

import java.util.ArrayList;

public class Cart {
    private ArrayList<VisitableProduct> products;

    public Cart() {
        this.products = new ArrayList<>();
    }
    public void addProduct(VisitableProduct product) {
        products.add(product);
    }
    public void removeProduct(VisitableProduct product) {
        products.remove(product);
    }
    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (VisitableProduct product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public double calculateTotalPriceWithTax(TaxVisitor taxVisitor) {
        double totalPriceWithTax = 0;
        for (VisitableProduct product : products) {
            totalPriceWithTax += product.getPriceWithTax(taxVisitor);
        }
        return totalPriceWithTax;
    }
}
