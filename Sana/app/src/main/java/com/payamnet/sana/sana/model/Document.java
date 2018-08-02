package com.payamnet.sana.sana.model;

/**
 * Created by kosar on 8/2/18.
 */

public class Document {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private String subject;


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
}
