package com.roycemars.royalgold.feature.market.data.remote

import com.roycemars.royalgold.BuildConfig // To access your API key from buildConfigField
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("X-CMC_PRO_API_KEY", BuildConfig.COINMARKETCAP_API_KEY)
            // You can add other common headers here if needed
            // .header("Accept", "application/json")
            .build()
        return chain.proceed(newRequest)
    }
}