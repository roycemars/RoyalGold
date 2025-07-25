package com.roycemars.royalgold.feature.market.data.remote

import com.google.gson.annotations.SerializedName

data class usdQuoteDataDto(
    @SerializedName("USD")
    val usd: quoteDataDto?
)