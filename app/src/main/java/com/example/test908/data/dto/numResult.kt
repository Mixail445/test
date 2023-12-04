package com.example.test908.data.dto

import com.example.test908.domain.model.NumResult
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

fun ReviewsResponseDto.mapToDomain(): NumResult =
    NumResult(
        copyright = copyright,
        lastUpdated = lastUpdated,
        numResults = numResults,
        results = results.map { it.mapToDomain() },
        section = section,
        status = status
    )
