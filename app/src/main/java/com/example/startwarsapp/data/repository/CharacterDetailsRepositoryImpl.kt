package com.example.startwarsapp.data.repository

import com.example.startwarsapp.domain.model.CharacterDetails
import com.example.startwarsapp.domain.model.Planet
import com.example.startwarsapp.domain.repository.CharacterDetailsRepository

class CharacterDetailsRepositoryImpl : CharacterDetailsRepository {

    override suspend fun getCharacter(id: Int): Result<CharacterDetails> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlanet(url: String): Result<Planet> {
        TODO("Not yet implemented")
    }
}