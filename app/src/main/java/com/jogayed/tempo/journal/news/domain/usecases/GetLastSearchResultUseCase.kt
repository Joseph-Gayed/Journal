package com.jogayed.tempo.journal.news.domain.usecases

import com.jogayed.core.domin.ISuspendableUseCase
import com.jogayed.tempo.journal.news.domain.repository.INewsRepository
import com.jogayed.tempo.journal.news.domain.result.NewsResult
import javax.inject.Inject

class GetLastSearchResultUseCase @Inject constructor(private val repository: INewsRepository) :
    ISuspendableUseCase.WithoutParams<NewsResult> {
    override suspend fun invoke(): NewsResult {
        return repository.getLastSearchResult()
    }
}