package org.example;

interface VisitableProduct {
    abstract double getPriceWithTax(TaxVisitor taxVisitor);
    abstract double getPrice();
}
