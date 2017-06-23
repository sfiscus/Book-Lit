package com.sfiscusdevelopment.sfiscus.booklit.bookLibrary;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.sfiscusdevelopment.sfiscus.booklit.R;
import com.sfiscusdevelopment.sfiscus.booklit.entities.Book;
import com.sfiscusdevelopment.sfiscus.booklit.entities.BookWrapper;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.BookCategories;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Create a class with an id of BookLibraryFragment. This class extends Fragment and implements
 * BookLibraryInterface.View, BookLibraryAdapter.BookListAdapterEventsListener,
 * BookLibraryViewHolder.BookListItemListener, BookLibraryModel.OnModalOptionSelected. This class
 * contains methods that are responsible for displaying data in the RecyclerView. This class also
 * is responsible for the notifications that the user receives
 *
 */
public class BookLibraryFragment extends Fragment implements BookLibraryInterface.View,
    BookLibraryAdapter.BookListAdapterEventsListener, BookLibraryViewHolder.BookListItemListener,
    BookLibraryModel.OnModalOptionSelected {

    // Create a String instance variable with an id of TAG and a value of BookLibraryFragment
    private String TAG = "BookLibraryFragment";

    // Create a static final String instance variable with an id of ARG_LIST_NAME and a value of list_name
    private static final String ARG_LIST_NAME = "list_name";

    // Create a BookLibraryPresenter instance variable with an id of mPresenter
    private BookLibraryPresenter mPresenter;

    // Create a BookLibraryInteractor instance variable with an id of mInteractor
    private BookLibraryInteractor mInteractor;

    // Create a RecyclerView instance variable with an id of mRecyclerView
    private RecyclerView mRecyclerView;

    // Create a FirebaseRecyclerAdapter instance variable with an id of mAdapter
    private FirebaseRecyclerAdapter mAdapter;

    // Create a GridLayoutManager instance variable with an id mLayoutManager
    private GridLayoutManager mLayoutManager;

    // Create a FrameLayout instance variable with an id of mFrameLayout
    private FrameLayout mFrameLayout;

    // Create a ProgressBar instance variable with an id of mProgressBar
    private ProgressBar mProgressBar;

    // Create a BookLibraryModel instance variable with an id mModalBottomSheet
    private BookLibraryModel mModalBottomSheet;

    // Create a Book instance variable with an id of mSelectedBook
    private Book mSelectedBook;

    // Create a String instance variable with an id of mCurrentListName
    private String mCurrentListName;

    // Create a View instance variable with an id of mNoBookIndicator
    private View mNoBookIndicator;

    // Create a Textview instance variable with an id of mNoBookIndicatorText
    private TextView mNoBookIndicatorText;

    // Create a boolean instance variable with an id of mIsListShown
    private boolean mIsListShown;

    // Create an int instance variable with an id of mCount
    private int mCount = 0;

    // Create a no argument constuctor
    public BookLibraryFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given the
     * Firebase list node name.
     * @param selectedList a string representing the selected list
     * @return a statement that returns fragment
     */
    public static BookLibraryFragment newInstance(@BookCategories.BOOK_LISTS String selectedList) {
        BookLibraryFragment fragment = new BookLibraryFragment();

        // set correct debug tag name
        fragment.TAG = fragment.TAG.concat("_" + selectedList);

        Bundle args = new Bundle();
        args.putString(ARG_LIST_NAME, selectedList);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Create method with an id of onCreateView. This method is called to have the fragment instantiate
     * its user interface view.
     * @param inflater an argument of LayoutInflater
     * @param container an argument of ViewGroup
     * @param savedInstanceState an argument of Bundle
     * @return a statement that returns rootView
     */
    @SuppressWarnings("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.book_library_fragment, container, false);

        // Wire widgets
        mFrameLayout = (FrameLayout) rootView.findViewById(R.id.layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mNoBookIndicator = rootView.findViewById(R.id.no_books_indicator);
        mNoBookIndicatorText = (TextView) rootView.findViewById(R.id.no_books_indicator_text);

        mCurrentListName = getArguments().getString(ARG_LIST_NAME);
        mNoBookIndicatorText.setText(getResources().getString(R.string.book_list_activity_no_book_lists_message));

        mInteractor = new BookLibraryInteractor(getActivity().getApplication());
        mPresenter = new BookLibraryPresenter(this, mInteractor);

        mModalBottomSheet = BookLibraryModel.newInstance(mCurrentListName);
        mLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.book_list_fragment_columns));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mPresenter.getBookFromList(mCurrentListName);

        return rootView;
    }

    // Create a method with an id of onDestroy that is called when the fragment is no longer in use
    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    // Create a method with an id of showBooks
    @Override
     public void showBooks(ArrayList<BookWrapper> booksArray) {
    }

    // Create a method with an id of showNoBooksFound which is called when the RecyclerView is empty
    @Override
    public void showNoBooksFound() {
        mNoBookIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    // Create a method with an id of displayError
    @Override
    public void displayError() {

    }

    // Create a method with an id of showLoading which is called whenever the RecyclerView is loading data
    @Override
    public void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mNoBookIndicator.setVisibility(View.GONE);
    }

    // Create a method with an id of hideLoading which is called when the loading of the RecyclerView has been complete
    @Override
    public void hideLoading() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mNoBookIndicator.setVisibility(View.GONE);
    }

    /**
     * Create a method with an id of displaySnackBar for the snackbar message and requires
     * a String argument
     * @param message a String argument
     */
    @Override
    public void displaySnackBar(String message) {
        Snackbar.make(mFrameLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Create a method with an id of setupRecyclerViewWithReference and requires a
     * DatabaseReference argument. This method sets the Adapter for the mRecyclerView variable
     * @param bookListRef an argument of DatabaseReference
     */
    @Override
    public void setupRecyclerViewWithReference(DatabaseReference bookListRef) {
        mAdapter = new BookLibraryAdapter(bookListRef, this, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
    * This is not with the mvp structure as we need a callback
    * from the mAdapter to let us know when we should hide the loading.
    */
    @Override
    public void onListShowed() {
        if (!mIsListShown) {
        mIsListShown = true;

        mCount = mRecyclerView.getAdapter().getItemCount();

            // Create an if statement to determine what notification the user should be receiving
            if(mCurrentListName == "pastReads") {
                if (mCount == 10) {
                    sendNotificationTen();
                }

                if (mCount == 25) {
                    sendNotificationTwentyFive();
                }

                if (mCount == 50) {
                    sendNotificationFifty();
                }

                if (mCount == 100) {
                    sendNotificationOneHundred();
                }

                if (mCount == 500) {
                    sendNotificationFiveHundred();
                }

                if (mCount == 1000) {
                    sendNotificationOneThousand();
                }
            }
        }
    }

    /**
     *     Create a method with an id of onNoBooksInList which calls two methods using the
     *     mPresenter instance variable
     */
    @Override
    public void onNoBooksInList() {
        mPresenter.showNoBooksFound();
        mPresenter.displayMessage(getResources().getString(R.string.book_list_activity_no_book_lists_message));
    }

    /**
     *     Create a method with an id of onBookInList which calls one method using the mPresenter
     *     instance variable.
     */
    @Override
    public void onBooksInList() {
        mPresenter.showBooks();
    }

    /**
     * Create a method with an id of onItemClickListener and requires a Book argument. This method
     * is a callback method that is invoked when the BookLibraryModal has been clicked
     * @param book the book itself
     */
    @Override
    public void onItemClickListener(Book book) {
        mSelectedBook = book;
        mModalBottomSheet.show(getActivity().getSupportFragmentManager(), "item_modal");
        mModalBottomSheet.setItemSelectedListener(this);
    }

    /**
     * Create a method with an id of onModalOptionSelected and requires a String argument.
     * The mPresenter instance variable calls bookListOperation method from the BookLibraryPresenter
     * class. The bookListOperation method requires three arguments.
     * @param optionSelected the option that was selected
     */
    @Override
    public void onModalOptionSelected(String optionSelected) {
        mPresenter.bookListOperation(optionSelected, mCurrentListName, mSelectedBook);
        mSelectedBook = null;
        mModalBottomSheet.dismiss();
    }

        // All methods required for each of the notifications the user will receive
        public void sendNotificationTen(){
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getActivity())
                            .setSmallIcon(R.drawable.book_logo)
                            .setContentTitle("Book Notification")
                            .setContentText("You have read 10 books!");

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1766, mBuilder.build());
        }

        public void sendNotificationTwentyFive(){
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getActivity())
                            .setSmallIcon(R.drawable.book_logo)
                            .setContentTitle("Book Notification")
                            .setContentText("You have read 25 books!");

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1766, mBuilder.build());
        }

        public void sendNotificationFifty(){
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getActivity())
                            .setSmallIcon(R.drawable.book_logo)
                            .setContentTitle("Book Notification")
                            .setContentText("You have read 50 books!");

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1766, mBuilder.build());
        }

        public void sendNotificationOneHundred(){
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getActivity())
                            .setSmallIcon(R.drawable.book_logo)
                            .setContentTitle("Book Notification")
                            .setContentText("You have read 100 books!");

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1766, mBuilder.build());
        }

        public void sendNotificationFiveHundred(){
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getActivity())
                            .setSmallIcon(R.drawable.book_logo)
                            .setContentTitle("Book Notification")
                            .setContentText("You have read 500 books!");

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1766, mBuilder.build());
        }

        public void sendNotificationOneThousand(){
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getActivity())
                            .setSmallIcon(R.drawable.book_logo)
                            .setContentTitle("Book Notification")
                            .setContentText("You have read 1000 books!");

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1766, mBuilder.build());
        }
}
