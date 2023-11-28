package com.example.test908.data.remote

import com.example.test908.data.dto.NumResultDto
import retrofit2.http.GET

interface ReviewsServiceRetrofit {
    @GET("arts.json?")
    suspend fun getReview(): NumResultDto
}
