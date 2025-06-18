package com.example.tmdbapp.data.repository

import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl : FavoritesRepository {

    override suspend fun saveFavorite(actor: Actor) {
        TODO("Not yet implemented")
    }

    override fun getFavorites(): Flow<List<Actor>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFavorite(id: Int) {
        TODO("Not yet implemented")
    }

}