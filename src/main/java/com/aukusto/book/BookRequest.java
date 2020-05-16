package com.aukusto.book;

public class BookRequest {

    private String title;
    private String author;
    private int publicatedOn;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicatedOn() {
        return publicatedOn;
    }

    public void setPublicatedOn(int publicatedOn) {
        this.publicatedOn = publicatedOn;
    }
}

