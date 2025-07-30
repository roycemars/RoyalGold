package com.roycemars.royalgold.feature.market.data.gemini

import kotlinx.coroutines.flow.Flow

interface GeminiRepository {
    suspend fun summarize(input: String): Flow<String>
}