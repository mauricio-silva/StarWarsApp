package com.example.tmdbapp.data.repository

import com.example.tmdbapp.data.local.datasource.LocalDataSource
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl(
    private val localDataSource: LocalDataSource
) : FavoritesRepository {

    override fun getFavoriteActors(): Flow<List<Actor>> = localDataSource.getFavoriteActors()

    override suspend fun saveFavorite(actor: Actor) = localDataSource.saveFavorite(actor)

    override suspend fun removeFavorite(actorId: Int) = localDataSource.removeFavorite(actorId)

    override suspend fun getFavoriteById(id: Int): Actor? = localDataSource.getFavoriteById(id)
}
