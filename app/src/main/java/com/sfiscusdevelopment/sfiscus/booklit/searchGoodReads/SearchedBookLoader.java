package com.sfiscusdevelopment.sfiscus.booklit.searchGoodReads;

import android.content.Context;

import com.sfiscusdevelopment.sfiscus.booklit.BuildConfig;
import com.sfiscusdevelopment.sfiscus.booklit.entities.BookWrapper;
import com.sfiscusdevelopment.sfiscus.booklit.entities.GoodreadsResponse;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.GoodreadsService;

import org.simpleframework.xml.core.ValueRequiredException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Create a class with an id of SearchedBookLoader which extends AsyncTaskLoader<List<BookWrapper>>.
 * This class handles the book loading that is done in the background.
 */
public class SearchedBookLoader extends android.support.v4.content.AsyncTaskLoader<List<BookWrapper>> {

    // Create a static final String instance variable with an id of TAG and a value of SearchedBookLoader
    private static final String TAG = "SearchedBookLoader";

    // Create a List instance variable with an id of mBooks
    private List<BookWrapper> mBooks;

    // Create a Retrofite with an id of mRetrofit
    private Retrofit mRetrofit;

    // Create a String instance variable with an id of mSearchQuery
    private String mSearchQuery;

    // Create an int instance variable with an id of mRequestPage
    private int mRequestPage;

    // Create a constructor with a Context argument
    SearchedBookLoader(Context context) {
        super(context);

    }

    // Create a constructor with a Context and a String argument
    SearchedBookLoader(Context context, String searchQuery) {
        super(context);
        this.mSearchQuery = searchQuery;
        mRequestPage = 1;
        mRetrofit = new Retrofit.Builder()
            .baseUrl(GoodreadsService.BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();
    }

    /**
     * Create a List method with an id of loadInBackground. This method is called in a background
     * thread and should generate a new set of data to be published by the loader
     * @return a statment that returns the ArrayList
     */
    @Override
    public List<BookWrapper> loadInBackground() {

        // Retrieve the books.
        mBooks = new ArrayList<>();

        if(mSearchQuery != null && !mSearchQuery.isEmpty()){
        GoodreadsService goodreadsService = mRetrofit.create(GoodreadsService.class);
        Call<GoodreadsResponse> call = goodreadsService.searchBooks(mSearchQuery, 1,
            BuildConfig.GoodreadsApiKey);

        try {
            GoodreadsResponse response = call.execute().body();
            mBooks = response.getSearch().getResults().getBookWrappers();

        } catch (Exception e) {
            if (e instanceof IOException || e instanceof ValueRequiredException) {
                mBooks = Collections.emptyList();
            return mBooks;

            } else{
            e.printStackTrace();
                mBooks = Collections.emptyList();
            return mBooks;
            }
        }
        return mBooks;

        } else{

        return mBooks;

        }
    }

    // Create a method with an id of onStartLoading that calls two methods when loading begins
    @Override
    protected void onStartLoading() {
        cancelLoad();
        forceLoad();
    }

    // Create a method with an id of onReset that is called when loading restarts
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();
    }

    /**
     * Create a method with an id of deliverResult which requires a List argument. This method
     * delivers the result
     * @param data the list argument that represents the data
     */
    @Override
    public void deliverResult(List<BookWrapper> data) {
        super.deliverResult(data);
    }
}
