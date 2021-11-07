package com.jogayed.tempo.journal.news.data.local.mapper

import com.jogayed.core.mapper.IMapper
import com.jogayed.tempo.journal.news.data.local.model.NewsLocalDataModel
import com.jogayed.tempo.journal.news.domain.model.NewsDomainModel
import javax.inject.Inject

/**
 * Map from remote data model to domain model
 */
class NewsLocalDataMapper @Inject constructor() :
    IMapper<NewsLocalDataModel, NewsDomainModel> {
    override fun map(inputFormat: NewsLocalDataModel): NewsDomainModel {
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

    override fun reverseMap(inputFormat: NewsDomainModel): NewsLocalDataModel {
        return NewsLocalDataModel(
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

    fun mapList(inputFormat: List<NewsLocalDataModel>): List<NewsDomainModel> {
        return inputFormat.map {
            map(it)
        }
    }

    fun reverseMapList(inputFormat: List<NewsDomainModel>): List<NewsLocalDataModel> {
        return inputFormat.map {
            reverseMap(it)
        }
    }

}