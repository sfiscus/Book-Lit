package com.sfiscusdevelopment.sfiscus.booklit.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sfiscusdevelopment.sfiscus.booklit.R;
import com.sfiscusdevelopment.sfiscus.booklit.entities.Book;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.BookCategories;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Create a class with an id of BooksRemoteViewsFactory that implements
 * RemoteViewsService.RemoteViewsFactory and ValueEventListener. This class implements a thin wrapper
 * around the {@link android.widget.Adapter} interface. This returns a collection as {@link RemoteViews}
 * to include it in the widget. This class gets the data to populate the widget views
 */
class BooksRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory, ValueEventListener {

    // Create a static final String instance variable with an id of TAG with a value of BooksRemoteViewsFactor
    private static final String TAG = "BooksRemoteViewsFactory";

    // Create a final Context instance variable with an id of mContext
    private final Context mContext;

    // Create a final Intent instance variable with an id of mIntent
    private final Intent mIntent;

    // Create a DatabaseReference instance variable with an id of mDatabaseReference
    private DatabaseReference mDatabaseReference;

    // Create an ArrayList instance variable with an id of books
    private ArrayList<Book> books = new ArrayList<Book>();

    // Create a constructor with a Context and an Intent argument
    BooksRemoteViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        this.mIntent = intent;
    }

    /**
     * This is the main processing method for the BooksRemoteViewsFactory class. This class sets up
     * any conections or cursors to the data source. Any downloading or creating content should be
     * deffered to onDataSetChanged
     */
    @Override
    public void onCreate() {
        // In onCreate() you set up any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
        getBooks();
    }

    // Create a method with an id of getBooks. This method verifies the user and retrieves the books associated
    private void getBooks() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            mDatabaseReference = FirebaseDatabase.getInstance().getReference()
            .child(user.getUid())
            .child(BookCategories.TOP_READS);
            mDatabaseReference.addValueEventListener(this);
        }

    }

    // Create a method with an id of onDataSetChanged this method is called for any downloading or content creating
    @Override
    public void onDataSetChanged() {
        getBooks();
    }

    // Create a method with an id of onDestroy perform a final cleanup before activity is destroyed
    @Override
    public void onDestroy() {

    }

    /**
     * Create a method with an id of getCount that returns the book size
     * @return the book size
     */
    @Override
    public int getCount() {
        return books.size();
    }

    /**
     * Create a method with an id of getViewAt. This method requires an int argument and deals
     * with a remote view based on the app widget
     * @param position this is an int variable that represents the current position
     * @return a statement that returns the remoteView
     */
    @Override
    public RemoteViews getViewAt(int position) {
        Book book = new Book();
        try {
        book = books.get(position);

        // Construct a remote views item based on the app widget item XML file,
        // and set the text based on the position.
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        // Set text to the views
        remoteViews.setTextViewText(R.id.widget_txt_book_title, book.getTitle());
        remoteViews.setTextViewText(R.id.widget_txt_author_name, book.getAuthor().getName());

        // Set content description
        remoteViews.setContentDescription(R.id.widget_txt_book_title, book.getTitle());
        remoteViews.setContentDescription(R.id.widget_txt_author_name, book.getAuthor().getName());

        // Set clickable button
        // Setting a fill-intent, which will be used to fill in the pending intent template
        // that is set on the collection view in ListOfStories provider.
        Bundle extras = new Bundle();
        extras.putString(BooksWidgetProvider.CLICKED_ITEM_BOOK_ID, String.valueOf(book.getId()));
        extras.putString(BooksWidgetProvider.CLICKED_ITEM_BOOK_TITLE, book.getTitle());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        // Setting the action, so the widget provider can identify the event of this view
        // being clicked.
        fillInIntent.setAction(BooksWidgetProvider.CLICKED_ITEM_ACTION);

        // Make it possible to distinguish the individual on-click
        // action of a given item
        remoteViews.setOnClickFillInIntent(R.id.widget_btn_select, fillInIntent);

        return remoteViews;

        } catch (IndexOutOfBoundsException e) {
        Log.e(TAG, "getViewAt: ", e);
        }

        return null;
    }

    // Create a method with an id of getLoadingView. This method returns a null
    @Override
    public RemoteViews getLoadingView() {
    return null;
  }

    // Create a method with an id of getViewTypeCount. This method returns a value of one
    @Override
    public int getViewTypeCount() {
    return 1;
  }

    /**
     * Create a method with an id of getItemId and requires an int argument. This method gets the
     * item id base on position
     * @param position an int that represents the current book position
     * @return a statement that returns the position
     */
    @Override
    public long getItemId(int position) {
    return position;
  }

    // Create a method with an id of hasStableIds and returns a value of false
    @Override
    public boolean hasStableIds() {
    return false;
  }

    /**
     * Create a method with an id of onDataChange and requires a DataSnapshot argument. This method
     * is called with a snapshot of the data at this location
     * @param dataSnapshot the current data at the location
     */
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        books.clear();
        for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
        Book book = bookSnapshot.getValue(Book.class);
        books.add(book);
        }
        mDatabaseReference.removeEventListener(this);
    }

    /**
     * Create a method with an id of onCancelled and requires a DatabaseError argument. This method
     * will be triggered in the event that the listener either failed at the server, or is removed
     * as a result of the security and Firebase rules
     * @param databaseError a description of the error that occurred
     */
    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

}
