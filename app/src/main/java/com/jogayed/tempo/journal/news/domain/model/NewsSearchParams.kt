package com.jogayed.tempo.journal.news.domain.model

import com.jogayed.tempo.journal.news.data.remote.datasource.NewsApi

data class NewsSearchParams(
    val keyword: String,
    val page: Int = NewsApi.FIRST_PAGE,
    val pageSize: Int = NewsApi.PAGE_SIZE,
)
