package com.roycemars.royalgold.feature.market.domain

import java.util.Date

class Crypto(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,
    val lastUpdated: Long
)