package com.roycemars.royalgold.feature.market.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CryptoEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,
    val lastUpdated: Long
)