package com.example.tmdbapp.domain.usecase

import com.example.tmdbapp.domain.repository.FavoritesRepository
import javax.inject.Inject

class RemoveFavoriteActorUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(actorId: Int) = repository.removeFavorite(actorId)
}