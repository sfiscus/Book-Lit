package com.sfiscusdevelopment.sfiscus.booklit.bookLibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.sfiscusdevelopment.sfiscus.booklit.R;
import com.sfiscusdevelopment.sfiscus.booklit.entities.Book;
import com.google.firebase.database.DatabaseReference;

/**
 * Create a class with an id of BookLibraryAdapter. This class extends FirebaseRecyclerAdapter.
 * This class is an adapter class for BookLibraryFragment. This class is responsible for populating
 * the viewHolder
 */
class BookLibraryAdapter extends FirebaseRecyclerAdapter<Book, BookLibraryViewHolder> {

    // Create a DatabaseReference variable with an id of mBookListReference
    private final DatabaseReference mBookListReference;

    // Create a BookListAdapterEventsListener variable with an id of mListListerner
    private final BookListAdapterEventsListener mListListerner;

    // Create a BookListItemListener variable with an id of mItemSelectedListener
    private BookLibraryViewHolder.BookListItemListener mItemSelectedListener;


    BookLibraryAdapter(DatabaseReference mBookListReference,
                       BookListAdapterEventsListener listerner,
                       BookLibraryViewHolder.BookListItemListener mItemSelectedListener) {

        // Call the super constructor with predefined values and the received mBookListReference
        super(Book.class, R.layout.book_item, BookLibraryViewHolder.class, mBookListReference);
        this.mBookListReference = mBookListReference;
        this.mListListerner = listerner;
        this.mItemSelectedListener = mItemSelectedListener;
    }

    /**
     * Create method with an id of onCreateViewHolder. This method is called when the RecyclerView
     * needs a new RecyclerView.Viewholder of the given type to respresent an item. It requires
     * a Viewgroup and an int argument
     * @param parent an argument of ViewGroup that represents the parent view
     * @param viewType an int argument variable
     * @return a statement that returns a new BookLibraryViewHolder
     */
    @Override
    public BookLibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /**
         * FirebaseRecycler Adapter does this itself but if the methods need to be overridden
         * then pass the context and a listener
         */
        Context context = parent.getContext();
        View searchViewItem = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new BookLibraryViewHolder(searchViewItem, mItemSelectedListener, context);
    }

    /**
     * Create method with an id of populateViewHolder that is generated from the
     * FirebaseRecyclerAdapter and requires three arguments
     * @param viewHolder an argument of BookLibraryViewHolder
     * @param model an argument of Book
     * @param position an int that represents the current position
     */
    @Override
    protected void populateViewHolder(BookLibraryViewHolder viewHolder, Book model, int position) {
        viewHolder.setmBook(model);
        mListListerner.onListShowed();
    }

    /**
     * Create method with an id of getItemCount which returns the total number of items in the
     * data set held by the adapter
     * @return a statement that returns the count instance variable
     */
    @Override
    public int getItemCount() {
        int count = super.getItemCount();

        if (count <= 0) {
        mListListerner.onNoBooksInList();
        }else{
        mListListerner.onBooksInList();
        }

        return count;
    }

    // Create interface which will be implemented by BookLibraryFragment class
    interface BookListAdapterEventsListener {
        void onListShowed();
        void onNoBooksInList();
        void onBooksInList();
    }

}
