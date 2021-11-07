package com.jogayed.tempo.journal.news.data.remote.model

import com.google.gson.annotations.SerializedName

data class NewsRemoteDataModel(
    @SerializedName("author") val author: String? = "",
    @SerializedName("content") val content: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("publishedAt") val publishedAt: String? = "",
    @SerializedName("source") val source: Source? = null,
    @SerializedName("title") val title: String? = "",
    @SerializedName("url") val url: String? = "",
    @SerializedName("urlToImage") val urlToImage: String? = ""
)