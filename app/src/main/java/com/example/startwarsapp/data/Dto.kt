package com.example.startwarsapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val name: String,
    @SerialName("birth_year") val birthYear: String,
    val gender: String,
    val height: String,
    val mass: String,
    @SerialName("eye_color") val eyeColor: String,
    val homeworld: String,
    val url: String
)

@Serializable
data class CharacterResponseDto(val results: List<CharacterDto>)

@Serializable
data class PlanetDto(val name: String)
