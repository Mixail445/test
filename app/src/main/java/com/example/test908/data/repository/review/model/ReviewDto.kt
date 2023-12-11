package com.example.test908.data.repository.review.model

import com.squareup.moshi.Json


data class ReviewDto(
    @Json(name = "abstract")
    val abstract: String? = null,
    @Json(name = "byline")
    val byline: String? = null,
    @Json(name = "multimedia")
    val multimedia: List<MultimediaDto>? = null,
    @Json(name = "published_date")
    val publishedDate: String? = null,
    @Json(name = "short_url")
    val shortUrl: String? = null,
    @Json(name = "title")
    val title: String? = null
)
