package com.jogayed.tempo.journal.news.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsUiModel(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val publishedDateFormatted: String = "",
    val sourceId: String = "",
    val sourceName: String = "",
    val sourceUrl: String = "",
    val title: String = "",
    val url: String = "",
    val urlToImage: String = ""
) : Parcelable