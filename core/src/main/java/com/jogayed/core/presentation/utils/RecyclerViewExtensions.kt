package com.jogayed.core.presentation.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Implementation of the [RecyclerView.OnScrollListener]
 * Used to define the infinite scrolling behavior for RecyclerView
 * @property isLoadMoreDisabled higher order function to check if the load more is enabled or not
 * @property loadMore higher order function to load more with [currentPage]
 *
 * @param recyclerView
 */
class RecyclerViewPaginator(
    recyclerView: RecyclerView,
    val isLoadMoreDisabled: () -> Boolean = { false },
    val loadMore: (Int) -> Unit
) : RecyclerView.OnScrollListener() {
    //if there is at least 1 item in the list the load more will be called
    var threshold = 1
    var currentPage = 1

    init {
        recyclerView.addOnScrollListener(this)
    }

    fun resetCurrentPage() {
        currentPage = 1
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (isLoadMoreDisabled()) {
            val layoutManager = recyclerView.layoutManager
            layoutManager?.run {
                val visibleItemCount = childCount
                val totalItemCount = itemCount
                val positionOfFirstVisibleItem = when (this) {
                    is LinearLayoutManager -> findLastVisibleItemPosition()
                    is GridLayoutManager -> findLastVisibleItemPosition()
                    else -> return
                }
                if (visibleItemCount + positionOfFirstVisibleItem + threshold >= totalItemCount)
                    loadMore(++currentPage)
            }
        }
    }

}

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}