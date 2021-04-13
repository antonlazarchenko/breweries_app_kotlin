package com.alazar.breweries.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alazar.breweries.api.ApiHelper
import com.alazar.breweries.repo.BreweryRepo
import com.alazar.breweries.viewmodel.BreweriesVM

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreweriesVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BreweriesVM(BreweryRepo(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}