package com.jogayed.tempo.journal.news.presentation.view.news_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jogayed.core.presentation.utils.AdapterStatus
import com.jogayed.core.presentation.utils.BaseRecyclerViewAdapter
import com.jogayed.core.presentation.utils.listen
import com.jogayed.tempo.journal.R
import com.jogayed.tempo.journal.app_core.ext.setImageUrl
import com.jogayed.tempo.journal.news.presentation.model.NewsUiModel

/**
 * [RecyclerView.Adapter] that can display a [NewsUiModel].
 */
class NewsListAdapter(
    private val itemClickListener: (item: NewsUiModel) -> Unit
) : BaseRecyclerViewAdapter<NewsUiModel, RecyclerView.ViewHolder>() {

    @AdapterStatus
    var adapterStatus: Int = AdapterStatus.Normal
        set(value) {
            if (field == value) return
            field = value
            if (value == AdapterStatus.Loading || value == AdapterStatus.Error) {
                notifyItemInserted(items.size - 1)
            } else {
                notifyItemInserted(items.size - 1)
            }
        }

    override fun setData(newItems: List<NewsUiModel>) {
        adapterStatus = AdapterStatus.Normal
        super.setData(newItems)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == items.size) adapterStatus else AdapterStatus.Normal
    }

    override fun getItemCount(): Int {
        return if (adapterStatus == AdapterStatus.Normal) items.size else items.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterStatus.Normal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_news, parent, false)

                NewsViewHolder(view).listen { position, _ ->
                    itemClickListener.invoke(items[position])
                }
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loading, parent, false)

                FooterViewHolder(view)
            }
        }
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            AdapterStatus.Normal -> {
                val item = items[position]
                (viewHolder as NewsViewHolder).bind(item)
            }
        }
    }

    inner class NewsViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val ivNewsImage: ImageView = view.findViewById(R.id.iv_news_image)
        private val tvNewsTitle: TextView = view.findViewById(R.id.tv_news_title)
        private val tvNewsSource: TextView = view.findViewById(R.id.tv_news_source)
        private val tvNewsDescription: TextView = view.findViewById(R.id.tv_news_description)

        fun bind(item: NewsUiModel) {
            tvNewsTitle.text = item.title
            tvNewsSource.text = item.sourceName
            ivNewsImage.setImageUrl(item.urlToImage, R.drawable.image_place_holder)
            tvNewsDescription.text = item.description
        }
    }


    inner class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
    }

}