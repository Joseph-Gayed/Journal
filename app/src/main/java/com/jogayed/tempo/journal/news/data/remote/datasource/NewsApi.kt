package com.jogayed.tempo.journal.news.data.remote.datasource

import com.jogayed.tempo.journal.BuildConfig
import com.jogayed.tempo.journal.app_core.AppConsts
import com.jogayed.tempo.journal.news.data.remote.model.NewsApiNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/everything")
    suspend fun searchNews(
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY,
        @Query("q") keyword: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = PAGE_SIZE,
        /*for future usages*/
        @Query("sortBy") sortBy: String? = null,
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null
    ): NewsApiNetworkResponse


    companion object {
        const val FIRST_PAGE: Int = AppConsts.DEFAULT_FIRST_PAGE
        const val PAGE_SIZE: Int = AppConsts.DEFAULT_PAGE_SIZE
    }

}