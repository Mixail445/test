package com.example.test908.domain.model

import com.example.test908.presentation.reviewList.ReviewUi

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
    val publishedDate: String,
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
        multimedia = multimedia.map { it.mapToUi() },
        publishedDate = publishedDate,
        title = title
    )
