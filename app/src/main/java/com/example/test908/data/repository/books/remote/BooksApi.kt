package com.example.test908.data.repository.books.remote

import com.example.test908.data.repository.books.model.BooksResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *[API DOCUMENTATION](https://developer.nytimes.com/docs/books-product/1/overview)
 */
interface BooksApi {
    /**
     * Api books[here](https://api.nytimes.com/svc/books/v3/lists/2024-02-19/hardcover-fiction.json?&api-key=GW5a0tJfWOcfQ7k3dpQizIsrmpZ33Bmm)
     */
    @GET("books/v3/lists/{date}/hardcover-fiction.json?")
    suspend fun getBooks(
        @Path("date") date: String,
    ): BooksResponseDto
}
