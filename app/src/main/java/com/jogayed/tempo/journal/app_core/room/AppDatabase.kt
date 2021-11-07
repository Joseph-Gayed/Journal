package com.jogayed.tempo.journal.app_core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jogayed.tempo.journal.app_core.room.AppDatabase.Companion.DATABASE_VERSION
import com.jogayed.tempo.journal.news.data.local.datasource.NewsDao
import com.jogayed.tempo.journal.news.data.local.model.NewsLocalDataModel


@Database(entities = [NewsLocalDataModel::class], version = DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "tempo_news"
        const val DATABASE_VERSION = 1
    }

    abstract fun newsDao(): NewsDao
}