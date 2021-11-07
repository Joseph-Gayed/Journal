package com.jogayed.tempo.journal.news.presentation.viewstate

import com.jogayed.tempo.journal.news.presentation.model.NewsUiModel

data class NewsUiViewState(
    val isIdle: Boolean = false,
    val isEmpty: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: Throwable? = null,
    val loadingMoreError: Throwable? = null,
    val data: List<NewsUiModel> = emptyList(),
    val isLastPage: Boolean = false,
    val isLoadingMoreDisabled: Boolean = false
)