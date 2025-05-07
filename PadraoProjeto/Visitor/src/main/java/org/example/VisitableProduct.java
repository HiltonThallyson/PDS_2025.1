package org.example;

public interface VisitableProduct {
    double getPriceWithTax(TaxVisitor taxVisitor);
}
