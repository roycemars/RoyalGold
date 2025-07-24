package com.roycemars.royalgold.feature.market.data.remote

import com.google.gson.annotations.SerializedName

data class CryptoDto(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,

    @SerializedName("last_updated")
    val lastUpdated: String
)