package com.example.tmdbapp.domain.module

import com.example.tmdbapp.domain.repository.FavoritesRepository
import com.example.tmdbapp.domain.repository.HomeRepository
import com.example.tmdbapp.domain.usecase.GetFavoriteActorByIdUseCase
import com.example.tmdbapp.domain.usecase.GetFavoriteActorsUseCase
import com.example.tmdbapp.domain.usecase.GetHomeCharactersUseCase
import com.example.tmdbapp.domain.usecase.RemoveFavoriteActorUseCase
import com.example.tmdbapp.domain.usecase.SaveFavoriteActorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun provideGetHomeCharactersUseCase(
        repository: HomeRepository
    ) = GetHomeCharactersUseCase(repository)

    @Provides
    fun provideGetFavoriteActorsUseCase(
        repository: FavoritesRepository
    ) = GetFavoriteActorsUseCase(repository)

    @Provides
    fun provideSaveFavoriteActorUseCase(
        repository: FavoritesRepository
    ) = SaveFavoriteActorUseCase(repository)

    @Provides
    fun provideRemoveFavoriteActorUseCase(
        repository: FavoritesRepository
    ) = RemoveFavoriteActorUseCase(repository)

    @Provides
    fun provideGetFavoriteActorByIdUseCase(
        repository: FavoritesRepository
    ) = GetFavoriteActorByIdUseCase(repository)
}
