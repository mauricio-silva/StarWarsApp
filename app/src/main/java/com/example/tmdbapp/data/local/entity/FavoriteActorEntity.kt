package com.example.tmdbapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "actors")
data class ActorEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val department: String,
    val imageUrl: String,
    val gender: String
)

@Serializable
@Entity(
    tableName = "actor_movies",
    primaryKeys = ["actorId", "title"],
    foreignKeys = [
        ForeignKey(
            entity = ActorEntity::class,
            parentColumns = ["id"],
            childColumns = ["actorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("actorId")]
)
data class ActorMovieEntity(
    val actorId: Int,
    val title: String,
    val overview: String,
    val releaseDate: String?,
    val posterPath: String?
)

@kotlinx.serialization.Serializable
data class ActorEntityWithMovies(
    @Embedded val actor: ActorEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "actorId"
    )
    val movies: List<ActorMovieEntity>
)
