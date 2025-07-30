package com.roycemars.royalgold.feature.market.data.gemini

import com.google.firebase.Firebase
import com.google.firebase.vertexai.type.HarmBlockThreshold
import com.google.firebase.vertexai.type.HarmCategory
import com.google.firebase.vertexai.type.SafetySetting
import com.google.firebase.vertexai.type.generationConfig
import com.google.firebase.vertexai.vertexAI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor(): GeminiRepository {
    val prompt = "Suggest which crypto is best to buy today, using following list of crypto in format [Symbol, price, % change 1 hour, % change 24 hours]:\n" +
            "\n" +
            "BTC, 117880.31722817733 -0.17744344 -1.04860029\n" +
            "LTC, 108.79108851281202 -0.19924454 -5.35351776\n" +
            "XRP, 3.124183406464968 0.06732171 -4.25292472\n" +
            "DOGE, 0.22535682243589572 -0.29235607 -6.14554677\n" +
            "XLM, 0.4192367712760907 0.20855056 -6.14119226\n" +
            "\n" +
            "Provide response in 2 parts:\n" +
            "\n" +
            "1) symbol & short one-string motivation why\n" +
            "2) analysis of data"

    private val generativeModel = Firebase.vertexAI.generativeModel(
        "gemini-2.0-flash",
        generationConfig = generationConfig {
            temperature = 0f
        },
        safetySettings = listOf(
            SafetySetting(HarmCategory.HARASSMENT, HarmBlockThreshold.LOW_AND_ABOVE),
            SafetySetting(HarmCategory.HATE_SPEECH, HarmBlockThreshold.LOW_AND_ABOVE),
            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, HarmBlockThreshold.LOW_AND_ABOVE),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, HarmBlockThreshold.LOW_AND_ABOVE),
        )
    )

    override suspend fun summarize(input: String): String {
//        val postString = StringBuilder()
//        for (paragraph in post.paragraphs) {
//            postString.append(paragraph.text)
//        }

//        val prompt =
//            "Summarize the following article in 4 concise bullet points. " +
//                    "Ensure each bullet point is specific, informative and relevant. " +
//                    "Return just the bullet points as plain text. " +
//                    "Use plain text, don't use markdown. \n $input"

        return generativeModel.generateContent(prompt).text ?: ""
    }
}