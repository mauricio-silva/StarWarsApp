package com.example.tmdbapp.data.mapper

import com.example.tmdbapp.core.Constants
import com.example.tmdbapp.data.local.entity.ActorEntity
import com.example.tmdbapp.data.local.entity.ActorMovieEntity
import com.example.tmdbapp.data.remote.dto.ActorDetailsDto
import com.example.tmdbapp.data.remote.dto.ActorDto
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.model.ActorDetails
import com.example.tmdbapp.domain.model.ActorMovie

private fun gender(genderId: Int): String = when (genderId) {
    1 -> "Female"
    2 -> "Male"
    3 -> "Non-Binary"
    else -> "Not specified"
}
internal fun ActorDto.toActor(isFavorite: Boolean): Actor {
    return Actor(
        id = id,
        name = name,
        department = knownForDepartment,
        imageUrl = "${Constants.IMAGE_BASE_URL}$profilePath",
        isFavorite = isFavorite,
        details = toActorDetails()
    )
}

internal fun ActorDto.toActorDetails(): ActorDetails {
    return ActorDetails(
        name = name,
        gender = gender(genderId),
        department = knownForDepartment,
        imageUrl = "${Constants.IMAGE_BASE_URL}$profilePath",
        knowFor = knownFor.map { it.toActorMovie() },
    )
}

internal fun ActorDetailsDto.toActorMovie() = ActorMovie(
    title = title,
    overview = overview,
    posterPath = "${Constants.IMAGE_BASE_URL}$posterPath",
    releaseDate = releaseDate
)

internal fun Actor.toActorEntity(): ActorEntity = ActorEntity(
    id = id,
    name = name,
    department = department,
    imageUrl = imageUrl,
    gender = details.gender
)

internal fun ActorEntity.toActor(movies: List<ActorMovie>, isFavorite: Boolean): Actor = Actor(
    id = id,
    name = name,
    department = department,
    imageUrl = imageUrl,
    isFavorite = isFavorite,
    details = ActorDetails(
        name = name,
        gender = gender,
        department = department,
        imageUrl = imageUrl,
        knowFor = movies
    )
)

internal fun ActorMovie.toActorMovieEntity(actorId: Int) = ActorMovieEntity(
    actorId = actorId,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath
)

fun ActorMovieEntity.toActorMovie() = ActorMovie(
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath
)
