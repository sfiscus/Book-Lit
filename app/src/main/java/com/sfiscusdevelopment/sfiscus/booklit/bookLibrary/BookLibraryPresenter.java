package com.sfiscusdevelopment.sfiscus.booklit.bookLibrary;

import com.sfiscusdevelopment.sfiscus.booklit.entities.Book;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.BookCategories;
import com.google.firebase.database.DatabaseReference;

/**
 * Create a class with an id of BookLibraryPresenter which implements BookLibraryInterface.Presenter.
 * This class is used to generate the book library view and bind the book objects to them.
 */
class BookLibraryPresenter implements BookLibraryInterface.Presenter {

    // Create a BookLibraryInterface.View instance variable with an id of mView
    private final BookLibraryInterface.View mView;

    // Create a BookLibraryInterface.Interactor instance variable with an id of mInteractor
    private final BookLibraryInterface.Interactor mInteractor;

    /**
     * Create a constructor for the class with a BookLibraryInterface.View and a
     * BookLibraryInterface.Interactor argument
     * @param view a BookLibraryInterface.View argument that represents the view
     * @param interactor a BookLibraryInterface.Interactor argument that fetchs the data
     */
    public BookLibraryPresenter(BookLibraryInterface.View view, BookLibraryInterface.Interactor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
        interactor.setmBookLibraryPresenter(this);
    }

    /**
     * Create a method with an id of displayMessage that displays a message through the snackbar
     * @param message a string argument that represents the message that id displayed
     */
    @Override
    public void displayMessage(String message) {
        mView.displaySnackBar(message);
    }

    /**
     * Create a method with an id of getBookFromList that requires a String argument
     * This method is used to retrieve the book from the list
     * @param bookListName the name of the bookList
     */
    @Override
    public void getBookFromList(@BookCategories.BOOK_LISTS String bookListName) {
        mView.showLoading();
        mInteractor.getBookFromList(bookListName);
    }

    /**
     * Create a method with an id of bookListOperation that requires two String and one Book argument.
     * This method determines if the move or delete option was selected
     * @param option a delete option or the list to move the book to.
     * @param currentList the current list
     * @param selectedBook the selected book
     */
    @Override
    public void bookListOperation(String option, String currentList, Book selectedBook) {
        if(option.equals(BookLibraryModel.OPTION_DELETE)){
            mInteractor.deleteBook(currentList, selectedBook);
        }else{
            mInteractor.moveBookTo(currentList, option, selectedBook);
        }
    }

    /**
     * Create a method with an id of SendDbReferenceToView requiring a DatabaseReference argument.
     * This method sends the query to the view
     * @param bookListRef an argument of DatabaseReference used to reference the bookList
     */
    @Override
    public void SendDbReferenceToView(DatabaseReference bookListRef) {

        // The hide loading will be trigger within a callback added to the adapter
        mView.setupRecyclerViewWithReference(bookListRef);
    }

    // Create a method with an id of showNoBooksFound which is called when view is empty
    @Override
    public void showNoBooksFound() {
        mView.showNoBooksFound();
  }

    // Create a method with an id of showBooks which is called when the view is not empty
    @Override
    public void showBooks() {
        mView.hideLoading();
  }

}
