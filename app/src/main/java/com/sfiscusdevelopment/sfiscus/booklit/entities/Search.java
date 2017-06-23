package com.sfiscusdevelopment.sfiscus.booklit.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Create a class with an id of Search that implements Serializable. This class contains getters
 * and setters that pertain to the results that the user receives
 */
@Root(name = "search", strict = false)
public class Search implements Serializable {

    // Create an int instance variable with an id of resultStart
    @Element(name = "results-start")
    private int resultsStart;

    // Create an int instance variable with an id of resultEnd
    @Element(name = "results-end")
    private int resultsEnd;

    // Create an int instance variable with an id of id
    @Element(name = "total-results")
    private int resultsTotal;

    // Create a Results instance variable with an id of results
    @Element(name = "results")
    private Results results;

    // Create a no argument constructor
    public Search() {
    }

    // Create a constructor with three int and one Results argument
    public Search(int resultsStart, int resultsEnd, int resultsTotal, Results results) {
        this.resultsStart = resultsStart;
        this.resultsEnd = resultsEnd;
        this.resultsTotal = resultsTotal;
        this.results = results;
    }

    // Create getters and setters for each instance variable
    public int getResultsStart() {
    return resultsStart;
  }

    public void setResultsStart(int resultsStart) {
    this.resultsStart = resultsStart;
  }

    public int getResultsEnd() {
    return resultsEnd;
  }

    public void setResultsEnd(int resultsEnd) {
    this.resultsEnd = resultsEnd;
  }

    public int getResultsTotal() {
    return resultsTotal;
  }

    public void setResultsTotal(int resultsTotal) {
    this.resultsTotal = resultsTotal;
  }

    public Results getResults() {
    return results;
  }

    public void setResults(Results results) {
    this.results = results;
  }
}