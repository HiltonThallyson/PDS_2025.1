package org.example;

public class ModerationState implements State {
    @Override
    public void edit(Document doc) {
        System.out.println("Reverting document to Draft state.");
        doc.setState(new DraftState());
    }

    @Override
    public void publish(Document doc) {
        System.out.println("Publishing document.");
        doc.setState(new PublishedState());
    }
}