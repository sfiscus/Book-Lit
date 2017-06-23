package com.sfiscusdevelopment.sfiscus.booklit.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Create a class with an id of Book which implements Serializable. This class contains all the vital
 * data for the book itself. It contains get and set methods for each of the instance variables
 */
@Root(name = "best_book", strict =  false)
public class Book implements Serializable {

    // Create an int instance variable with an id of id
    @Element(name = "id")
    private int id;

    // Create a String instance variable with an id of title
    @Element(name = "title")
    private String title;

    // Create a Author instance variable with an id of author
    @Element(name = "author", required = false)
    private Author author;

    // Create a String instance variable with an id of averageRating
    @Element(required = false)
    private String averageRating;

    // Create a String instance variable with an id of imageUrl
    @Element(name = "image_url")
    private String imageUrl;

    // Create a String instance variable with an id of smallImageUrl
    @Element(name = "small_image_url")
    private String smallImageUrl;

    // Create a no argument constructor
    public Book() {
    }

    // Create a constructor with one int, four String, and one Author argument
    public Book(int id, String title, Author author, String averageRating, String imageUrl, String smallImageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.averageRating = averageRating;
        this.imageUrl = imageUrl;
        this.smallImageUrl = smallImageUrl;
    }

    // Create get and set methods for variables
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }
}
