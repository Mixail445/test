package com.example.test908.data.repository

import com.example.test908.data.repository.books.featureFlag.remote.model.FeatureFlagsDto
import com.example.test908.data.repository.books.limitedSeries.model.BookOfferDto
import com.example.test908.data.repository.books.model.BooksDataDto
import com.example.test908.data.repository.books.model.BooksStoreDto
import com.example.test908.data.repository.review.entity.ReviewEntity
import com.example.test908.data.repository.review.model.ReviewDto
import com.example.test908.domain.repository.books.model.Books
import com.example.test908.domain.repository.books.model.BooksStore
import com.example.test908.domain.repository.featureFlag.FeatureFlags
import com.example.test908.domain.repository.limitedSeries.model.BookOffer
import com.example.test908.domain.repository.review.model.Review
import com.example.test908.utils.DateUtils

fun ReviewDto.mapToEntity() =
    ReviewEntity(
        abstracts = abstract.orEmpty(),
        byline = byline.orEmpty(),
        multimedia = multimedia?.firstOrNull()?.url.orEmpty(),
        publishedDate = publishedDate.orEmpty(),
        title = title.orEmpty(),
    )

fun ReviewEntity.mapToDomain() =
    Review(
        abstract = abstracts.orEmpty(),
        byline = byline.orEmpty(),
        multimedia = multimedia.orEmpty(),
        publishedDate = DateUtils.parseLocalDateTime(publishedDate),
        title = title.orEmpty(),
        localId = id.toString(),
    )

fun BooksDataDto.mapToDomain() =
    Books(
        description = description,
        title = title,
        author = author,
        bookImage = bookImage,
        buyLinksName = buyLinks.map { it.mapToDomain() },
        id = rank,
    )

fun BooksStoreDto.mapToDomain() = BooksStore(name = name, url = url)

fun BookOfferDto.mapToDomain() =
    BookOffer(
        id = id,
        expiresDate = DateUtils.parseLocalDate(expiresDate),
        title = title,
        description = description,
        price = price,
    )

fun FeatureFlagsDto.mapToDomain() =
    FeatureFlags(
        isLimitedSeriesEnabled,
    )
