package com.example.startwarsapp.domain.repository

import androidx.paging.PagingData
import com.example.startwarsapp.domain.model.Character
import com.example.startwarsapp.domain.model.CharacterDetails
import com.example.startwarsapp.domain.model.Planet
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getCharacters(): Flow<PagingData<Character>>
}

interface CharacterDetailsRepository {
    suspend fun getCharacter(id: Int): Result<CharacterDetails>
    suspend fun getPlanet(url: String): Result<Planet>
}

interface FavoritesRepository {
    suspend fun saveFavorite(character: Character)
    fun getFavorites(): Flow<List<Character>>
    suspend fun removeFavorite(id: Int)
}
