package com.example.startwarsapp.data

import com.example.startwarsapp.core.Constants
import com.example.startwarsapp.domain.Character
import com.example.startwarsapp.domain.CharacterDetails
import com.example.startwarsapp.domain.Planet

fun extractId(url: String): Int = url.trimEnd('/').split("/").last().toInt()

internal fun CharacterDto.toDomain(): Character {
    val id = extractId(url)
    return Character(
        id = id,
        name = name,
        gender = gender,
        birthYear = birthYear,
        imageUrl = "${Constants.IMAGE_BASE_URL}$id${Constants.IMAGE_EXTENSION}"
    )
}

internal fun CharacterDto.toDomainDetails(planet: Planet): CharacterDetails {
    val id = extractId(url)
    return CharacterDetails(
        id = id,
        name = name,
        gender = gender,
        birthYear = birthYear,
        height = height,
        mass = mass,
        eyeColor = eyeColor,
        homeworld = planet,
        imageUrl = "${Constants.IMAGE_BASE_URL}$id${Constants.IMAGE_EXTENSION}"
    )
}

internal fun PlanetDto.toDomain(): Planet = Planet(name = name)