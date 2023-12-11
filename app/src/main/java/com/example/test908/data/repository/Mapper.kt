package com.example.test908.data.repository

import com.example.test908.data.repository.review.model.ReviewDto
import com.example.test908.data.repository.review.model.mapToDomain
import com.example.test908.domain.repository.review.model.Review
import com.example.test908.utils.DateUtils

fun ReviewDto.mapToDomain() = Review(
    abstract = abstract,
    byline = byline,
    multimedia = multimedia.map { it.mapToDomain() },
    publishedDate = DateUtils.parseLocalDateTime(publishedDate),
    title = title,
    shortUrl
)


