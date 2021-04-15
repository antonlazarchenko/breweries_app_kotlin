package com.alazar.breweries.api

import com.alazar.breweries.data.Brewery
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("breweries")
    suspend fun getAll(): List<Brewery>

    @GET("breweries")
    suspend fun searchByName(@Query("by_name") name: String): List<Brewery>
}
