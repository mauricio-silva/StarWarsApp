package com.example.tmdbapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.tmdbapp.data.local.entity.ActorEntity
import com.example.tmdbapp.data.local.entity.ActorEntityWithMovies
import com.example.tmdbapp.data.local.entity.ActorMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor: ActorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<ActorMovieEntity>)

    @Transaction
    suspend fun insertFavorite(actor: ActorEntity, movies: List<ActorMovieEntity>) {
        insertActor(actor)
        insertMovies(movies)
    }

    @Transaction
    @Query("SELECT * FROM actors")
    fun getAll(): Flow<List<ActorEntityWithMovies>>

    @Query("SELECT EXISTS(SELECT 1 FROM actors WHERE id = :id) AS isFavorite")
    fun isFavorite(id: Int): Flow<Boolean>

    @Query("DELETE FROM actors WHERE id = :id")
    suspend fun deleteActor(id: Int)

    @Query("DELETE FROM actor_movies WHERE actorId = :actorId")
    suspend fun deleteMovies(actorId: Int)

    @Transaction
    suspend fun deleteFavorite(actorId: Int) {
        deleteActor(actorId)
        deleteMovies(actorId)
    }

    @Transaction
    @Query("SELECT * FROM actors WHERE id = :id")
    suspend fun getActorWithMovies(id: Int): ActorEntityWithMovies?
}