package com.jogayed.tempo.journal.news.data.remote.datasource

import com.jogayed.core.ICoroutineDispatchers
import com.jogayed.tempo.journal.news.data.remote.model.NewsApiNetworkResponse
import com.jogayed.tempo.journal.news.domain.model.NewsSearchParams
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val newsApi: NewsApi,
    private val coroutineDispatcher: ICoroutineDispatchers
) : INewsRemoteDataSource {

    override suspend fun searchNews(searchParams: NewsSearchParams): NewsApiNetworkResponse {
        return withContext(coroutineDispatcher.io) {
            newsApi.searchNews(
                keyword = searchParams.keyword,
                page = searchParams.page,
                pageSize = searchParams.pageSize
            )
        }
    }
}