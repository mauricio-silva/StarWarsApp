package com.example.tmdbapp.data.module

import com.example.tmdbapp.data.local.datasource.LocalDataSource
import com.example.tmdbapp.data.remote.api.TMDbApi
import com.example.tmdbapp.data.repository.FavoritesRepositoryImpl
import com.example.tmdbapp.data.repository.HomeRepositoryImpl
import com.example.tmdbapp.domain.repository.FavoritesRepository
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
        localDataSource: LocalDataSource,
        dispatcherIO: CoroutineDispatcher
    ): HomeRepository = HomeRepositoryImpl(api,localDataSource, dispatcherIO)

    @Provides
    @Singleton
    fun provideFavoriteActorsRepository(
        localDataSource: LocalDataSource
    ): FavoritesRepository = FavoritesRepositoryImpl(localDataSource)
}
