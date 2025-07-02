package com.roycemars.royalgold.model.network.news

import android.util.Log
import com.roycemars.royalgold.BuildConfig // If storing API key in BuildConfig (for debug ONLY)
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // --- WARNING: Storing API keys in BuildConfig is NOT secure for production apps. ---
    // This is a simplified example. For production, use a backend proxy or a more secure method.
    // Ensure your local.properties has: CMC_PRO_API_KEY="YOUR_ACTUAL_KEY"
    // And your build.gradle.kts has:
    // buildConfigField("String", "CMC_PRO_API_KEY", "\"${System.getenv("CMC_PRO_API_KEY") ?: getProperty("CMC_PRO_API_KEY")}\"")
    // fun getProperty(key: String): String = (project.findProperty(key) as? String) ?: ""
    // We will use a placeholder here for safety.
    private const val CMC_API_KEY_PLACEHOLDER = "YOUR_CMC_API_KEY_GOES_HERE_SECURELY"


    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        // You might add other interceptors here (e.g., for auth if not in header)
        .build()

    val cmcApiService: CmcApiService by lazy {
        Retrofit.Builder()
            .baseUrl(CmcApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CmcApiService::class.java)
    }

    // This function demonstrates how you might get the API key.
    // In a real app, this logic would be more robust and secure.
    fun getApiKey(): String {
        if (BuildConfig.CMC_PRO_API_KEY.isBlank() || BuildConfig.CMC_PRO_API_KEY == "null") {
            if (BuildConfig.DEBUG) {
                Log.e("RetrofitClient", "CMC API Key is not configured in local.properties or BuildConfig!")
                // You might want to return a dummy key or throw an exception for debug to catch this early
            }
            return "MISSING_API_KEY" // Or handle error appropriately
        }
        return BuildConfig.CMC_PRO_API_KEY
    }

//    fun getApiKey(): String {
//        // Prioritize BuildConfig if available (and if you've set it up there for debug)
//        // if (BuildConfig.CMC_PRO_API_KEY.isNotEmpty() && BuildConfig.CMC_PRO_API_KEY != "null") {
//        //     return BuildConfig.CMC_PRO_API_KEY
//        // }
//        // Fallback or primary method: environment variable or other secure storage.
//        // For this example, we return the placeholder. You MUST replace this.
//        if (CMC_API_KEY_PLACEHOLDER == "YOUR_CMC_API_KEY_GOES_HERE_SECURELY") {
//            // Log a warning or throw an error in debug builds if the key isn't set
//            if (BuildConfig.DEBUG) {
//                android.util.Log.e("RetrofitClient", "CMC API Key is not configured!")
//            }
//        }
//        return CMC_API_KEY_PLACEHOLDER
//    }
}