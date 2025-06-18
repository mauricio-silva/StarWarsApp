package com.example.tmdbapp.data.mapper

import com.example.tmdbapp.core.Constants
import com.example.tmdbapp.data.dto.ActorDetailsDto
import com.example.tmdbapp.data.dto.ActorDto
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.model.ActorDetails
import com.example.tmdbapp.domain.model.ActorMovie

private fun gender(genderId: Int): String = when (genderId) {
    1 -> "Female"
    2 -> "Male"
    3 -> "Non-Binary"
    else -> "Not specified"
}

private fun popularity(popularity: Double): Double = popularity.div(100).times(5)

internal fun ActorDto.toActor(): Actor {
    return Actor(
        id = id,
        name = name,
        department = knownForDepartment,
        imageUrl = "${Constants.IMAGE_BASE_URL}$profilePath",
        details = toActorDetails()
    )
}

internal fun ActorDto.toActorDetails(): ActorDetails {
    return ActorDetails(
        name = name,
        gender = gender(genderId),
        department = knownForDepartment,
        popularity = popularity(popularity),
        imageUrl = "${Constants.IMAGE_BASE_URL}$profilePath",
        knowFor = knownFor.map { it.toActorMovie() },
    )
}

internal fun ActorDetailsDto.toActorMovie() = ActorMovie(
    title = title,
    overview = overview,
    popularity = popularity(popularity),
    posterPath = "${Constants.IMAGE_BASE_URL}$posterPath",
    releaseDate = releaseDate
)
