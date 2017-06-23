package com.sfiscusdevelopment.sfiscus.booklit.bookLibrary;

import android.app.Application;

import com.sfiscusdevelopment.sfiscus.booklit.LoginCredentials;
import com.sfiscusdevelopment.sfiscus.booklit.R;
import com.sfiscusdevelopment.sfiscus.booklit.entities.Book;
import com.sfiscusdevelopment.sfiscus.booklit.modelViewPresenter.BaseInterface;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.BookCategories;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.BookCategories.BOOK_LISTS;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.FirebaseActivity;

/**
 * Create a class with an id of BookLibraryInteractor. This class implements
 * BookLibraryInterface.Interactor. The main methods that this class contains is: deleteBook, moveBookTo
 * setmBookLibraryPresenter.
 */
class BookLibraryInteractor implements BookLibraryInterface.Interactor {

    // Create an Application instance variable with an id of mApp
    private Application mApp;

    // Create a BookLibraryPresenter instance variable with an id of mBookLibraryPresenter
    private BookLibraryPresenter mBookLibraryPresenter;

    /**
     * Create a method with an id of BookLibraryInteractor and requires an Application argument.
     * @param mApp the Application itself
     */
    public BookLibraryInteractor(Application mApp) {
    this.mApp = (LoginCredentials) mApp;
  }

    /**
     * Create a method with an id of getBookFromList which requires a String argument. This method
     * finds the book from the database and attachs it to the presenter.
     * @param bookListName the name of the bookList
     */
    @Override
    public void getBookFromList(@BOOK_LISTS String bookListName) {
        mBookLibraryPresenter.SendDbReferenceToView(FirebaseActivity.getmInstance().getBooksFromNodeReference(bookListName));
    }

    /**
     * Create a method with an id of moveBookTo which requires two String arguments and a Book argument.
     * This method is called whenever a book is being moved to another category
     * @param currentList the current category list
     * @param listToMoveTo the category list the book is moved to
     * @param book the book itself
     */
    @Override
    public void moveBookTo(@BOOK_LISTS String currentList, @BOOK_LISTS String listToMoveTo, Book book) {
        FirebaseActivity.getmInstance().moveBook(currentList, listToMoveTo, book);

        String list = (listToMoveTo.equals(BookCategories.READING))
            ? mApp.getResources().getString(R.string.list_reading)
            : mApp.getResources().getString(R.string.list_past_reads);

        mBookLibraryPresenter.displayMessage(mApp.getString(R.string.book_list_activity_msg_book_moved_to,
        list));
    }

    /**
     * Create a method with an id of deleteBook which requires a String and a Book argument.
     * This method is called whenever a book is deleted from the list
     * @param bookListName the name of the bookList
     * @param book the book itself
     */
    @Override
    public void deleteBook(@BOOK_LISTS String bookListName, Book book) {
        FirebaseActivity.getmInstance().deleteBook(bookListName, String.valueOf(book.getId()));
        mBookLibraryPresenter.displayMessage(mApp.getResources()
        .getString(R.string.book_lists_activity_msg_deleted_book));
    }

    /**
     * Create a method with an id of setmBookLibraryPresent which requires a
     * BaseInterface.BasePresenter argument. This is a set method for the presenter.
     * @param mBookLibraryPresenter an argument of BaseInterface.BasePresenter
     */
    public void setmBookLibraryPresenter(BaseInterface.BasePresenter mBookLibraryPresenter) {
        this.mBookLibraryPresenter = (BookLibraryPresenter) mBookLibraryPresenter;
    }
}
