package com.example.test908.data.remote


import com.example.test908.data.dto.NumResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ReviewsServiceRetrofit {
    @GET("arts.json?")
    suspend fun getReview(
        @Query("api-key") apikey: String,
    ): NumResultDto


}