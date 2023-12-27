package com.example.test908.data.repository.review.remote

import com.example.test908.data.repository.review.model.ReviewsResponseDto
import retrofit2.http.GET

interface ReviewApi {
    @GET("topstories/v2/arts.json?")
    suspend fun getReviews(): ReviewsResponseDto
}
