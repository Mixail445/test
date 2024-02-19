package com.example.test908.domain.repository.books

import com.example.test908.domain.repository.books.model.Books
import com.example.test908.utils.AppResult

interface BooksRepository {
    suspend fun getBooksRemote(date: String): AppResult<List<Books>, Throwable>
}
