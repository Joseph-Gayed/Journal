package com.jogayed.tempo.journal.news.domain.action

import com.jogayed.core.Action
import com.jogayed.tempo.journal.news.domain.model.NewsSearchParams

sealed class NewsAction : Action {
    object GetCachedSearchResult : NewsAction()
    data class SearchNews(val searchParams: NewsSearchParams) : NewsAction()
    data class RefreshResults(val searchParams: NewsSearchParams) : NewsAction()
    data class LoadMoreResults(val searchParams: NewsSearchParams) : NewsAction()
}