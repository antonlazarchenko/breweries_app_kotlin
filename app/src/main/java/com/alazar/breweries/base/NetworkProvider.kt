package com.alazar.breweries.base

interface NetworkProvider {
    fun isConnected(): Boolean
}