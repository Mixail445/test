package com.example.test908.network


import com.example.test908.data.modelone.ResultOne
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceRetrofit {
    //Get all reviews

    @GET("arts.json?")
    suspend fun getreview(
        @Query("api-key") api_key: String,
    ): ResultOne


}