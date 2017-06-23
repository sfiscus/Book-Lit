package com.sfiscusdevelopment.sfiscus.booklit.searchGoodReads;

import com.sfiscusdevelopment.sfiscus.booklit.entities.Book;
import com.sfiscusdevelopment.sfiscus.booklit.entities.BookWrapper;
import com.sfiscusdevelopment.sfiscus.booklit.modelViewPresenter.BaseInterface;

import java.util.ArrayList;

/**
 * Create an interface with an id of SearchBooksContract that implements a Presenter, a View, and a
 * Interactor interface
 */
public interface SearchBooksContract {

  interface Presenter extends BaseInterface.BasePresenter {
    void loadBooks(ArrayList<BookWrapper> books);
    void searchBooks(String bookTitle);
    void saveBookTo(Book book, String list);
  }

  interface View extends BaseInterface.BaseView {
    void onSearchTextChange(String text);
  }

  interface Interactor extends BaseInterface.BaseInteractor {
    void sendBooksToPresenter(ArrayList<BookWrapper> books);
    void searchBooks(String bookTitle) throws Exception;
    void saveBookTo(Book book, String nodeName);
  }
}
