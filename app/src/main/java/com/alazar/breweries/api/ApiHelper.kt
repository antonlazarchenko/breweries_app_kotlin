package com.alazar.breweries.api

import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {
    suspend fun getAll() = apiService.getAll()

    suspend fun searchByName(name: String) = apiService.searchByName(name)
}