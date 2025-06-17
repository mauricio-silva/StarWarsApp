package com.example.startwarsapp.domain

data class Character(
    val id: Int,
    val name: String,
    val birthYear: String,
    val gender: String,
    val imageUrl: String
)

data class CharacterDetails(
    val id: Int,
    val name: String,
    val birthYear: String,
    val gender: String,
    val height: String,
    val mass: String,
    val eyeColor: String,
    val homeworld: Planet,
    val imageUrl: String
)

data class Planet(val name: String)
