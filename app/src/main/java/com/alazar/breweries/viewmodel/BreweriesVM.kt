package com.alazar.breweries.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.alazar.breweries.base.NetworkProvider
import com.alazar.breweries.repo.BreweryRepo
import com.alazar.breweries.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class BreweriesVM @Inject constructor(private val repo: BreweryRepo) : ViewModel() {

    @Inject
    lateinit var networkProvider: NetworkProvider


    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.getAll()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}