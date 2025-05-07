package org.example;

public interface State {
    void edit(Document doc);
    void publish(Document doc);
}