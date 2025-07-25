package com.roycemars.royalgold.feature.market.data.remote

import com.google.gson.annotations.SerializedName

data class PriceDto(
    @SerializedName("percent_change_1h")
    val percentChange1h: Double,
    @SerializedName("percent_change_24h")
    val percentChange24h: Double,
    @SerializedName("percent_change_7d")
    val percentChange7d: Double,
    @SerializedName("percent_change_30d")
    val percentChange30d: Double,
    @SerializedName("last_updated")
    val lastUpdated: String
)