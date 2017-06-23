package com.sfiscusdevelopment.sfiscus.booklit.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Create a class with an id of GoodreadsResponse that implements Serializable. This class contains
 * getters and setter that pertain to the user query
 */
@Root(strict = false)
public class GoodreadsResponse implements Serializable {

    // Create a Search instance variable with an id of search
    @Element(name = "search")
    private Search search;

    // Create getters and setters for the instance variable
    public Search getSearch() {
    return search;
  }

    public void setSearch(Search search) {
    this.search = search;
  }
}

