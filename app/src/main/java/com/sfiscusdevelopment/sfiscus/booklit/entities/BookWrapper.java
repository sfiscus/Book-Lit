package com.sfiscusdevelopment.sfiscus.booklit.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Create a class with an id of BookWrapper that implements Serializable. This class contains get
 * and set methods pertaining to publication year, average rating, as well as an id
 */
@Root(name = "work", strict = false)
public class BookWrapper implements Serializable {

    // Create an int instance variable with an id of originalPublicationYear
    @Element(name = "original_publication_year", required = false)
    public int origialPublicationYear;

    // Create a String instance variable with an id of id
    @Element(name = "average_rating", required = false)
    public String averageRating;

    // Create a public Book instance variable with an id of best_book
    @Element(name = "best_book")
    public Book book;

    // Create a String instance variable with an id of id
    public String id;

    // Create a no argument constructor
    public BookWrapper() {
    }

    // Create getters and setters for each instance variable
    public int getOrigialPublicationYear() {
    return origialPublicationYear;
  }

    public void setOrigialPublicationYear(int origialPublicationYear) {
        this.origialPublicationYear = origialPublicationYear;
    }

    public String getAverageRating() {
    return averageRating;
  }

    public void setAverageRating(String averageRating) {
    this.averageRating = averageRating;
  }

    public Book getBook() {
    return book;
  }

    public void setBook(Book book) {
    this.book = book;
  }
}
