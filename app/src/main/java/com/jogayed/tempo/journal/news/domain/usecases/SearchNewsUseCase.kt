package com.jogayed.tempo.journal.news.domain.usecases

import com.jogayed.core.domin.ISuspendableUseCase
import com.jogayed.tempo.journal.news.domain.model.NewsSearchParams
import com.jogayed.tempo.journal.news.domain.repository.INewsRepository
import com.jogayed.tempo.journal.news.domain.result.NewsResult
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(private val repository: INewsRepository) :
    ISuspendableUseCase.WithParams<NewsSearchParams, NewsResult> {
    override suspend fun invoke(input: NewsSearchParams): NewsResult {
        return repository.searchNews(input)
    }
}
