package com.alazar.breweries.api

import com.alazar.breweries.data.Brewery
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("breweries")
    suspend fun getAll(): List<Brewery>

    @GET("breweries?by_name={query}")
    suspend fun searchByName(): List<Brewery>
}
