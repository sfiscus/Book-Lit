package com.sfiscusdevelopment.sfiscus.booklit.bookLibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sfiscusdevelopment.sfiscus.booklit.R;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.BookCategories;

/**
 * Create a class with an id of BookLibraryModel which extends BottomSheetDialogFragment. This class
 * is responsible for handling the list of book options. This class contains methods that allows for
 * the user to take one of two options. The user is able to decide if the book should be deleted or
 * moved.
 */
public class BookLibraryModel extends BottomSheetDialogFragment {

    // Create a static final String instance variable with an id of ARG_CURRENT_LIST with a value of current_list
    public static final String ARG_CURRENT_LIST = "current_list";

    // Create a static final String instance variable with an id of ARG_SAVE_LOCATION with a value of save_location
    public static final String ARG_SAVE_LOCATION = "save_location";

    // Create a static cinal String instance variable with an id of OPTION_DELETE with a value of delete
    public static final String OPTION_DELETE = "delete";

    // Create a String instance variable with an id of moveToTopReads
    private String moveToTopReads;

    // Create a String instance variable with an id of moveToReading
    private String moveToReading;

    // Create a String instance variable with an id of moveToPastReads
    private String moveToPastReads;

    // Create a String instance variable with an id of moveToFutureReads
    private String moveToFutureReads;

    // Create a String instance variable with an id of savedToTopTen
    private String savedToTopTen;

    // Create a String instance variable with an id of savedToReading
    private String savedToReading;

    // Create a String instance variable with an id of savedToPastReads
    private String savedToPastReads;

    // Create a String instance variable with an id of savedToFutureReads
    private String savedToFutureReads;

    OnModalOptionSelected listener;

    // Create a public interface with an id of OnModalOptionSelected
    public interface OnModalOptionSelected {

        /**
        * Create a method with an id of onModalOptionSelected that requires a String argument
        * @param optionSelected returns the {@value OPTION_DELETE} or the name of the list to move to.
        */
        void onModalOptionSelected(String optionSelected);
    }

    /**
     * Create a static BookLibraryModel method with an id of newInstance. This method requires a
     * String argument and creates a new instance of the BookLibraryModel fragment.
     * @param currentListName the current category name
     * @return a statement that returns a fragment
     */
    static BookLibraryModel newInstance(@BookCategories.BOOK_LISTS String currentListName) {
        BookLibraryModel fragment = new BookLibraryModel();

        // create the bundle
        Bundle args = new Bundle();
        args.putString(ARG_CURRENT_LIST, currentListName);
        fragment.setArguments(args);
        return fragment;
    }

    // Create a no argument constructor for the class
    public BookLibraryModel() {
    }

    static BookLibraryModel bookSave(@BookCategories.BOOK_LISTS String saveLocation) {

        BookLibraryModel bookLibraryModel = new BookLibraryModel();

        Bundle bundle = new Bundle();
        bundle.putString(ARG_SAVE_LOCATION, saveLocation);
        bookLibraryModel.setArguments(bundle);
        return bookLibraryModel;

    }

    /**
     * Create a View method with an id of onCreateView which requires a LayoutInflater, a Viewgroup,
     * and a Bundle argument. This method is called when the fragment instantiates the user interface
     * view. This method also contains the neccessary code to determine which button the user selects
     * to move the book to another category
     * @param inflater the object used to inflate an views in the fragment
     * @param container the parent view that the fragment is attached to
     * @param savedInstanceState the fragment re-constructed from the previous saved state
     * @return a statement that returns the view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.book_library_model, container, false);

        final String action = getArguments().getString(ARG_CURRENT_LIST);
        final String moveToListAction;
        final String moveToListTextOption;

        moveToTopReads = BookCategories.TOP_READS;
        moveToReading = BookCategories.READING;
        moveToPastReads = BookCategories.PAST_READS;
        moveToFutureReads = BookCategories.FUTURE_READS;

        savedToTopTen = getResources().getString(R.string.list_top_reads);
        savedToReading = getResources().getString(R.string.list_reading);
        savedToPastReads = getResources().getString(R.string.list_past_reads);
        savedToFutureReads = getResources().getString(R.string.list_future_reads);


        TextView readingAction = (TextView) v.findViewById(R.id.btn_reading);
        readingAction.setText(getResources().getString(R.string.book_list_activity_move_to,savedToReading));
        readingAction.setContentDescription(savedToReading);

        TextView pastAction = (TextView) v.findViewById(R.id.btn_past_reads);
        pastAction.setText(getResources().getString(R.string.book_list_activity_move_to, savedToPastReads));
        pastAction.setContentDescription(savedToPastReads);

        TextView futureAction = (TextView) v.findViewById(R.id.btn_future_reads);
        futureAction.setText(getResources().getString(R.string.book_list_activity_move_to, savedToFutureReads));
        futureAction.setContentDescription(savedToFutureReads);

        TextView topTenAction = (TextView) v.findViewById(R.id.btn_top_reads);
        topTenAction.setText(getResources().getString(R.string.book_list_activity_move_to, savedToTopTen));
        topTenAction.setContentDescription(savedToTopTen);

        readingAction.findViewById(R.id.btn_reading).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_reading:
                    listener.onModalOptionSelected(moveToReading);
                    break;
                    case R.id.btn_past_reads:
                    listener.onModalOptionSelected(moveToPastReads);
                    break;
                    case R.id.btn_future_reads:
                    listener.onModalOptionSelected(moveToFutureReads);
                    break;
                    case R.id.btn_top_reads:
                    listener.onModalOptionSelected(moveToTopReads);
                    break;
                }
            }
        });

            pastAction.findViewById(R.id.btn_past_reads).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onModalOptionSelected(moveToPastReads);
                }
            });

            futureAction.findViewById(R.id.btn_future_reads).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onModalOptionSelected(moveToFutureReads);
                }
            });

            topTenAction.findViewById(R.id.btn_top_reads).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onModalOptionSelected(moveToTopReads);
                }
              });

            v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                listener.onModalOptionSelected(OPTION_DELETE);
                }
            });

            return v;
        }

    /**
     * Create a method with an id of setItemSelectedListener which requires a
     * OnModalOptionSelected argument. This method registers a callback to be invoked when an item
     * in the AdapterView has been selected
     * @param listener the callback that will run
     */
    public void setItemSelectedListener(OnModalOptionSelected listener) {
            this.listener = listener;
        }
}
