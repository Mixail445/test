package com.example.test908.data.repository

import com.example.test908.data.repository.books.model.BooksDataDto
import com.example.test908.data.repository.books.model.BooksStoreDto
import com.example.test908.data.repository.review.entity.ReviewEntity
import com.example.test908.data.repository.review.model.ReviewDto
import com.example.test908.domain.repository.books.model.Books
import com.example.test908.domain.repository.books.model.BooksStore
import com.example.test908.domain.repository.review.model.Review
import com.example.test908.utils.DateUtils

fun ReviewDto.mapToEntity() = ReviewEntity(
    abstract = abstract.orEmpty(),
    byline = byline.orEmpty(),
    multimedia = multimedia?.firstOrNull()?.url.orEmpty(),
    publishedDate = publishedDate.orEmpty(),
    title = title.orEmpty(),
    shortUrl = shortUrl.orEmpty()
)
fun ReviewEntity.mapToDomain() =
    Review(
        abstract = abstract,
        byline = byline,
        multimedia = multimedia,
        publishedDate = DateUtils.parseLocalDateTime(publishedDate),
        title = title,
        shortUrl = shortUrl,
        localId = id.toString()
    )
fun BooksDataDto.mapToDomain() =
    Books(
        description = description,
        title = title,
        author = author,
        bookImage = bookImage,
        buyLinksName = buyLinks.map { it.mapToDomain() },
        id = rank
    )
fun BooksStoreDto.mapToDomain() =
    BooksStore(name, url)




