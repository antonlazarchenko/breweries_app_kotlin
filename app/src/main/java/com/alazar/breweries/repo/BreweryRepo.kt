package com.alazar.breweries.repo

import com.alazar.breweries.api.ApiHelper
import javax.inject.Inject

class BreweryRepo @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getAll() = apiHelper.getAll()

    suspend fun searchByName(name: String) = apiHelper.searchByName(name)
}