package com.jogayed.tempo.journal.news.di

import com.jogayed.core.ICoroutineDispatchers
import com.jogayed.tempo.journal.app_core.room.AppDatabase
import com.jogayed.tempo.journal.news.data.local.datasource.INewsLocalDataSource
import com.jogayed.tempo.journal.news.data.local.datasource.NewsDao
import com.jogayed.tempo.journal.news.data.local.datasource.NewsLocalDataSource
import com.jogayed.tempo.journal.news.data.local.mapper.NewsLocalDataMapper
import com.jogayed.tempo.journal.news.data.remote.datasource.INewsRemoteDataSource
import com.jogayed.tempo.journal.news.data.remote.datasource.NewsApi
import com.jogayed.tempo.journal.news.data.remote.datasource.NewsRemoteDataSource
import com.jogayed.tempo.journal.news.data.remote.mapper.NewsRemoteDataMapper
import com.jogayed.tempo.journal.news.data.repository.NewsRepository
import com.jogayed.tempo.journal.news.domain.repository.INewsRepository
import com.jogayed.tempo.journal.news.domain.usecases.GetLastSearchResultUseCase
import com.jogayed.tempo.journal.news.domain.usecases.SearchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {
    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun providesDao(appDatabase: AppDatabase): NewsDao {
        return appDatabase.newsDao()
    }


    @Singleton
    @Provides
    fun providesRemoteDataSource(
        api: NewsApi,
        coroutineDispatcher: ICoroutineDispatchers
    ): INewsRemoteDataSource {
        return NewsRemoteDataSource(api, coroutineDispatcher)
    }

    @Singleton
    @Provides
    fun providesLocalDataSource(
        dao: NewsDao,
        coroutineDispatcher: ICoroutineDispatchers
    ): INewsLocalDataSource {
        return NewsLocalDataSource(dao, coroutineDispatcher)
    }

    @Singleton
    @Provides
    fun providesRepository(
        localDs: INewsLocalDataSource,
        remoteDs: INewsRemoteDataSource,
        localDataMapper: NewsLocalDataMapper,
        remoteDataMapper: NewsRemoteDataMapper
    ): INewsRepository {
        return NewsRepository(localDs, remoteDs, localDataMapper, remoteDataMapper)
    }

    @Singleton
    @Provides
    fun providesGetLastSearchResultUseCase(
        repository: INewsRepository
    ): GetLastSearchResultUseCase {
        return GetLastSearchResultUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetSearchNewsUseCase(
        repository: INewsRepository
    ): SearchNewsUseCase {
        return SearchNewsUseCase(repository)
    }

}