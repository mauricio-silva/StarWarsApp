package com.example.tmdbapp.data.local.datasource

import com.example.tmdbapp.data.local.dao.ActorDao
import com.example.tmdbapp.data.local.entity.ActorMovieEntity
import com.example.tmdbapp.data.mapper.toActor
import com.example.tmdbapp.data.mapper.toActorMovie
import com.example.tmdbapp.data.mapper.toActorEntity
import com.example.tmdbapp.data.mapper.toActorMovieEntity
import com.example.tmdbapp.domain.model.Actor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val actorDao: ActorDao
) {
    fun getFavoriteActors(): Flow<List<Actor>> =
        actorDao.getAll().map { list ->
            list.map {
                it.actor.toActor(
                    movies = it.movies.map(ActorMovieEntity::toActorMovie),
                    isFavorite = true
                )
            }
        }

    suspend fun isFavorite(id: Int): Boolean = actorDao.isFavorite(id).first()

    suspend fun saveFavorite(actor: Actor) {
        actorDao.insertFavorite(
            actor.toActorEntity(),
            actor.details.knowFor.map { it.toActorMovieEntity(actor.id) }
        )
    }

    suspend fun removeFavorite(actorId: Int) {
        actorDao.deleteFavorite(actorId)
    }

    suspend fun getFavoriteById(id: Int): Actor? {
        return actorDao.getActorWithMovies(id)?.let {
            it.actor.toActor(
                movies = it.movies.map(ActorMovieEntity::toActorMovie),
                isFavorite = true
            )
        }
    }
}