package com.sfiscusdevelopment.sfiscus.booklit.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.sfiscusdevelopment.sfiscus.booklit.R;
import com.sfiscusdevelopment.sfiscus.booklit.entities.Book;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.BookCategories;
import com.sfiscusdevelopment.sfiscus.booklit.repositories.FirebaseActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Create a class with an id of BooksWidgetProvider that extends AppWidgetProvider. This class
 * contains methods that allow the ability to see which element on the widget was clicked. Also
 * it also for an update once there is a change in the view
 */
public class BooksWidgetProvider extends AppWidgetProvider {

    // Create a static final String with an id of TAG and a value of BooksWidgetProvider
    private static final String TAG = "BooksWidgetProvider";

    // Create a static final String with an id of CLICKED_ITEM_ACTION and a value of widget_action_clicked
    public static final String CLICKED_ITEM_ACTION  = "widget_action_clicked";

    // Create a static final String with an id of CLICKED_ITEM_BOOK_ID and a value of widget_item_clicked_book_id
    public static final String CLICKED_ITEM_BOOK_ID = "widget_item_clicked_book_id";

    // Create a static final String with an id of CLICKED_ITEM_BOOK_TITLE and a value of widget_item_clicked_book_title
    public static final String CLICKED_ITEM_BOOK_TITLE = "widget_item_clicked_book_title";

    /**
     * Create a method with an id of onUpdate which requires a Context, AppWidgetManager, and an
     * int[] argument. This method updates the widgets
     * @param context the current state of the application
     * @param appWidgetManager updates the AppWidget state
     * @param appWidgetIds a list of AppWidget ids that have been bound to the given AppWidget provider
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so all widgets must be updated
        for (int appWidgetId : appWidgetIds) {

        // Set up the intent that starts the BooksWidgetService, providing the views for this collection
        Intent intent = new Intent(context, BooksWidgetService.class);

        // Add the app widget ID to the intent extras.
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        // Instantiate the RemoteViews object for the app widget layout using the "RemoteViews adapter"
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.book_widget);

        // Set up the RemoteViews object to use a RemoteViews adapter populating the data.
        // This adapter connects to a RemoteViewsService  through the specified intent.
        remoteViews.setRemoteAdapter(R.id.widget_list, intent);


        // The empty view is displayed when the collection has no items.
        // It should be in the same layout used to instantiate the RemoteViews object above.
        remoteViews.setEmptyView(R.id.widget_list, R.id.widget_empty_view);

        // This section makes it possible for items to have individualized behavior.
        // It does this by setting up a pending intent template. Individuals items of a collection
        // cannot set up their own pending intents. Instead, the collection as a whole sets
        // up a pending intent template, and the individual items set a fillInIntent
        // to create unique behavior on an item-by-item basis.
        Intent clickIntent = new Intent(context, BooksWidgetProvider.class);

        // Set the action for the intent and When the user touches a particular view,
        // it will have the effect of broadcasting TOAST_ACTION.
        clickIntent.setAction(BooksWidgetProvider.CLICKED_ITEM_ACTION);
        clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setPendingIntentTemplate(R.id.widget_list, toastPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
        }

    }

    /**
     * Create a method with an id of onReceive that requires a Context and an Intent argument.
     * This method is called when the BroadcastReceiver receives an Intent broadcast.
     * This method is where we are able to see which element on the widget was clicked
     * @param context the current state of the application
     * @param intent abstract decription of an operation to be performed
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);

        if (intent.getAction().equals(CLICKED_ITEM_ACTION)) {
        String bookId = intent.getStringExtra(CLICKED_ITEM_BOOK_ID);

        final FirebaseActivity firebaseActivity = FirebaseActivity.getmInstance();
        firebaseActivity.setmContext(context);
        firebaseActivity.setFirebaseAnalytics(FirebaseAnalytics.getInstance(context));

        // First we get the book from the corresponding id
        FirebaseActivity.GetBookFromListListener listener = new FirebaseActivity.GetBookFromListListener() {

            /**
             * Create a method with an id of onReceivedBook requiring a Book argument. This method
             * moves the book once it has been received to the database
             * @param book the book itself
             */
            @Override
            public void onReceivedBook(Book book) {
            // Then we send it to "Reading" list.
            firebaseActivity.moveBook(BookCategories.READING, BookCategories.PAST_READS, book);
            firebaseActivity.logEvent("moved_book", "from_widget");
            }

            @Override
            public void onError() {
          Log.d(TAG, "onError: Database error");
        }
        };

        firebaseActivity.getBookFromIdInList(BookCategories.READING, bookId, listener);
        }

        super.onReceive(context, intent);
    }

}
