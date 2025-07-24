package com.roycemars.royalgold.feature.market.data.remote

data class CryptoListingsResponseDto(
        val status: StatusDto,
        val data: List<CryptoDto>
    )