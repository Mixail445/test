package com.example.test908.data.repository

import com.example.test908.data.repository.review.model.ReviewDto
import com.example.test908.data.repository.review.model.mapToDomain
import com.example.test908.domain.repository.review.model.Review
import com.example.test908.utils.DateUtils

fun ReviewDto.mapToDomain() = Review(
    abstract = abstract,
    byline = byline,
    createdDate = createdDate,
    desFacet = desFacet,
    geoFacet = geoFacet,
    itemType = itemType,
    kicker = kicker,
    materialTypeFacet = materialTypeFacet,
    multimedia = multimedia.map { it.mapToDomain() },
    orgFacet = orgFacet,
    perFacet = perFacet,
    publishedDate = DateUtils.parseLocalDateTime(publishedDate),
    section = section,
    shortUrl = shortUrl,
    subsection = subsection,
    title = title,
    updatedDate = updatedDate,
    url = url,
    uri = uri
)


