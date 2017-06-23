package com.sfiscusdevelopment.sfiscus.booklit.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Create a class with an id of Author which implements Serializable. This class contains get and
 * set methods that pertain to the authors information
 */
@Root(name = "author", strict = false)
public class Author implements Serializable {

    // Create an int instance variable with an id of id
    @Element(name = "id")
    public int id;

    // Create a String instance variable with an id of name
    @Element(name = "name")
    public String name;

    // Create a no argument constructor
    public Author() {
    }

    // Create a constructor with two arguments
    public Author(@Element(name = "id") int id, @Element(name = "name") String name) {
        this.id = id;
        this.name = name;
    }

    // Create get and set methods for variables
    public int getId() {
    return id;
  }

    public void setId(int id) {
    this.id = id;
  }

    public String getName() {
    return name;
  }

    public void setName(String name) {
    this.name = name;
  }

}