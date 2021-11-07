package com.jogayed.tempo.journal.news.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.jogayed.core.presentation.viewmodel.MVIBaseViewModel
import com.jogayed.tempo.journal.news.domain.action.NewsAction
import com.jogayed.tempo.journal.news.domain.result.NewsResult
import com.jogayed.tempo.journal.news.domain.usecases.GetLastSearchResultUseCase
import com.jogayed.tempo.journal.news.domain.usecases.SearchNewsUseCase
import com.jogayed.tempo.journal.news.domain.viewstate.NewsDomainViewState
import com.jogayed.tempo.journal.news.presentation.mapper.NewsUiViewStateMapper
import com.jogayed.tempo.journal.news.presentation.viewstate.NewsUiViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getLastSearchResultUseCase: GetLastSearchResultUseCase,
    private val searchNewsUseCase: SearchNewsUseCase,
    private val viewStateUiMapper: NewsUiViewStateMapper
) : MVIBaseViewModel<NewsAction, NewsResult, NewsDomainViewState>() {
    override val defaultInternalViewState = NewsDomainViewState()

    private val _newsListViewStates =
        MutableStateFlow(NewsUiViewState())
    val newsListViewStates: StateFlow<NewsUiViewState> = _newsListViewStates

    var keyword: String = ""

    init {
        executeAction(NewsAction.GetCachedSearchResult)
        observeParentViewState()
    }

    private fun observeParentViewState() {
        viewModelScope.launch {
            viewStates.collect { domainViewState: NewsDomainViewState ->
                _newsListViewStates.emit(viewStateUiMapper.map(domainViewState))
            }
        }
    }


    override fun handleAction(action: NewsAction): Flow<NewsResult> {
        return flow {
            when (action) {
                NewsAction.GetCachedSearchResult -> {
                    emit(getLastSearchResultUseCase())
                }
                is NewsAction.SearchNews -> {
                    keyword = action.searchParams.keyword
                    emit(NewsResult.LoadingFirstPage)
                    emit(searchNewsUseCase(action.searchParams))
                }
                is NewsAction.RefreshResults -> {
                    emit(NewsResult.RefreshResult)
                    delay(500) // to swipe refresh effect
                    emit(searchNewsUseCase(action.searchParams))
                }
                is NewsAction.LoadMoreResults -> {
                    keyword = action.searchParams.keyword
                    emit(NewsResult.LoadingMore)
                    delay(1000) // to show the footer progress
                    emit(searchNewsUseCase(action.searchParams))
                }
            }
        }
    }


    fun isLoadMoreDisabled(): Boolean {
        return newsListViewStates.value.isLoadingMoreDisabled
    }

}