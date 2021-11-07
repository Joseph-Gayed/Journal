package com.jogayed.tempo.journal.news.data.remote.mapper

import com.jogayed.core.mapper.IMapper
import com.jogayed.tempo.journal.news.data.remote.model.NewsRemoteDataModel
import com.jogayed.tempo.journal.news.data.remote.model.Source
import com.jogayed.tempo.journal.news.domain.model.NewsDomainModel
import javax.inject.Inject

/**
 * Map from remote data model to domain model
 */
class NewsRemoteDataMapper @Inject constructor() :
    IMapper<NewsRemoteDataModel, NewsDomainModel> {
    override fun map(inputFormat: NewsRemoteDataModel): NewsDomainModel {
        return NewsDomainModel(
            author = inputFormat.author ?: "",
            content = inputFormat.content ?: "",
            description = inputFormat.description ?: "",
            publishedAt = inputFormat.publishedAt ?: "",
            sourceId = inputFormat.source?.id ?: "",
            sourceName = inputFormat.source?.name ?: "",
            sourceUrl = inputFormat.source?.url ?: "",
            title = inputFormat.title ?: "",
            url = inputFormat.url ?: "",
            urlToImage = inputFormat.urlToImage ?: ""
        )
    }

    override fun reverseMap(inputFormat: NewsDomainModel): NewsRemoteDataModel {
        return NewsRemoteDataModel(
            author = inputFormat.author,
            content = inputFormat.content,
            description = inputFormat.description,
            publishedAt = inputFormat.publishedAt,
            source = Source(
                id = inputFormat.sourceId,
                name = inputFormat.sourceName,
                url = inputFormat.sourceUrl
            ),
            title = inputFormat.title,
            url = inputFormat.url,
            urlToImage = inputFormat.urlToImage
        )
    }

    fun mapList(inputFormat: List<NewsRemoteDataModel>): List<NewsDomainModel> {
        return inputFormat.map {
            map(it)
        }
    }

    fun reverseMapList(inputFormat: List<NewsDomainModel>): List<NewsRemoteDataModel> {
        return inputFormat.map {
            reverseMap(it)
        }
    }

}