package com.soma2026.lab.core.network

import okhttp3.Interceptor
import okhttp3.Response

internal class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("X-Auth-Token", apiKey)
            .build()
        return chain.proceed(request)
    }
}
