package com.example.test908.data.network

import com.example.test908.Constant.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url
            .newBuilder()
            .addQueryParameter(
                "api-key",
                API_KEY
            )
            .build()
        val request = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(request)
    }
}
