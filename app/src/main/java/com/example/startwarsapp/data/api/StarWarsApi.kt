package com.example.startwarsapp.data.api

import com.example.startwarsapp.data.dto.CharacterResponseDto
import com.example.startwarsapp.data.dto.PlanetDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface StarWarsApi  {

    @GET("api/people/")
    suspend fun getPeople(@Query("page") page: Int): CharacterResponseDto

    @GET
    suspend fun getPlanet(@Url url: String): PlanetDto
}
