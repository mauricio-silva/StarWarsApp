package com.example.tmdbapp.domain.repository

import androidx.paging.PagingData
import com.example.tmdbapp.domain.model.Actor
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getCharacters(): Flow<PagingData<Actor>>
}

interface FavoritesRepository {
    suspend fun saveFavorite(actor: Actor)
    fun getFavorites(): Flow<List<Actor>>
    suspend fun removeFavorite(id: Int)
}
