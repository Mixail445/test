package com.example.test908.data.repository.books.model

import com.squareup.moshi.Json

data class BooksDataDto(
    val rank: Long,
    val description: String,
    val title: String,
    val author: String,
    val contributor: String,
    @Json(name = "book_image")
    val bookImage: String,
    @Json(name = "amazon_product_url")
    val amazonProductUrl: String,
    @Json(name = "book_review_link")
    val bookReviewLink: String,
    @Json(name = "buy_links")
    val buyLinks: List<BooksStoreDto>
)
