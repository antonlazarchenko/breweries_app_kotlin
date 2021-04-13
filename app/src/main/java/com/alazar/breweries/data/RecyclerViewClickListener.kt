package com.alazar.breweries.data

import android.view.View

interface RecyclerViewClickListener {
    fun recyclerViewListClicked(link: String, v: View, position: Int)
}