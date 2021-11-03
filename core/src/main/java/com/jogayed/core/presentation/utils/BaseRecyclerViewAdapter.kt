package com.jogayed.core.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerViewAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    var items: List<T> = listOf()
    var pagedItems: ArrayList<T>? = ArrayList()
    var selectedPos = -1

    lateinit var diffUtil: BaseDiffUtil<T>
    lateinit var onItemClickListener: OnItemClickListener<T>

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(newItems: List<T>) {
        if (::diffUtil.isInitialized)
            setDataWithDiffUtil(newItems)
        else {
            items = newItems
            notifyDataSetChanged()
        }
    }

    private fun setDataWithDiffUtil(newItems: List<T>) {
        diffUtil.oldList = items
        diffUtil.newList = newItems
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener<T> {
        fun onItemClick(pos: Int, item: T)
    }
}

abstract class BaseDiffUtil<T> : DiffUtil.Callback() {
    lateinit var oldList: List<T>
    lateinit var newList: List<T>

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}