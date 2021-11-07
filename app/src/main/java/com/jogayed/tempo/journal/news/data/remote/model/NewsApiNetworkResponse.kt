package com.jogayed.tempo.journal.news.data.remote.model

import com.google.gson.annotations.SerializedName

data class NewsApiNetworkResponse(
    @SerializedName("articles") val newsList: List<NewsRemoteDataModel>,
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int
)

