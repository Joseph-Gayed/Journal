package com.jogayed.tempo.journal.news.data.local.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jogayed.tempo.journal.news.data.local.model.NewsLocalDataModel

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(rates: List<NewsLocalDataModel>)

    @Query("Delete from News")
    suspend fun deleteAll()

    @Query("select * from News")
    suspend fun getLastSearchResult(): List<NewsLocalDataModel>
}