package com.sfiscusdevelopment.sfiscus.booklit.repositories;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Create a class with an id of BookCategories. This class contains the category names
 */
public class BookCategories {

  @Retention(RetentionPolicy.CLASS)
  @StringDef({TOP_READS, READING, PAST_READS, FUTURE_READS})
  public@interface BOOK_LISTS{}
  public static final String TOP_READS = "topReads";
  public static final String READING = "reading";
  public static final String PAST_READS = "pastReads";
  public static final String FUTURE_READS = "futureReads";
}
