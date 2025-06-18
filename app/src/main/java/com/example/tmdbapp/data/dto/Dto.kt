package com.example.tmdbapp.data.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class PagingApiResponse<T>(
    val page: Int,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int,
    val results: List<T>
)

@Serializable
data class ActorDto(
    val id: Int,
    val name: String,
    @SerialName("gender") val genderId: Int,
    val popularity: Double,
    @SerialName("known_for_department") val knownForDepartment: String,
    @SerialName("profile_path") val profilePath: String? = "",
    @SerialName("known_for") val knownFor: List<ActorDetailsDto> = emptyList(),
)

@Serializable
data class ActorDetailsDto @OptIn(ExperimentalSerializationApi::class) constructor(
    @JsonNames("title", "name") val title: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String? = "",
    @JsonNames("release_date", "first_air_date") val releaseDate: String?
)
