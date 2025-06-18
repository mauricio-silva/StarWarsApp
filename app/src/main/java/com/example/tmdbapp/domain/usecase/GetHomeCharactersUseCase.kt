package com.example.tmdbapp.domain.usecase

import androidx.paging.PagingData
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeCharactersUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(): Flow<PagingData<Actor>> = repository.getCharacters()
}
