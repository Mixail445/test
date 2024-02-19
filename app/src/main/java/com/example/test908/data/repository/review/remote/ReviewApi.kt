package com.example.test908.data.repository.review.remote

import com.example.test908.data.repository.review.model.ReviewsResponseDto
import retrofit2.http.GET

/**
 * [API DOCUMENTATION](https://developer.nytimes.com/docs/top-stories-product/1/overview)
 */
interface ReviewApi {
    /**
     * Api Review[here](https://api.nytimes.com/svc/topstories/v2/arts.json?&api-key=GW5a0tJfWOcfQ7k3dpQizIsrmpZ33Bmm)
     */
    @GET("topstories/v2/arts.json?")
    suspend fun getReviews(): ReviewsResponseDto
}
