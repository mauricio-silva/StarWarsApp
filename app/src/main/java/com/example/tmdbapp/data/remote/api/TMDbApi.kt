package com.example.tmdbapp.data.api

import com.example.tmdbapp.data.dto.ActorDto
import com.example.tmdbapp.data.dto.PagingApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbApi  {

    @GET("person/popular")
    suspend fun getActors(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): PagingApiResponse<ActorDto>

}
