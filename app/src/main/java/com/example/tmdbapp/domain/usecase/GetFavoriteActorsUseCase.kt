package com.example.tmdbapp.domain.usecase

import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteActorsUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<Actor>> = repository.getFavoriteActors()
}