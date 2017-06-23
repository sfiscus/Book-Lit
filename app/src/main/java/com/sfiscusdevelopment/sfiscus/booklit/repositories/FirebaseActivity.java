package com.sfiscusdevelopment.sfiscus.booklit.repositories;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sfiscusdevelopment.sfiscus.booklit.entities.Book;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.BookCategories.BOOK_LISTS;
import com.sfiscusdevelopment.sfiscus.booklit.widgets.BooksWidgetProvider;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Create a class with an id of FirebaseActivity. This class implements ValueEventListener.
 * This class contains code that is responsible for moving the book to a specific category
 */
public class FirebaseActivity implements ValueEventListener {

    // A String with an id of TAG and given the value of FirebaseActivity
    private static final String TAG = "FirebaseActivity";

    // A static FirebaseActivity variable with an id of mInstance
    private static FirebaseActivity mInstance = null;

    // A static FirebaseDatabase variable with an id of mDatabase
    private static FirebaseDatabase mDatabase;

    // A static FirebaseAnalytics variable with an id of mFirebaseAnalytics
    private static FirebaseAnalytics mFirebaseAnalytics;

    // A static FirebaseUser variable with an id of mCurrentUser
    private static FirebaseUser mCurrentUser;

    // A Context variable with an id of mContext
    private Context mContext;

    // A DatabaseReference variable with an id of mSavedBookReference
    private DatabaseReference mSavedBookReference;

    // A Database Reference variable with an id of mGetBookReference
    private DatabaseReference mGetBookReference;

    // A DatabaseReference variable with an id of mDeletedBookReference
    private DatabaseReference mDeletedBookReference;

    // Empty constructor for the class
    public FirebaseActivity() {
    }

    /**
     * Create a method to determine of an instance of the FirebaseActivity already exists
     * @return a statement that returns the value of the mInstance variable
     */
    public static FirebaseActivity getmInstance() {
        if (mInstance == null) {
        mInstance = new FirebaseActivity();
        mInstance.initialize();
        }
        return mInstance;
    }

    /**
     * Set method for FirebaseAnalytics
     * @param mFirebaseAnalytics an instance variable of FirebaseAnalytics
     */
    public void setFirebaseAnalytics(FirebaseAnalytics mFirebaseAnalytics) {
        FirebaseActivity.mFirebaseAnalytics = mFirebaseAnalytics;
    }

    /**
     * Set method for mContext
     * @param mContext instance variable of Context
     */
    public void setmContext(Context mContext) {
    this.mContext = mContext;
  }


    // Create a method with an id of initialize which will be called in the getmInstance method
    private void initialize() {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.setPersistenceEnabled(true);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     * Create a method witn an id of saveBook which creates a logEvent and calls the saveBookToList
     * method. The saveBookToList method requires a Book and String variable
     * @param book the book itself
     * @param nodeName the name of the category
     */
    public void saveBook(Book book, String nodeName) {
        logEvent("saved_book_to_" + nodeName, nodeName);
        saveBookToList(book, nodeName);
    }

    /**
     * Create a method with an id getBooksFromNodeReference with a String argument
     * @param nodeName the name of the category
     * @return a statement that returns the value of mGetBookReference
     */
    public DatabaseReference getBooksFromNodeReference(@BOOK_LISTS String nodeName) {

        if (mCurrentUser == null) {
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        }

        mGetBookReference = mDatabase.getReference()
            .child(mCurrentUser.getUid())
            .child(nodeName.toLowerCase());

        mGetBookReference.addValueEventListener(this);

        return mGetBookReference;
    }

    /**
     * Create a method with an id of deleteBook which is called if the user chooses to delete a book
     * from one of the categories. The method requires two string arguments
     * @param nodeName the name of the category
     * @param bookId the book id
     */
    public void deleteBook(@BOOK_LISTS String nodeName, String bookId) {
        mDeletedBookReference = mDatabase.getReference()
            .child(mCurrentUser.getUid())
            .child(nodeName.toLowerCase())
            .child(bookId);

        mDeletedBookReference.addValueEventListener(this);
        mDeletedBookReference.removeValue();
    }

    /**
     * Create a method with an id of moveBook which is called if the user chooses to move a
     * book to one of the categories. This method requires a string and a book argument
     * @param currentList the current list
     * @param listToMoveTo the category to move the book to
     * @param book the book itself
     */
    public void moveBook(@BOOK_LISTS String currentList, @BOOK_LISTS String listToMoveTo, Book book) {

        // the same function as the delete book is here
        // to prevent the addValueListener to code the updateWidget twice for an operation.
        mDatabase.getReference()
            .child(mCurrentUser.getUid())
            .child(currentList.toLowerCase())
            .child(String.valueOf(book.getId()))
            .removeValue();

        saveBook(book, listToMoveTo, true);
    }

    /**
     * Create a method with an id of geBookFromIdInList with two String and a GetBookFromListener
     * arguments.
     * @param currentList the current category list
     * @param bookId the book id
     * @param bookListener a listener from GetBookFromListListener
     */
    public void getBookFromIdInList(@BOOK_LISTS String currentList, String bookId, final GetBookFromListListener bookListener) {

        final DatabaseReference reference = mDatabase.getReference()
            .child(mCurrentUser.getUid())
            .child(currentList)
            .child(bookId);

        final ValueEventListener valueEventListener = new ValueEventListener() {

        /**
         *  Create a method with an id of onDataChange with a DataSnapshot argument
         * @param dataSnapshot an instance of DataSnapshot
         */
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            bookListener.onReceivedBook(dataSnapshot.getValue(Book.class));
            reference.removeEventListener(this);
        }

        /**
         *  Create a method with an id of onCancelled with a DatabaseError argument
         * @param databaseError an instance of DatabaseError
         */
        @Override
        public void onCancelled(DatabaseError databaseError) {
            bookListener.onError();
            reference.removeEventListener(this);
        }
        };

        reference.addValueEventListener(valueEventListener);

    }

    /**
     * Create a method with an id of logEvent that requires two String arguments.
     * @param eventType an argument of a String that represents the type of event
     * @param event an argument of a String that represents the event itself
     */
    public void logEvent(String eventType, String event) {
        Bundle bundle = new Bundle();
        bundle.putString(eventType, event);
        mFirebaseAnalytics.logEvent(eventType, bundle);
    }

    /**
     * Create a static method with an id of updateWidget with a Context argument
     * @param context instance of Context
     */
    private static void updateWidget(Context context) {
        ComponentName name = new ComponentName(context, BooksWidgetProvider.class);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
        Intent intent = new Intent(context, BooksWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }

    /**
     * Create a method with an id of saveBook and calls the saveBookToList method.
     * This method requires a Book, a String, and a boolean argument. This method is called
     * whenever a book is being saved or moved to a category
     * @param book the book itself
     * @param nodeName the category name
     * @param fromMoveBook the category the book was moved from
     */
    private void saveBook(Book book, String nodeName, Boolean fromMoveBook) {
        logEvent("moved_book_to_" + nodeName, nodeName);
        saveBookToList(book, nodeName);
    }

    /**
     * Create a method with an id of saveBookToList and is called by the saveBook method.
     * This method is called when the user selects to save a book through the addValueEventListener
     * @param book the book itself
     * @param nodeName the category name
     */
    private void saveBookToList(Book book, String nodeName) {
            mSavedBookReference = mDatabase.getReference()
                    .child(mCurrentUser.getUid())
                    .child(nodeName.toLowerCase())
                    .child(String.valueOf(book.getId()));

            mSavedBookReference.addValueEventListener(this);
            mSavedBookReference.setValue(book);

      //Toast.makeText(mContext, "You have already added this book ", Toast.LENGTH_SHORT).show();

      //Toast.makeText(mContext, "Book Id: " + book.getId(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Create a method with an id of onDataChange with a DataSnapshot argument
     * @param dataSnapshot an instance of DataSnapshot
     */
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //Log.d(TAG, "onDataChange: going to update the widget");
        updateWidget(mContext);
    }

    /**
     * Create a method with an id of onCancelled with a DatabaseError argument
     * @param databaseError an instance of DatabaseError
     */
    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    // Create an interface with an id of GetBookFromListListener with two void methods
    public interface GetBookFromListListener {
        void onReceivedBook(Book book);

        void onError();
    }
}
