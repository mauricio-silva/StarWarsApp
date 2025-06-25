package com.example.tmdbapp.domain.repository

import androidx.paging.PagingData
import com.example.tmdbapp.domain.model.Actor
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getCharacters(): Flow<PagingData<Actor>>
}

interface FavoritesRepository {
    fun getFavoriteActors(): Flow<List<Actor>>
    suspend fun saveFavorite(actor: Actor)
    suspend fun removeFavorite(actorId: Int)
    suspend fun getFavoriteById(id: Int): Actor?
}
