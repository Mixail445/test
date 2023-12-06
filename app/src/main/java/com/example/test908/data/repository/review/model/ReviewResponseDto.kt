package com.example.test908.data.repository.review.model

import com.google.gson.annotations.SerializedName

data class ReviewsResponseDto(
    val copyright: String,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("num_results")
    val numResults: Int,
    val results: List<ReviewDto>,
    val section: String,
    val status: String
)
