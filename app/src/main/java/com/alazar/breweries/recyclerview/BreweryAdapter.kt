package com.alazar.breweries.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alazar.breweries.R
import com.alazar.breweries.data.Brewery
import java.util.*

class BreweryAdapter(itemListener: RecyclerViewClickListener) :
    RecyclerView.Adapter<BreweryAdapter.ViewHolder>() {

    private val items: MutableList<Brewery> = ArrayList<Brewery>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.brewery_item, parent, false)
        return ViewHolder(view)
    }

    fun setItems(news: List<Brewery>) {
        this.items.addAll(news)
        notifyDataSetChanged()
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = items[position].name.trim()
        holder.phone.text = items[position].phone?.trim()
        holder.country.text = items[position].country?.trim()
        holder.city.text = items[position].city?.trim()
        holder.website.text = items[position].website_url?.trim()


        checkFieldsVisibility(
            mapOf(
                holder.phone to holder.phoneGroup,
                holder.country to holder.countryGroup,
                holder.city to holder.cityGroup,
                holder.website to holder.websiteGroup
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun checkFieldsVisibility(mapView: Map<TextView, LinearLayout>) {
        for (item in mapView) {
            if (item.key.text.isNotEmpty()) {
                item.value.visibility = View.VISIBLE
            } else {
                item.value.visibility = View.GONE
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val name: TextView = view.findViewById(R.id.nameTxt)

        val phone: TextView = view.findViewById(R.id.phoneTxt)
        val phoneGroup: LinearLayout = view.findViewById(R.id.phoneGroup)

        val country: TextView = view.findViewById(R.id.countryTxt)
        val countryGroup: LinearLayout = view.findViewById(R.id.countryGroup)

        val city: TextView = view.findViewById(R.id.cityTxt)
        val cityGroup: LinearLayout = view.findViewById(R.id.cityGroup)

        val website: TextView = view.findViewById(R.id.websiteTxt)
        val websiteGroup: LinearLayout = view.findViewById(R.id.websiteGroup)

        override fun onClick(v: View) {
            itemListener?.recyclerViewListClicked(website.text.toString(), v, this.layoutPosition)
        }

        init {
            view.setOnClickListener(this)
        }
    }

    companion object {
        private var itemListener: RecyclerViewClickListener? = null
    }

    init {
        Companion.itemListener = itemListener
    }
}