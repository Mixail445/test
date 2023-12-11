package com.example.test908.data.repository.review.model

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("abstract")
    val abstract: String,
    @SerializedName("byline")
    val byline: String,
    @SerializedName("multimedia")
    val multimedia: List<MultimediaDto>,
    @SerializedName("published_date")
    val publishedDate: String,
    @SerializedName("short_url")
    val shortUrl: String,
    @SerializedName("title")
    val title: String
)
