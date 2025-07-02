package com.roycemars.royalgold.model.network.gemini

import com.google.gson.annotations.SerializedName

// --- Request Data Classes ---
data class GeminiRequest(
    @SerializedName("contents") val contents: List<ContentRequest>
)

data class ContentRequest(
    @SerializedName("parts") val parts: List<PartRequest>
)

data class PartRequest(
    @SerializedName("text") val text: String? = null,
    @SerializedName("inline_data") val inlineData: InlineData? = null // Gemini uses inline_data
)

data class InlineData(
    @SerializedName("mime_type") val mimeType: String,
    @SerializedName("data") val data: String
)

// --- Response Data Classes (Simplified - adjust to actual Gemini response) ---
// The Gemini API response is more complex. This is a placeholder for the text part.
data class GeminiApiResponse(
    @SerializedName("candidates") val candidates: List<Candidate>?
)

data class Candidate(
    @SerializedName("content") val content: ContentResponse?
)

data class ContentResponse(
    @SerializedName("parts") val parts: List<PartResponse>?
)

data class PartResponse(
    @SerializedName("text") val text: String? // This should contain your extracted JSON string
)