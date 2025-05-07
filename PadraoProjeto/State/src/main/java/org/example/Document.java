package org.example;

public class Document {
    private State state;

    public Document() {
        this.state = new DraftState(); // Initial state
    }

    public void setState(State state) {
        this.state = state;
    }

    public void edit() {
        state.edit(this);
    }

    public void publish() {
        state.publish(this);
    }
}