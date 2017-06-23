package com.sfiscusdevelopment.sfiscus.booklit.bookLibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sfiscusdevelopment.sfiscus.booklit.R;
import com.sfiscusdevelopment.sfiscus.booklit.entities.Book;
import com.squareup.picasso.Picasso;

/**
 * Create a class with an id of BookLibraryViewHolder. The class extends RecyclerView.ViewHolder.
 * This class functions as an item view with metadata for the book library withing the RecyclerView
 */
class BookLibraryViewHolder extends RecyclerView.ViewHolder {

    // A public interface with an id of BookListItemListener which is implemented by other classes
    public interface BookListItemListener {
        void onItemClickListener(Book book);
    }

    // Create a BookListItemListener with an id of mListener
    private BookListItemListener mListener;

    // Create a TextView instance variable with an id of mTitle
    private TextView mTitle;

    // Create a TextView instance variable with an id of mAuthor
    private TextView mAuthor;

    // Create a RatingBar instance variable with an id of mRating
    private RatingBar mRating;

    // Create an ImageView instance variable with an id of mCover
    private ImageView mCover;

    // Create a Context instance variable with an id of mContext
    private Context mContext;

    // Create a View instance variable with an id of mLayout
    private View mLayout;

    // Create a Book instance variable with an id of mBook
    private Book mBook;

    /**
     * Create a constructor for the class that requires three arguments: a View, a BookListItemListener,
     * and a Context
     * @param itemView a View argument
     * @param listItemListerner a BookListItemListener argument
     * @param mContext a Context argument
     */
    public BookLibraryViewHolder(View itemView, BookListItemListener listItemListerner, Context mContext) {
        super(itemView);

        this.mContext = itemView.getContext();
        this.mContext = mContext;
        mListener = listItemListerner;
        mLayout = itemView.findViewById(R.id.layout);
        mTitle = (TextView) itemView.findViewById(R.id.txt_title);
        mAuthor = (TextView) itemView.findViewById(R.id.txt_author);
        mRating = (RatingBar) itemView.findViewById(R.id.rating_stars);
        mCover = (ImageView) itemView.findViewById(R.id.img_cover);
    }

    /**
     * Create a set method with an id of setmBook
     * @param listBook the book itself
     */
    public void setmBook(final Book listBook) {
        mBook = listBook;
        mTitle.setText(mBook.getTitle());
        mTitle.setContentDescription(mBook.getTitle());

        String authorName = mBook.getAuthor().getName();
        mAuthor.setText(mContext.getResources()
        .getString(R.string.search_activity_by_author, authorName ));
        mAuthor.setContentDescription(authorName);

        mRating.setRating(Float.parseFloat(mBook.getAverageRating()));
        mRating.setContentDescription(mBook.getAverageRating());

        Picasso.with(mContext).load(mBook.getImageUrl()).fit().into(mCover);

        mLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mListener.onItemClickListener(mBook);

      }
    });

      //Toast.makeText(mContext, "Book Id: " + mBook.getId(), Toast.LENGTH_SHORT).show();
    }
}