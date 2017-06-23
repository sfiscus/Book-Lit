package com.sfiscusdevelopment.sfiscus.booklit.bookLibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.sfiscusdevelopment.sfiscus.booklit.LoginActivity;
import com.sfiscusdevelopment.sfiscus.booklit.R;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.BookCategories;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sfiscusdevelopment.sfiscus.booklit.searchGoodReads.SearchActivity;

/**
 * Create a class with an id of BookLibraryActivity. This class extends AppCompatActivity.
 * This class contains code for fragment that will appear directly after login has been successful.
 */
public class BookLibraryActivity extends AppCompatActivity {

  /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    // An int variable with an id of curTabId
    private int curTabId;

    /**
     * This is the main processing method for the BookLibraryActivity class
     * @param savedInstanceState argument variable of Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_library_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Clicking button will take user to the SearchActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openSearchActivity = new Intent(BookLibraryActivity.this, SearchActivity.class);
                BookLibraryActivity.this.startActivity(openSearchActivity);
            }
        });
    }


    /**
     * This is the main method which inflates the menu bar with data
     * @param menu argument variable of Menu
     * @return a statement that returns a boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    /**
     * This is the main method which is called when a menu item is selected
     * @param item an argument variable of MenuItem
     * @return statement that returns a boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // If statement that if logout option selected user is taken to LoginActivity
        if (id == R.id.action_logout) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(BookLibraryActivity.this, LoginActivity.class));
                            finish();
                        }
                    });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        /**
         * Method with id of SectionPagerAdapter with an instance of FragmentManager
         * @param fragmentManager an instance variable of FragmentManager
         */
        public SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        /**
         * Fragment instance with an id of getItem that will determine which fragment is currently
         * being viewed by the user by checking the current position. The getItem method is called
         * to instantiate the fragment for the given page.
         * @param position an int argument variable that determines the current category position
         * @return a statement that returns a null value
         */
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return BookLibraryFragment.newInstance(BookCategories.READING);
                case 1:
                    return BookLibraryFragment.newInstance(BookCategories.PAST_READS);
                case 2:
                    return BookLibraryFragment.newInstance(BookCategories.FUTURE_READS);
                case 3:
                    return BookLibraryFragment.newInstance(BookCategories.TOP_READS);
            }
            return null;
        }

        /**
         * Method with an id of getCount that shows the total pages being displayed
         * @return a statement that returns the total number of views
         */
        @Override
        public int getCount() {
            return 4;
        }

        /**
         * This method verifies the current position and returns the appropriate page title
         * @param position an int instance variable that determines the current category position
         *                 and displays the corresponding page title
         * @return a statement that returns a null value
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.main_activity_reading);
                case 1:
                    return getString(R.string.main_activity_past_reads);
                case 2:
                    return getString(R.string.main_activity_future_reads);
                case 3:
                    return getString(R.string.main_activity_top_reads);

            }
            return null;
        }
    }
}
