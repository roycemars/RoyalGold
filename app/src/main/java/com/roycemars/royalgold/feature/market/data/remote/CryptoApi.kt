package com.roycemars.royalgold.feature.market.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {
    @GET("quotes/latest")
    suspend fun getQuotes(
        @Query("symbol") symbol: String,
    ): List<CryptoDto>

    @GET("listings/latest")
    suspend fun getListings(
        @Query("start") start: Int = 1,
        @Query("limit") limit: Int = 100,
    ): CryptoListingsResponseDto

    companion object {
        const val BASE_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/"
    }
}