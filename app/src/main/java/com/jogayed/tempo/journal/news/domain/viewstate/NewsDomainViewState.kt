package com.jogayed.tempo.journal.news.domain.viewstate

import com.jogayed.core.ViewState
import com.jogayed.tempo.journal.news.domain.model.NewsDomainModel

data class NewsDomainViewState(
    val isIdle: Boolean = false,
    val isEmpty: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: Throwable? = null,
    val loadingMoreError: Throwable? = null,
    val data: List<NewsDomainModel> = emptyList(),
    val isLastPage: Boolean = false
) : ViewState {
    fun isLoadMoreDisabled(): Boolean {
        return isLastPage || isLoading || isRefreshing || isLoadingMore || error != null || loadingMoreError != null
    }
}
