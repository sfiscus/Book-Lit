package com.sfiscusdevelopment.sfiscus.booklit.customViews

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Create a class with an id of EndlessCrollListener. Kotlin programming language is used for this
 * class setting it apart from many of the other classes in this project. Kotlin is designed to
 * run on a JVM while allowing it to be used interchangeably with Java. This class is contains
 * code for scrolling of the RecyclerView
 */
class EndlessScrollListener(val listener: () -> Unit) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibileItemCount: Int = recyclerView?.layoutManager?.childCount!!
        val totalItemCount: Int = recyclerView?.layoutManager?.itemCount!!
        val pastVisibleItems: Int = (recyclerView?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (visibileItemCount + pastVisibleItems >= totalItemCount) listener.invoke()

    }

    fun onBottomListener(listener: () -> Unit) {}
}
