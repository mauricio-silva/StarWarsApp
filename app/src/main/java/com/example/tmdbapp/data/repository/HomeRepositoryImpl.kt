package com.example.tmdbapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.tmdbapp.core.Constants
import com.example.tmdbapp.data.local.datasource.LocalDataSource
import com.example.tmdbapp.data.remote.api.TMDbApi
import com.example.tmdbapp.data.mapper.toActor
import com.example.tmdbapp.data.remote.paging.ActorsPagingSource
import com.example.tmdbapp.domain.model.Actor
import com.example.tmdbapp.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: TMDbApi,
    private val localDataSource: LocalDataSource,
    private val dispatcherIO: CoroutineDispatcher
) : HomeRepository {

    override fun getCharacters(): Flow<PagingData<Actor>> =
        Pager(
            config = PagingConfig(pageSize = Constants.PAGE_SIZE),
            pagingSourceFactory = { ActorsPagingSource(api) }
        ).flow.map { it.map { dto ->
            val isFavorite = localDataSource.isFavorite(dto.id)
            dto.toActor(isFavorite)
        } }
            .flowOn(dispatcherIO)
}
