package com.jogayed.tempo.journal.app_core.di

import com.jogayed.core.ICoroutineDispatchers
import com.jogayed.core.MyCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatchersModule {
    @Singleton
    @Provides
    fun providesCoroutineDispatcher(): ICoroutineDispatchers {
        return MyCoroutineDispatchers()
    }
}