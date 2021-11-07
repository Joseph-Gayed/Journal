package com.jogayed.tempo.journal.news.presentation.mapper

import com.jogayed.core.mapper.IMapper
import com.jogayed.tempo.journal.news.domain.viewstate.NewsDomainViewState
import com.jogayed.tempo.journal.news.presentation.viewstate.NewsUiViewState
import javax.inject.Inject

/**
 * Map from remote domain model to Presentation model
 */
class NewsUiViewStateMapper @Inject constructor(
    private val uiMapper: NewsUiMapper
) :
    IMapper<NewsDomainViewState, NewsUiViewState> {
    override fun map(inputFormat: NewsDomainViewState): NewsUiViewState {
        return NewsUiViewState(
            isIdle = inputFormat.isIdle,
            isEmpty = inputFormat.isEmpty,
            isRefreshing = inputFormat.isRefreshing,
            isLoading = inputFormat.isLoading,
            isLoadingMore = inputFormat.isLoadingMore,
            error = inputFormat.error,
            loadingMoreError = inputFormat.loadingMoreError,
            data = uiMapper.mapList(inputFormat.data),
            isLastPage = inputFormat.isLastPage,
            isLoadingMoreDisabled = inputFormat.isLoadMoreDisabled()
        )
    }

    override fun reverseMap(inputFormat: NewsUiViewState): NewsDomainViewState {
        return NewsDomainViewState(
            isIdle = inputFormat.isIdle,
            isEmpty = inputFormat.isEmpty,
            isRefreshing = inputFormat.isRefreshing,
            isLoading = inputFormat.isLoading,
            isLoadingMore = inputFormat.isLoadingMore,
            error = inputFormat.error,
            loadingMoreError = inputFormat.loadingMoreError,
            data = uiMapper.reverseMapList(inputFormat.data),
            isLastPage = inputFormat.isLastPage
        )
    }

}