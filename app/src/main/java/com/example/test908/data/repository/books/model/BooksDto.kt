package com.example.test908.data.repository.books.model

import com.squareup.moshi.Json

data class BooksDto(
    @Json(name = "books")
    val books: List<BooksDataDto>
)
