package com.roycemars.royalgold.feature.market.data.remote

data class CryptoDto(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,
    val last_updated: String
)