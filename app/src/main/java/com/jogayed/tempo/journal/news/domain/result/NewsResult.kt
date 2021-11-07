package com.jogayed.tempo.journal.news.domain.result

import com.jogayed.core.Result
import com.jogayed.tempo.journal.news.domain.model.NewsDomainModel
import com.jogayed.tempo.journal.news.domain.viewstate.NewsDomainViewState

sealed class NewsResult : Result<NewsDomainViewState> {
    object Empty : NewsResult() {
        override fun reduce(
            defaultState: NewsDomainViewState,
            oldState: NewsDomainViewState
        ): NewsDomainViewState {
            return defaultState.copy(isEmpty = true)
        }
    }


    /******** Results of loading First Page ********/

    object LoadingFirstPage : NewsResult() {
        override fun reduce(
            defaultState: NewsDomainViewState,
            oldState: NewsDomainViewState
        ): NewsDomainViewState {
            return defaultState.copy(isLoading = true)
        }
    }

    object RefreshResult : NewsResult() {
        override fun reduce(
            defaultState: NewsDomainViewState,
            oldState: NewsDomainViewState
        ): NewsDomainViewState {
            return defaultState.copy(isRefreshing = true)
        }
    }

    data class SuccessOfFirstPage(val data: List<NewsDomainModel>, val isLastPage: Boolean) :
        NewsResult() {
        override fun reduce(
            defaultState: NewsDomainViewState,
            oldState: NewsDomainViewState
        ): NewsDomainViewState {
            return defaultState.copy(data = data, isLastPage = isLastPage)
        }
    }

    data class ErrorOfFirstPage(val error: Throwable) : NewsResult() {
        override fun reduce(
            defaultState: NewsDomainViewState,
            oldState: NewsDomainViewState
        ): NewsDomainViewState {
            return defaultState.copy(error = error)
        }
    }


    /******** Results of loading More ********/

    object LoadingMore : NewsResult() {
        override fun reduce(
            defaultState: NewsDomainViewState,
            oldState: NewsDomainViewState
        ): NewsDomainViewState {
            return oldState.copy(isLoadingMore = true, loadingMoreError = null)
        }
    }

    data class SuccessOfLoadingMore(val data: List<NewsDomainModel>, val isLastPage: Boolean) :
        NewsResult() {
        override fun reduce(
            defaultState: NewsDomainViewState,
            oldState: NewsDomainViewState
        ): NewsDomainViewState {
            return defaultState.copy(data = oldState.data + data, isLastPage = isLastPage)
        }
    }

    data class ErrorOfLoadingMore(val error: Throwable) : NewsResult() {
        override fun reduce(
            defaultState: NewsDomainViewState,
            oldState: NewsDomainViewState
        ): NewsDomainViewState {
            return oldState.copy(isLoadingMore = true, loadingMoreError = error)
        }
    }

}
