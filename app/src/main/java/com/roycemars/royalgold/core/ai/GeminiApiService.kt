package com.roycemars.royalgold.core.ai

import retrofit2.Response // Use Retrofit's Response for more details
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {
    @POST("v1beta/models/gemini-pro-vision:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body requestBody: GeminiRequest
    ): Response<GeminiApiResponse> // Wrap response in Retrofit's Response
}