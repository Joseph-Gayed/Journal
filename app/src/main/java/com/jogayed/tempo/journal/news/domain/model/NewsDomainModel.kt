package com.jogayed.tempo.journal.news.domain.model

data class NewsDomainModel(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val sourceId: String = "",
    val sourceName: String = "",
    val sourceUrl: String = "",
    val title: String = "",
    val url: String = "",
    val urlToImage: String = ""
)