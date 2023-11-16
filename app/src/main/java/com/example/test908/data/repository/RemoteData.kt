package com.example.test908.data.repository

import com.example.test908.network.ServiceRetrofit
import javax.inject.Inject

class RemoteData @Inject constructor(private val service: ServiceRetrofit) {
    suspend fun getUser(api: String) = service.getreview(api)
}