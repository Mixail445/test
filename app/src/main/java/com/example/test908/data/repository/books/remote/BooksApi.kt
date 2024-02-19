package com.example.test908.data.repository.books.remote

import com.example.test908.data.repository.books.model.BooksResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BooksApi {
    @GET("books/v3/lists/{date}/hardcover-fiction.json?")
    suspend fun getBooks(@Path("date") date: String): BooksResponseDto
}
