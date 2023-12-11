package com.example.test908.domain.repository.review.model

import com.example.test908.presentation.reviews.ReviewUi
import com.example.test908.utils.DateUtils
import com.example.test908.utils.format
import java.time.LocalDateTime

data class Review(
    val abstract: String,
    val byline: String,
    val multimedia: String,
    val publishedDate: LocalDateTime?,
    val title: String,
    val shortUrl: String

) {
    fun mapToUi() = ReviewUi(
    abstract = abstract,
    byline = byline,
    date = publishedDate?.format(DateUtils.CALENDAR_UI_ITEM_FORMAT).orEmpty(),
    title = title,
    pictureSrc = multimedia,
    itemId = shortUrl
)
}

