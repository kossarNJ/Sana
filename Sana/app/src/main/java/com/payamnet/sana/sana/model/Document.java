package com.payamnet.sana.sana.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosar on 8/2/18.
 */




public class Document {
    public static List<Document> documentList = new ArrayList<>();
    public static String ID_TAG = "DC";
    public static String TITLE_TAG = "Title";
    public static String AUTHOR_TAG = "Author";
    public static String PUBLISHER_TAG = "Publisher";
    public static String SUBJECT_TAG = "Subject";
    private String id;
    private String title;
    private String author;
    private String publisher;
    private String subject;

    public Document(String id, String title, String author, String publisher, String subject) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.subject = subject;
    }

    public Document() {
    }

    // Getter Methods

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSubject() {
        return subject;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    public void setAuthor(String Author) {
        this.author = Author;
    }

    public void setPublisher(String Publisher) {
        this.publisher = Publisher;
    }

    public void setSubject(String Subject) {
        this.subject = Subject;
    }

    @Override
    public String toString() {
        return "ID is: " + id + "\n" +
                "Title is: " + title + "\n" +
                "Author is: " + author + "\n" +
                "Publisher is: " + publisher + "\n" +
                "Subject is: " + subject + "\n";
    }
}