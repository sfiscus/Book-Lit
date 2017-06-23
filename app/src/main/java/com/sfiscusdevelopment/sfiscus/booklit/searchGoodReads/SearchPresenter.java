package com.sfiscusdevelopment.sfiscus.booklit.searchGoodReads;

import com.sfiscusdevelopment.sfiscus.booklit.entities.Book;
import com.sfiscusdevelopment.sfiscus.booklit.entities.BookWrapper;

import java.util.ArrayList;

/**
 * Create a class with an id of SearchPresenter that implements SearchBooksContract.Presenter.
 * This class takes care of the search and what is displayed to the Presenter
 */
public class SearchPresenter implements SearchBooksContract.Presenter {

    // Create SearchBooksContract.View instance variable with an id of mView
    private SearchBooksContract.View mView;

    // Create a SearchBooksContract.Interactor instance variable with an id of mInteractor
    private SearchBooksContract.Interactor mInteractor;

    // Create a constructor with a SearchBooksContract.View and a SearchBooksContract.Interactor argument
    public SearchPresenter(SearchBooksContract.View view, SearchBooksContract.Interactor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
        this.mInteractor.setmBookLibraryPresenter(this);
    }

    /**
     * Create a method with an id of searchBooks that requires a String argument. This method
     * displays an error if there is anything that goes wrong with the book search
     * @param bookTitle the title of the book the user is searching for
     */
    @Override
    public void searchBooks(String bookTitle) {
        if (!bookTitle.isEmpty()) {
            mView.showLoading();
        try {
            mInteractor.searchBooks(bookTitle);
        } catch (RuntimeException e) {
            mView.hideLoading();
            mView.displayError();
        } catch (Exception e) {
            e.printStackTrace();
            mView.hideLoading();
            mView.displayError();
        }
        }
    }

    /**
     * Create a method with an id of loadBooks that requires an ArrayList argument. This method
     * calls multiple methods that are associated to the View
     * @param books the book the user is searching for
     */
    @Override
    public void loadBooks(ArrayList<BookWrapper> books) {
        mView.hideLoading();

        if(books == null){
            mView.displayError();
        }else if (!books.isEmpty()) {
            mView.showBooks(books);
        }else{
            mView.showNoBooksFound();
        }

    }

    /**
     * Create a method with an id of saveBookTo that requires a Book and a String argument
     * @param book the book itself
     * @param list the list the book is being saved to
     */
    @Override
    public void saveBookTo(Book book, String list) {
        mInteractor.saveBookTo(book, list);
  }

    /**
     * Create a method with an id of displayMessage that requires a String argument
     * @param message a String that gives information to the user such as errors or updates
     */
    @Override
    public void displayMessage(String message) {
        mView.displaySnackBar(message);
  }
}
