package com.example.test908.data.repository.books.model

import com.squareup.moshi.Json

data class BooksStoreDto(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)
