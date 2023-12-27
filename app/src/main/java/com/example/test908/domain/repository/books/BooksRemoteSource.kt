package com.example.test908.domain.repository.books

import com.example.test908.data.repository.books.model.BooksResponseDto

interface BooksRemoteSource {
    suspend fun getBooksRemote(date: String): BooksResponseDto
}
