package com.sfiscusdevelopment.sfiscus.booklit.repositories;


import com.sfiscusdevelopment.sfiscus.booklit.entities.GoodreadsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Create a public interface with an id of GoodreadsService. This interfaces contains data for the
 * search and the url that is being used.
 */
public interface GoodreadsService {

  String BASE_URL = "https://www.goodreads.com";

  @GET("/search")
  Call<GoodreadsResponse> searchBooks(
          @Query("q") String searchString, // supports book title, author, ISBN & fields phrase searching
          @Query("page") int page,
          @Query("key") String goodreadsApiKey
  );
}
