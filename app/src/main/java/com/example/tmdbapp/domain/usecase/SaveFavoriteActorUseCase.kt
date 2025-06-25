package com.example.tmdbapp.domain.usecase

import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.repository.FavoritesRepository
import javax.inject.Inject

class SaveFavoriteActorUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(actor: Actor) = repository.saveFavorite(actor)
}