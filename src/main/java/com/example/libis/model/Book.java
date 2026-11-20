package com.example.libis.model;

public class Book {
    private int bookId;
    private String isbn;
    private String title;
    private String author;
    private String publishedYear;
    private String genre;
    private String synopsis;
    private boolean available;

    public Book() {
    }

    public Book(int bookId, String isbn, String title, String author, String publishedYear, String genre,
                String synopsis, boolean available) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.genre = genre;
        this.synopsis = synopsis;
        this.available = available;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() { return isbn; }

    public void setIsbn(String isbn) {}

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

    public String getPublishedYear() { return publishedYear; }

    public void setPublishedYear(int publishedYear) {}

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}