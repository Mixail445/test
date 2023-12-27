package com.example.test908.data.repository.books.remote

import com.example.test908.data.repository.books.model.BooksResponseDto
import com.example.test908.data.repository.review.remote.DispatchersProvider
import com.example.test908.domain.repository.books.BooksRemoteSource
import kotlinx.coroutines.withContext

class BooksRemoteSourceImpl(
private val api: BooksApi,
private val dispatchersProvider: DispatchersProvider
) : BooksRemoteSource {
    override suspend fun getBooksRemote(date: String): BooksResponseDto = withContext(
        dispatchersProvider.io
    ) {
        api.getBooks(date)
    }
}
