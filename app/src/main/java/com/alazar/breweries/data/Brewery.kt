package com.alazar.breweries.data

import com.google.gson.annotations.SerializedName

data class Brewery(

    val id: Int,
    val name: String,
    val phone: String,

    val latitude: String,
    val longitude: String,

    @SerializedName("website_url")
    val website_url: String,

    val city: String,
    val country: String,
    @SerializedName("postal_code")
    val postalCode: String,
    val state: String,
    val street: String,
    @SerializedName("address_2")
    val addressSecond: String?,
    @SerializedName("address_3")
    val addressThird: String?,
    @SerializedName("brewery_type")
    val breweryType: String,
    @SerializedName("county_province")
    val countyProvince: String?,

    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at: String,

    )