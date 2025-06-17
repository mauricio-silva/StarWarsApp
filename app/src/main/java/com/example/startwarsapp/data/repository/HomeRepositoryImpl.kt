package com.example.startwarsapp.data.repository

import androidx.paging.PagingData
import com.example.startwarsapp.domain.model.Character
import com.example.startwarsapp.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl : HomeRepository {

    override fun getCharacters(): Flow<PagingData<Character>> {
        TODO("Not yet implemented")
    }
}