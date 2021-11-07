package com.jogayed.tempo.journal.news.domain.repository

import com.jogayed.tempo.journal.news.domain.model.NewsDomainModel
import com.jogayed.tempo.journal.news.domain.model.NewsSearchParams
import com.jogayed.tempo.journal.news.domain.result.NewsResult

interface INewsRepository {
    suspend fun getLastSearchResult(): NewsResult
    suspend fun searchNews(searchParams: NewsSearchParams): NewsResult
    suspend fun saveSearchResult(newsList: List<NewsDomainModel>)
}