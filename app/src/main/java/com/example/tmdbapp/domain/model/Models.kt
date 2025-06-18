package com.example.tmdbapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    val id: Int,
    val name: String,
    val department: String,
    val imageUrl: String,
    val details: ActorDetails
)

@Serializable
data class ActorDetails(
    val name: String,
    val gender: String,
    val department: String,
    val popularity: Double,
    val imageUrl: String,
    val knowFor: List<ActorMovie>
)

@Serializable
data class ActorMovie(
    val title: String,
    val overview: String,
    val releaseDate: String?,
    val popularity: Double,
    val posterPath: String?
)
