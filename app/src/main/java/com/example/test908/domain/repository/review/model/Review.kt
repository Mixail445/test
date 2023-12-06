package com.example.test908.domain.repository.review.model

import com.example.test908.presentation.reviews.ReviewUi
import java.time.LocalDateTime

data class Review(
    val abstract: String,
    val byline: String,
    val createdDate: String,
    val desFacet: List<String>,
    val geoFacet: List<String>,
    val itemType: String,
    val kicker: String,
    val materialTypeFacet: String,
    val multimedia: List<Multimedia>,
    val orgFacet: List<String>,
    val perFacet: List<String>,
    val publishedDate: LocalDateTime?,
    val section: String,
    val shortUrl: String,
    val subsection: String,
    val title: String,
    val updatedDate: String,
    val uri: String,
    val url: String
)

fun Review.mapToUi(): ReviewUi =
    ReviewUi(
        abstract = abstract,
        byline = byline,
        publishedDate = publishedDate,
        title = title,
        pictureSrc = multimedia[0].url,
        itemId = shortUrl
    )