package com.example.startwarsapp.data.repository

import com.example.startwarsapp.domain.model.Character
import com.example.startwarsapp.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl : FavoritesRepository {

    override suspend fun saveFavorite(character: Character) {
        TODO("Not yet implemented")
    }

    override fun getFavorites(): Flow<List<Character>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFavorite(id: Int) {
        TODO("Not yet implemented")
    }
}