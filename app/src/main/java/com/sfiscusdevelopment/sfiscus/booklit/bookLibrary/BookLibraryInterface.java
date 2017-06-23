package com.sfiscusdevelopment.sfiscus.booklit.bookLibrary;

import com.sfiscusdevelopment.sfiscus.booklit.entities.Book;
import com.sfiscusdevelopment.sfiscus.booklit.modelViewPresenter.BaseInterface;
import com.google.firebase.database.DatabaseReference;

import static com.sfiscusdevelopment.sfiscus.booklit.repositories.BookCategories.BOOK_LISTS;

/**
 * Create an interface with an id of BookLIbraryInterface. Inside are three seperate interfaces.
 * One interfaces has an id of Presenter which extends BaseInterface.BasePresenter. The second
 * interface has an id of View and extends BaseInterface.BaseView. The final interface has an id of
 * Interactor and extends BaseInterface.BaseInteractor.
 */
interface BookLibraryInterface {

    interface Presenter extends BaseInterface.BasePresenter {

        /**
         * Create a method with an id of getBookFromList. This method requires a String argument.
         * @param bookListName the name of the bookList
         */
        void getBookFromList(@BOOK_LISTS String bookListName);

        /**
        * Create a method with an id of bookListOperation and requires two String and one Book argument.
        * @param option a delete option or the list to move the book to.
        * @param currentList the current list
        * @param selectedBook the selected book
        */
        void bookListOperation(String option, String currentList, Book selectedBook);

        void SendDbReferenceToView(DatabaseReference bookListRef);

        void showNoBooksFound();

        void showBooks();
    }


    interface View extends BaseInterface.BaseView {
        void setupRecyclerViewWithReference(DatabaseReference bookListRef);
    }

    interface Interactor extends BaseInterface.BaseInteractor {

        /**
         * Create a method with an id of getBookFromList and requires one String argument. This
         * method is used to retrieve the book from the list
         * @param bookListName the name of the bookList
         */
        void getBookFromList(@BOOK_LISTS String bookListName);

        /**
         * Create a method with an id of moveBookTo and requires two String and one Book argument.
         * This method is used to move the book to a different category
         * @param currentList the current list
         * @param listToMoveTo the category to move the book to
         * @param book the book itself
         */
        void moveBookTo(@BOOK_LISTS String currentList, @BOOK_LISTS String listToMoveTo, Book book);

        /**
         * Create a method with an id of deleteBook and requires one String and one Book argument.
         * This method is called to delete the book from the list
         * @param bookListName the name of the bookList
         * @param book the book itself
         */
        void deleteBook(@BOOK_LISTS String bookListName, Book book);
    }

}
