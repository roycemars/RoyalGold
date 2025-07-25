package com.roycemars.royalgold.feature.market.domain

import java.util.Date

class Crypto(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,
    val percentChange1h: Double,
    val percentChange24h: Double,
    val percentChange7d: Double,
    val percentChange30d: Double,
    val lastUpdated: Long
)