package com.example.tmdbapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdbapp.data.remote.api.TMDbApi
import com.example.tmdbapp.data.remote.dto.ActorDto

class ActorsPagingSource(
    private val api: TMDbApi
) : PagingSource<Int, ActorDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ActorDto> {
        val page = params.key ?: 1
        return try {
            val response = api.getActors(page = page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ActorDto>): Int = 1
}
