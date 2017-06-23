package com.sfiscusdevelopment.sfiscus.booklit.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create a class with an id of Results that implements Serializable. This class contains getters
 * and setters for an Array of the results
 */
@Root(name = "results", strict = false)
public class Results implements Serializable {

    // Create an ArraryList instance variable with an id of bookWrappers
    @ElementList(inline = true)
    private ArrayList<BookWrapper> bookWrappers;

    // Create a no argument constructor
    public Results() {
    }

    // Create a constructor with an ArrayList argument
    public Results(ArrayList<BookWrapper> bookWrappers) {
    this.bookWrappers = bookWrappers;
  }

    // Create getters and setters for the instance variable
    public ArrayList<BookWrapper> getBookWrappers() {
    return bookWrappers;
  }

    public void setBookWrappers(ArrayList<BookWrapper> bookWrappers) {
        this.bookWrappers = bookWrappers;
    }
}