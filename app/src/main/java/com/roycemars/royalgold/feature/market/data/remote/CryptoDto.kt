package com.roycemars.royalgold.feature.market.data.remote

import com.google.gson.annotations.SerializedName

data class CryptoDto(
    val id: Int,
    val name: String,
    val symbol: String,
    @SerializedName("last_updated")
    val lastUpdated: String,
    val quote: UsdQuoteDataDto?
)