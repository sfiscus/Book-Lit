package com.sfiscusdevelopment.sfiscus.booklit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

/**
 * Create a function for the TextView, ImageView, and ViewGroup. Kotlin programming language is used for this class
 * setting it apart from many of the other classes in this project. Kotlin is designed to run on a
 * JVM while allowing it to be used interchangeably with Java.
 */
fun TextView.setTextAndContentDescription(text: String){
    this.text = text
    this.contentDescription = text
}

fun ImageView.loadImage(url: String){
    Picasso.with(this.context).load(url).fit().into(this)
}

fun ViewGroup.inflate(resource: Int): View {
    return LayoutInflater.from(this.context).inflate(resource, this, false)
}
