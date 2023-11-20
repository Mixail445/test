package com.example.test908.network


import com.example.test908.data.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceRetrofit {
    //Get all reviews
    @GET("arts.json?")
    suspend fun getReview(
        @Query("api-key") api_key: String,
    ): Result


}