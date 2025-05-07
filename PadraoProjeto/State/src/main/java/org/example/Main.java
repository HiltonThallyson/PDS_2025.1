package org.example;

public class Main {
    public static void main(String[] args) {
        Document doc = new Document();

        doc.edit();     // Draft
        doc.publish();  // Move to Moderation
        doc.edit();     // Move to Draft
        doc.publish();  // Move to Moderation
        doc.publish();  // Move to Published
        doc.edit();     // Cannot edit
        doc.publish();  // Already Published
    }
}
