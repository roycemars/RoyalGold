package com.roycemars.royalgold.feature.market.data.remote

data class StatusDto(
    val timestamp: String,
    val errorCode: Int,
    val errorMessage: String?,
    val elapsed: Int,
    val creditCount: Int,
    val notice: String?,
    val totalCount: Int
)