package com.roycemars.royalgold.data.network.news

import com.roycemars.royalgold.data.news.CmcBaseResponse
import com.roycemars.royalgold.data.news.CmcContentLatestData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CmcApiService {
    // IMPORTANT: Replace "YOUR_CMC_API_KEY" with a secure way to provide your key
    // This should NOT be hardcoded in client code for production.
    // Consider BuildConfig or a backend proxy.
    companion object {
        const val BASE_URL = "https://pro-api.coinmarketcap.com/"
        const val API_KEY_HEADER = "X-CMC_PRO_API_KEY"
    }

    @GET("v1/content/latest")
    suspend fun getLatestNews(
        @Header(API_KEY_HEADER) apiKey: String, // Pass API key securely
        @Query("limit") limit: Int = 20, // Number of results
        @Query("cursor") cursor: String? = null // For pagination
    ): Response<CmcBaseResponse<CmcContentLatestData>>
}