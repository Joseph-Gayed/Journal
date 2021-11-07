package com.jogayed.tempo.journal.news.data.remote.model

import com.google.gson.annotations.SerializedName


data class Source(
    @SerializedName("id") val id: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("url") val url: String? = ""
)