package com.example.tmdbapp.data.module

import com.example.tmdbapp.data.api.TMDbApi
import com.example.tmdbapp.data.repository.HomeRepositoryImpl
import com.example.tmdbapp.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(
        api: TMDbApi,
        dispatcherIO: CoroutineDispatcher
    ): HomeRepository = HomeRepositoryImpl(api, dispatcherIO)

}
