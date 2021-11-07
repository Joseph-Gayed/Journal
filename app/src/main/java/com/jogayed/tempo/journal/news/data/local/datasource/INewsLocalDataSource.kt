package com.jogayed.tempo.journal.news.data.local.datasource

import com.jogayed.tempo.journal.news.data.local.model.NewsLocalDataModel

interface INewsLocalDataSource {
    suspend fun getLastSearchResult(): List<NewsLocalDataModel>
    suspend fun saveSearchResult(rates: List<NewsLocalDataModel>)
    suspend fun deleteAll()
}