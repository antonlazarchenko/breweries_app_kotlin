package com.alazar.breweries.recyclerview

import android.view.View

interface RecyclerViewClickListener {
    fun recyclerViewListClicked(link: String, v: View, position: Int)
}