package org.example;

public class PublishedState implements State {
    @Override
    public void edit(Document doc) {
        System.out.println("Cannot edit a Published document.");
    }

    @Override
    public void publish(Document doc) {
        System.out.println("Document is already Published.");
    }
}