package org.example;

public class DraftState implements State {
    @Override
    public void edit(Document doc) {
        System.out.println("Editing document in Draft state.");
    }

    @Override
    public void publish(Document doc) {
        System.out.println("Moving document to Moderation.");
        doc.setState(new ModerationState());
    }
}
