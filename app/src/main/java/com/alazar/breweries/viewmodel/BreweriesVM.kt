package com.alazar.breweries.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.alazar.breweries.base.NetworkProvider
import com.alazar.breweries.repo.BreweryRepo
import com.alazar.breweries.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class BreweriesVM @Inject constructor(private val repo: BreweryRepo) : ViewModel() {

    companion object {
        private val TAG = BreweriesVM::class.simpleName
    }

    @Inject
    lateinit var networkProvider: NetworkProvider

    private val errorMsg: String = "Error Occurred!"

    fun getBreweries() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.getAll()))
        } catch (e: Exception) {
            emit(getErrorResource(e))
        }
    }

    fun searchBreweriesByName(name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.searchByName(name)))
        } catch (e: Exception) {
            emit(getErrorResource(e))
        }
    }

    private fun getErrorResource(exception: java.lang.Exception): Resource<Nothing> {
        Log.d(TAG, "ERROR MSG ::: " + exception.message.toString())
        Log.d(TAG, exception.stackTraceToString())

        return Resource.error(data = null, message = exception.message ?: errorMsg)
    }
}