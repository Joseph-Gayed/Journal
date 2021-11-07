package com.jogayed.tempo.journal.news.data.remote.datasource

import com.jogayed.tempo.journal.news.data.remote.model.NewsApiNetworkResponse
import com.jogayed.tempo.journal.news.domain.model.NewsSearchParams

interface INewsRemoteDataSource {
    suspend fun searchNews(searchParams: NewsSearchParams): NewsApiNetworkResponse
}