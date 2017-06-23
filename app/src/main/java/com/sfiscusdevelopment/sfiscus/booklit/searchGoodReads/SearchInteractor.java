package com.sfiscusdevelopment.sfiscus.booklit.searchGoodReads;

import android.app.Application;
import android.content.Context;

import com.sfiscusdevelopment.sfiscus.booklit.BuildConfig;
import com.sfiscusdevelopment.sfiscus.booklit.LoginCredentials;
import com.sfiscusdevelopment.sfiscus.booklit.R;
import com.sfiscusdevelopment.sfiscus.booklit.entities.Book;
import com.sfiscusdevelopment.sfiscus.booklit.entities.BookWrapper;
import com.sfiscusdevelopment.sfiscus.booklit.entities.GoodreadsResponse;
import com.sfiscusdevelopment.sfiscus.booklit.modelViewPresenter.BaseInterface;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.BookCategories;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.FirebaseActivity;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.GoodreadsService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create a class with an id of SearchInteractor that implements SearchBooksContrack.Interactor.
 * This class contains methods that allow for the book to be searched through the GoodReads Service
 * as well as what category the book will be saved to
 */
public class SearchInteractor implements SearchBooksContract.Interactor {

    // Create a SearchPresenter instance variable with an id of mPresenter
    private SearchPresenter mPresenter;

    // Create a LoginCredentials instance variable with an id of mCredentials
    private LoginCredentials mCredentials;

    // Create an ArrayList instance variable with an id of mBooks
    private ArrayList<BookWrapper> mBooks;

    // Create a Context instance variable with an id of mContext
    private Context mContext;

    // Create a constructor with an Application argument
    public SearchInteractor(Application app) {
    this.mCredentials = (LoginCredentials) app;
  }

    /**
     * Create a method with an id of searchBooks that requires a String argument. The purpose of
     * this method is to search for the book requested using the GoodReads Service
     * @param bookTitle the title of the book the user is looking for
     */
    @Override
    public void searchBooks(String bookTitle) {

      GoodreadsService goodreadsService =
              mCredentials.getRetrofitClient(mCredentials.getGoodreadsBaseUrl())
              .create(GoodreadsService.class);

      goodreadsService.searchBooks(bookTitle, 1, BuildConfig.GoodreadsApiKey)
          .enqueue(new Callback<GoodreadsResponse>() {
            @Override
            public void onResponse(Call<GoodreadsResponse> call, Response<GoodreadsResponse> response) {
              if (response.isSuccessful()) {
                  mBooks = response.body()
                    .getSearch()
                    .getResults()
                    .getBookWrappers();
                sendBooksToPresenter(mBooks);

              } else {
                int statusCode = response.code();
                new RuntimeException(String.valueOf(response.code()));
                sendBooksToPresenter(null);
              }
            }

            @Override
            public void onFailure(Call<GoodreadsResponse> call, Throwable t) {
              new RuntimeException(t.getMessage()).printStackTrace();
              sendBooksToPresenter(mBooks);
            }
          });
    }

    /**
     * Create a method with an id of setmBookLibraryPresenter that requires a BaseInterface.BasePresenter
     * argument
     * @param mBookLibraryPresenter an object for the base presenter
     */
    public void setmBookLibraryPresenter(BaseInterface.BasePresenter mBookLibraryPresenter) {
      this.mPresenter = (SearchPresenter) mBookLibraryPresenter;
    }

    /**
     * Create a method with an id of sendBooksToPresenter that requires an ArrayList argument
     * @param books a list of all the books that will display to the user
     */
    @Override
    public void sendBooksToPresenter(ArrayList<BookWrapper> books) {

        mPresenter.loadBooks(books);
  }

    /**
     * Create a method with an id of saveBookTo that requires a Book and a String argument.
     * This method is used to save the book and then display a message to the user
     * @param book the book that is being saved
     * @param nodeName the category the book is being saved to
     */
    @Override
    public void saveBookTo(Book book, String nodeName) {
      FirebaseActivity.getmInstance().saveBook(book, nodeName);

      String list = (nodeName.equals(BookCategories.READING))
          ? mCredentials.getResources().getString(R.string.list_reading)
          : mCredentials.getResources().getString(R.string.list_past_reads);

        mPresenter.displayMessage(mCredentials.getString(R.string.search_activity_book_saved, list));
    }

}
