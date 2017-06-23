package com.sfiscusdevelopment.sfiscus.booklit.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * This service allows the remote adapter a {@link RemoteViewsFactory}
 * implementation to request {@link android.widget.RemoteViews}.
 */
public class BooksWidgetService extends RemoteViewsService {
  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new BooksRemoteViewsFactory(this.getApplicationContext(), intent);
  }
}
