package com.jogayed.tempo.journal.news.data.local.datasource

import com.jogayed.core.ICoroutineDispatchers
import com.jogayed.tempo.journal.news.data.local.model.NewsLocalDataModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsLocalDataSource @Inject constructor(
    private val ratesDao: NewsDao,
    private val coroutineDispatcher: ICoroutineDispatchers
) : INewsLocalDataSource {
    override suspend fun getLastSearchResult(): List<NewsLocalDataModel> {
        return withContext(coroutineDispatcher.io) {
            ratesDao.getLastSearchResult()
        }
    }

    override suspend fun saveSearchResult(rates: List<NewsLocalDataModel>) {
        withContext(coroutineDispatcher.io) {
            ratesDao.saveAll(rates)
        }
    }

    override suspend fun deleteAll() {
        withContext(coroutineDispatcher.io) {
            ratesDao.deleteAll()
        }
    }
}