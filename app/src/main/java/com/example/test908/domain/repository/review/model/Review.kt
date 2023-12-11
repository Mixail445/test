package com.example.test908.domain.repository.review.model

import com.example.test908.presentation.reviews.ReviewUi
import com.example.test908.utils.DateUtils
import com.example.test908.utils.format
import java.time.LocalDateTime

data class Review(
    val abstract: String,
    val byline: String,
    val multimedia: List<Multimedia>,
    val publishedDate: LocalDateTime?,
    val title: String,
    val shortUrl:String

) {
    companion object {
        fun mock() = Review(
            abstract = "abstract",
            byline = "byline",
            multimedia = emptyList(),
            publishedDate = LocalDateTime.of(2023, 1, 1, 1, 1),
            title = "",
            shortUrl = ""
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

