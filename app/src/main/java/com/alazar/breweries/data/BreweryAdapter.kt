package com.alazar.breweries.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alazar.breweries.R
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


        val list = listOf(
//            holder.name,
            holder.phone,
            holder.country,
            holder.city,
            holder.website)
        checkFieldsVisibility(list)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun checkFieldsVisibility(listView: List<TextView>) {
        for (item in listView) {
            if (item.text.isNotEmpty())
                item.visibility = View.VISIBLE
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val name: TextView = view.findViewById(R.id.nameTxt)
        val phone: TextView = view.findViewById(R.id.phoneTxt)
        val country: TextView = view.findViewById(R.id.countryTxt)
        val city: TextView = view.findViewById(R.id.cityTxt)
        val website: TextView = view.findViewById(R.id.websiteTxt)

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