package com.jogayed.tempo.journal.news.presentation.view.news_details

import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jogayed.core.presentation.view.BaseFragment
import com.jogayed.tempo.journal.R
import com.jogayed.tempo.journal.app_core.ext.setImageUrl
import com.jogayed.tempo.journal.news.presentation.model.NewsUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * A fragment representing a list of Items.
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NewsDetailsFragment : BaseFragment() {

    private lateinit var ivNewsImage: ImageView
    private lateinit var tvNewsAuthor: TextView
    private lateinit var tvNewsDate: TextView
    private lateinit var fabOpenSource: FloatingActionButton
    private lateinit var tvNewsTitle: TextView
    private lateinit var tvNewsSource: TextView
    private lateinit var tvNewsContent: TextView


    private val navArgs: NewsDetailsFragmentArgs by navArgs()
    private val newsDetailsItem: NewsUiModel? by lazy {
        navArgs.clickedNewsItem
    }

    override fun getLayoutResId() = R.layout.fragment_news_details

    override fun init() {
        super.init()
        initUi()
        bindUi()
    }

    private fun initUi() {
        ivNewsImage = requireView().findViewById(R.id.iv_news_image)
        tvNewsAuthor = requireView().findViewById(R.id.tv_news_author)
        tvNewsDate = requireView().findViewById(R.id.tv_news_date)
        fabOpenSource = requireView().findViewById(R.id.fab_visit_source)
        tvNewsTitle = requireView().findViewById(R.id.tv_news_title)
        tvNewsSource = requireView().findViewById(R.id.tv_news_source)
        tvNewsContent = requireView().findViewById(R.id.tv_news_content)

        fabOpenSource.setOnClickListener {
            newsDetailsItem?.url?.let { sourceUrl ->
                if (sourceUrl.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(sourceUrl)
                    val title = resources.getString(R.string.chooser_title)
                    val chooser = Intent.createChooser(intent, title)
                    try {
                        startActivity(chooser)
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    private fun bindUi() {
        newsDetailsItem?.run {
            setScreenTitle(title)
            ivNewsImage.setImageUrl(urlToImage, R.drawable.image_place_holder)

            tvNewsAuthor.text = String.format(getString(R.string.author_format), author)
            tvNewsAuthor.isVisible = author.isNotEmpty()

            tvNewsDate.text = publishedDateFormatted
            tvNewsDate.isVisible = publishedDateFormatted.isNotEmpty()

            tvNewsTitle.text = title
            tvNewsTitle.isVisible = title.isNotEmpty()

            tvNewsSource.text = sourceName
            tvNewsSource.isVisible = sourceName.isNotEmpty()

            tvNewsContent.text = content
            tvNewsContent.isVisible = content.isNotEmpty()
        }
    }
}