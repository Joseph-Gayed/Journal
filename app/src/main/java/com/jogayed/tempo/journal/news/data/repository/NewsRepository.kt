package com.jogayed.tempo.journal.news.data.repository

import com.jogayed.tempo.journal.news.data.local.datasource.INewsLocalDataSource
import com.jogayed.tempo.journal.news.data.local.mapper.NewsLocalDataMapper
import com.jogayed.tempo.journal.news.data.remote.datasource.INewsRemoteDataSource
import com.jogayed.tempo.journal.news.data.remote.datasource.NewsApi
import com.jogayed.tempo.journal.news.data.remote.mapper.NewsRemoteDataMapper
import com.jogayed.tempo.journal.news.data.remote.model.NewsApiNetworkResponse
import com.jogayed.tempo.journal.news.domain.model.NewsDomainModel
import com.jogayed.tempo.journal.news.domain.model.NewsSearchParams
import com.jogayed.tempo.journal.news.domain.repository.INewsRepository
import com.jogayed.tempo.journal.news.domain.result.NewsResult
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val localDataSource: INewsLocalDataSource,
    private val remoteDataSource: INewsRemoteDataSource,
    private val localDataMapper: NewsLocalDataMapper,
    private val remoteDataMapper: NewsRemoteDataMapper,
) : INewsRepository {
    override suspend fun getLastSearchResult(): NewsResult {
        return try {
            val cachedList = localDataSource.getLastSearchResult()
            if (cachedList.isNotEmpty()) {
                NewsResult.SuccessOfFirstPage(localDataMapper.mapList(cachedList), true)
            } else {
                NewsResult.Empty
            }
        } catch (e: Exception) {
            NewsResult.ErrorOfFirstPage(error = e)
        }
    }

    override suspend fun searchNews(searchParams: NewsSearchParams): NewsResult {
        return try {
            val networkResponse: NewsApiNetworkResponse = remoteDataSource.searchNews(searchParams)
            val isLastPage = (networkResponse.totalResults == 0)

            val newsList = remoteDataMapper.mapList(networkResponse.newsList)
            if (searchParams.page == NewsApi.FIRST_PAGE) {
                if (newsList.isEmpty())
                    NewsResult.Empty
                else {
                    saveSearchResult(newsList)
                    NewsResult.SuccessOfFirstPage(newsList, isLastPage)
                }
            } else
                NewsResult.SuccessOfLoadingMore(newsList, isLastPage)
        } catch (e: Exception) {
            if (searchParams.page == NewsApi.FIRST_PAGE)
                NewsResult.ErrorOfFirstPage(error = e)
            else
                NewsResult.ErrorOfLoadingMore(error = e)
        }
    }

    override suspend fun saveSearchResult(newsList: List<NewsDomainModel>) {
        if (newsList.isNotEmpty()) {
            localDataSource.deleteAll()
            localDataSource.saveSearchResult(localDataMapper.reverseMapList(newsList))
        }

    }
}