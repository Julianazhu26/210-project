package model;

import org.json.JSONObject;

import persistence.Writable;

// A class representing a book with title, author, quick summary, rating and review status.
public class Book implements Writable {
    private String title;
    private String author;
    private String genre;
    private int rating;
    private Boolean status;

    // EFFECTS: initiate an unreviewed, empty Book object
    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rating = 0;
        this.status = false;
    }

    // MODIFIES: this
    // EFFECTS: set new book title
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    // MODIFIES: this
    // EFFECTS: set new book author
    public void setAuthor(String newAuthor) {
        this.author = newAuthor;
    }

    // MODIFIES: this
    // EFFECTS: set new book genre
    public void setGenre(String newGenre) {
        this.genre = newGenre;
    }

    // MODIFIES: this
    // EFFECTS: set new book rating
    public void setRating(int newRating) {
        this.rating = newRating;
    }

    // MODIFIES: this
    // EFFECTS: list this book read(change status)
    public void listRead() {
        this.status = true;
    }

    // MODIFIES: this
    // EFFECTS: list this book not read(change status)
    public void listToBeRead() {
        this.status = false;
    }

    // EFFECTS: return book title
    public String getTitle() {
        return title;
    }

    // EFFECTS: return book author
    public String getAuthor() {
        return author;
    }

    // EFFECTS: return book genre
    public String getGenre() {
        return genre;
    }

    // EFFECTS: return book rating
    public int getRating() {
        return rating;
    }

    // EFFECTS: return whether the book was read
    public Boolean getStatus() {
        return status; // true = read, vice versa
    }

    // EFEFECTS: return just the book title for GUI list
    @Override
    public String toString() {
        return title;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("genre", genre);
        json.put("rating", rating);
        json.put("status", status);
        return json;
    }

}