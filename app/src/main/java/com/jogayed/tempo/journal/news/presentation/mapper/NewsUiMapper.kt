package com.jogayed.tempo.journal.news.presentation.mapper

import com.jogayed.core.mapper.IMapper
import com.jogayed.tempo.journal.app_core.AppConsts
import com.jogayed.tempo.journal.news.domain.model.NewsDomainModel
import com.jogayed.tempo.journal.news.presentation.model.NewsUiModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Map from remote domain model to Presentation model
 */
class NewsUiMapper @Inject constructor() :
    IMapper<NewsDomainModel, NewsUiModel> {
    override fun map(inputFormat: NewsDomainModel): NewsUiModel {
        return NewsUiModel(
            author = inputFormat.author,
            content = inputFormat.content,
            description = inputFormat.description,
            publishedAt = inputFormat.publishedAt,
            publishedDateFormatted = getFormattedDate(inputFormat.publishedAt),
            sourceId = inputFormat.sourceId,
            sourceName = inputFormat.sourceName,
            sourceUrl = inputFormat.sourceUrl,
            title = inputFormat.title,
            url = inputFormat.url,
            urlToImage = inputFormat.urlToImage
        )
    }

    private fun getFormattedDate(publishedAt: String): String {
        val inputFormatter = SimpleDateFormat(AppConsts.SERVER_DATE_PATTERN, Locale.getDefault())
        val outputFormatter = SimpleDateFormat(AppConsts.APP_DATE_PATTERN, Locale.getDefault())
        val date = inputFormatter.parse(publishedAt)
        return date?.let { outputFormatter.format(it) } ?: publishedAt
    }

    override fun reverseMap(inputFormat: NewsUiModel): NewsDomainModel {
        return NewsDomainModel(
            author = inputFormat.author,
            content = inputFormat.content,
            description = inputFormat.description,
            publishedAt = inputFormat.publishedAt,
            sourceId = inputFormat.sourceId,
            sourceName = inputFormat.sourceName,
            sourceUrl = inputFormat.sourceUrl,
            title = inputFormat.title,
            url = inputFormat.url,
            urlToImage = inputFormat.urlToImage
        )
    }

    fun mapList(inputFormat: List<NewsDomainModel>): List<NewsUiModel> {
        return inputFormat.map {
            map(it)
        }
    }

    fun reverseMapList(inputFormat: List<NewsUiModel>): List<NewsDomainModel> {
        return inputFormat.map {
            reverseMap(it)
        }
    }


}