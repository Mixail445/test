package com.example.test908.domain.repository.review.model

import com.example.test908.presentation.reviews.ReviewUi
import com.example.test908.utils.DateUtils
import com.example.test908.utils.format
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
) {
    companion object {
        fun mock() = Review(
            abstract = "abstract",
            byline = "byline",
            createdDate ="",
            desFacet = emptyList(),
            geoFacet = emptyList(),
        itemType = "",
        kicker = "",
            materialTypeFacet = "",
        multimedia = emptyList(),
        orgFacet= emptyList(),
        perFacet= emptyList(),
        publishedDate = LocalDateTime.of(2023, 1, 1, 1, 1),
        section= "",
        shortUrl = "",
        subsection = "",
        title = "",
        updatedDate = "",
        uri = "",
        url = "",
        )
    }
}

fun Review.mapToUi(): ReviewUi =
    ReviewUi(
        abstract = abstract,
        byline = byline,
        date = publishedDate?.format(DateUtils.CALENDAR_UI_ITEM_FORMAT).orEmpty(),
        title = title,
        pictureSrc = multimedia.getOrNull(0)?.url,
        itemId = shortUrl
    )
