package com.example.test908.data.repository.review.model


import com.squareup.moshi.Json


data class ReviewsResponseDto(
    val copyright: String,
    @Json(name = "last_updated")
    val lastUpdated: String,
    @Json(name = "num_results")
    val numResults: Int,
    val results: List<ReviewDto>,
    val section: String,
    val status: String
)
