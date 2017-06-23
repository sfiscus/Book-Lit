package com.sfiscusdevelopment.sfiscus.booklit.customViews

import android.text.Editable
import android.text.TextWatcher

/**
 * Create a class with an id of BookTextWatcher. Kotlin programming language is used for this class
 * setting it apart from many of the other classes in this project. Kotlin is designed to run on a
 * JVM while allowing it to be used interchangeably with Java. This class is contains code for
 * changing of text
 */
class BookTextWatcher(val listener: (String) -> Unit) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        listener.invoke(s.toString())
    }

    fun onTextChangedListener(listener: (String) -> Unit) {}

}
