package com.example.tmdbapp.domain.module

import com.example.tmdbapp.domain.repository.HomeRepository
import com.example.tmdbapp.domain.usecase.GetHomeCharactersUseCase
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
}
