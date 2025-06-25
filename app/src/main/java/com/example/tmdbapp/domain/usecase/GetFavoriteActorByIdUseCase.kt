package com.example.tmdbapp.domain.usecase

import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.repository.FavoritesRepository
import javax.inject.Inject

class GetFavoriteActorByIdUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(id: Int): Actor? = repository.getFavoriteById(id)
}