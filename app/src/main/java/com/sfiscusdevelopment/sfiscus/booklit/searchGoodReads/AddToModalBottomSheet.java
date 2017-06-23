package com.sfiscusdevelopment.sfiscus.booklit.searchGoodReads;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sfiscusdevelopment.sfiscus.booklit.R;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.BookCategories;

/**
 * Create a class with an id of AddToModalBottomSheet that extends BottomSheetDialogFragment and
 * implements View.OnClickListener. This class contains methods that take place one the user clicks
 * on category to send the book to.
 */
public class AddToModalBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    // Create a OnModalOptionSelected with an id of listener
    private OnModalOptionSelected listener;

    // Create an interface with an id of OnModalOptionSelected
    interface OnModalOptionSelected {
        void onModalOptionSelected(String selectedItem);
    }

    // Create a static AddToModalBottomSheet method with an id of newInstance
    static AddToModalBottomSheet newInstance() {
    return new AddToModalBottomSheet();
  }

    // Create a no argument constructor
    public AddToModalBottomSheet() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.book_add, container, false);

        Resources res = getContext().getResources();

        v.findViewById(R.id.container).setContentDescription(res.getString(R.string.search_activity_add_to));

        View btnReading   = v.findViewById(R.id.btn_reading);
        View btnPastReads = v.findViewById(R.id.btn_past_reads);
        View btnFutureReads = v.findViewById(R.id.btn_future_reads);
        View btnTopTen = v.findViewById(R.id.btn_top_reads);

        // Set content descriptions
        setButtonContentDescription(btnTopTen, R.string.search_activity_add_to);
        setButtonContentDescription(btnReading, R.string.search_activity_reading);

        // Set listeners
        btnTopTen.setOnClickListener(this);
        btnReading.setOnClickListener(this);
        btnPastReads.setOnClickListener(this);
        btnFutureReads.setOnClickListener(this);

        return v;
    }

    private void setButtonContentDescription(View v, int stringResouce) {
        v.setContentDescription(
            getResources().getString(R.string.accessibility_search_activity_add_to, // Format string
            getResources().getString(stringResouce))
        );
    }

    public void setItemSelectedListener(OnModalOptionSelected listener) {
        this.listener = listener;
    }

    // Implementations
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_reading:
            listener.onModalOptionSelected(BookCategories.READING);
            break;
        case R.id.btn_past_reads:
            listener.onModalOptionSelected(BookCategories.PAST_READS);
            break;
        case R.id.btn_future_reads:
            listener.onModalOptionSelected(BookCategories.FUTURE_READS);
            break;
        case R.id.btn_top_reads:
            listener.onModalOptionSelected(BookCategories.TOP_READS);
            break;
        }
    }
}
