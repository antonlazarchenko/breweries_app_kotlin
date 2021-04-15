package com.alazar.breweries.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.alazar.breweries.base.NetworkProvider
import com.alazar.breweries.di.App
import javax.inject.Inject

class NetworkWrapper @Inject constructor() : NetworkProvider {

    @Inject
    lateinit var context: Context

    private lateinit var connectivityManager: ConnectivityManager


    init {
        App.getComponent().inject(this)
    }

    override fun isConnected() : Boolean {

        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork
            val actNw = connectivityManager.getNetworkCapabilities(nw)
            (actNw != null
                    && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)))
        } else {
            @Suppress("DEPRECATION")
            val nwInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            nwInfo != null && nwInfo.isConnected
        }
    }
}