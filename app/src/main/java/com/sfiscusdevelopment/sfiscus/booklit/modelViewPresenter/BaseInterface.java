package com.sfiscusdevelopment.sfiscus.booklit.modelViewPresenter;

import com.sfiscusdevelopment.sfiscus.booklit.entities.BookWrapper;

import java.util.ArrayList;

/**
 * Create a public interface with an id of BaseInterface which contains multiple calls from other
 * interfaces that in turn call multiple methods that display in the View.
 */
public interface BaseInterface {

  interface BasePresenter {
    void displayMessage(String message);
  }

  interface BaseView {
    void showBooks(ArrayList<BookWrapper> booksArray);
    void showNoBooksFound();
    void displayError();
    void showLoading();
    void hideLoading();
    void displaySnackBar(String message);
  }

  interface BaseInteractor {
    void setmBookLibraryPresenter(BasePresenter mBookLibraryPresenter);
  }

}
