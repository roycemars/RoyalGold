package com.roycemars.royalgold.core.ai

import android.util.Base64
import android.util.Log
import com.roycemars.royalgold.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File

class AiReceiptScanner {
    private val geminiService = GeminiRetrofitClient.instance

    suspend fun callGeminiAPI(imageFile: File): JSONObject {
        val apiKey = BuildConfig.GEMINI_API_KEY // Access the key here

        if (apiKey == "MISSING_API_KEY") {
            // Handle the case where the API key is missing (e.g., log an error, show a message to the user)
            Log.e("AiReceiptScanner", "API Key is missing. Please check your configuration.")
            return JSONObject() // Or throw an exception
        }


        val prompt = """
        Extract item names and prices and total from this receipt image. Return result as JSON like:
        {
          "items": [{"name": "...", "price": ...}],
          "total": ...
        }
    """.trimIndent()

        val imageBase64 = try {
            withContext(Dispatchers.IO) { // Perform file reading on IO dispatcher
                Base64.encodeToString(imageFile.readBytes(), Base64.NO_WRAP)
            }
        } catch (e: Exception) {
            Log.e("AiReceiptScanner", "Error reading or encoding image file", e)
            return JSONObject() // Or throw
        }


        val payload = JSONObject().apply {
            put("contents", listOf(
                mapOf("parts" to listOf(
                    mapOf("text" to prompt),
                    mapOf("inlineData" to mapOf(
                        "mimeType" to "image/jpeg",
                        "data" to imageBase64
                    ))
                ))
            ))
        }

//        TODO: fix googleapis request
//        val response = post("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro-vision:generateContent?key=$apiKey", payload)

        val requestPayload = GeminiRequest(
            contents = listOf(
                ContentRequest(
                    parts = listOf(
                        PartRequest(text = prompt),
                        PartRequest(
                            inlineData = InlineData(
                                mimeType = "image/jpeg",
                                data = imageBase64
                            )
                        )
                    )
                )
            )
        )

        return try {
            val response = withContext(Dispatchers.IO) { // Network call on IO dispatcher
                geminiService.generateContent(apiKey, requestPayload)
            }

            if (response.isSuccessful) {
                val geminiApiResponse = response.body()
                // The actual JSON string you want is likely nested within the response.
                // You need to parse `geminiApiResponse` to extract it.
                val extractedJsonString = geminiApiResponse?.candidates?.firstOrNull()
                    ?.content?.parts?.firstOrNull()?.text

                if (extractedJsonString != null) {
                    Log.d("AiReceiptScanner", "Extracted JSON: $extractedJsonString")
                    JSONObject(extractedJsonString) // Parse the extracted string
                } else {
                    Log.e("AiReceiptScanner", "Could not extract text from Gemini response. Body: ${response.body().toString()}")
                    JSONObject()
                }
            } else {
                Log.e("AiReceiptScanner", "API call failed with error code: ${response.code()} - ${response.message()}")
                Log.e("AiReceiptScanner", "Error body: ${response.errorBody()?.string()}")
                JSONObject()
            }
        } catch (e: Exception) {
            Log.e("AiReceiptScanner", "API call failed with exception: ${e.message}", e)
            JSONObject()
        }
    }
}