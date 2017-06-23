package com.sfiscusdevelopment.sfiscus.booklit.searchGoodReads

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.sfiscusdevelopment.sfiscus.booklit.R
import com.sfiscusdevelopment.sfiscus.booklit.entities.BookWrapper
import com.sfiscusdevelopment.sfiscus.booklit.inflate
import com.sfiscusdevelopment.sfiscus.booklit.loadImage
import com.sfiscusdevelopment.sfiscus.booklit.setTextAndContentDescription
import kotlinx.android.synthetic.main.book_search.view.*

/**
 * Create a class with an id of SearchAdapter. Kotlin programming language is used for this class
 * setting it apart from many of the other classes in this project. Kotlin is designed to run on a
 * JVM while allowing it to be used interchangeably with Java. This class is an adapter for the
 * SearchActivity
 */
class SearchAdapter(private val listener: SearchViewHolder.SearchBookListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val VIEW_TYPE_ITEM = 0
        val VIEW_TYPE_PROGRESS = 1
    }

    private lateinit var context: Context
    var bookWrappers: ArrayList<BookWrapper> = ArrayList()
    var progressBarEnabled = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
//        val searchViewItem = LayoutInflater.from(context).inflate(R.layout.book_search, parent, false)

        if (viewType.equals(VIEW_TYPE_ITEM)) {
            return SearchViewHolder(parent.inflate(R.layout.book_search), listener)
        } else {
            return ProgressViewHolder(parent.inflate(R.layout.search_progress));
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchViewHolder) {
            holder.setBookWrapper(bookWrappers[position])
        }
    }

    override fun getItemCount(): Int = if (progressBarEnabled) bookWrappers.size + 1 else bookWrappers.size

    override fun getItemViewType(position: Int): Int {
        return if (position >= bookWrappers.size) VIEW_TYPE_PROGRESS else VIEW_TYPE_ITEM
    }

    fun addBooks(bookWrapper: ArrayList<BookWrapper>) {
        bookWrappers.addAll(bookWrapper)
        this.notifyDataSetChanged()
    }

    fun clear() {
        bookWrappers.clear()
        this.notifyDataSetChanged()
    }

    fun enableProgressBar() {
        progressBarEnabled = true
    }

    fun disableProgressBar() {
        progressBarEnabled = false
    }

    class SearchViewHolder(itemView: View, private val listener: SearchViewHolder.SearchBookListener) : RecyclerView.ViewHolder(itemView) {

        interface SearchBookListener {
            fun onAddToClickListener(bookWrapper: BookWrapper)
        }

        fun setBookWrapper(item: BookWrapper) {
            val book = item.getBook()

            with(book) {
                itemView.txt_title.setTextAndContentDescription(title)
                itemView.txt_author.setTextAndContentDescription(author.name)
                itemView.img_cover.loadImage(imageUrl)
                itemView.rating_stars.contentDescription = item.averageRating
                itemView.rating_stars.rating = item.averageRating.toFloat()
                itemView.btn_add_to.setOnClickListener { listener.onAddToClickListener(item) }
            }
        }
    }

    class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
