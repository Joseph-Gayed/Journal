package com.jogayed.tempo.journal.news.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News")
data class NewsLocalDataModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "author")
    val author: String = "",
    @ColumnInfo(name = "content")
    val content: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String = "",
    @ColumnInfo(name = "sourceId")
    val sourceId: String = "",
    @ColumnInfo(name = "sourceName")
    val sourceName: String = "",
    @ColumnInfo(name = "sourceUrl")
    val sourceUrl: String = "",
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "url")
    val url: String = "",
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String = ""
)