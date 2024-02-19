package com.example.test908.data.repository

import com.example.test908.domain.repository.books.BooksRemoteSource
import com.example.test908.domain.repository.books.BooksRepository
import com.example.test908.domain.repository.books.model.Books
import com.example.test908.utils.AppResult
import com.example.test908.utils.ResultWrapper
import javax.inject.Inject

class BooksRepositoryImpl@Inject constructor(
    private val remoteSource: BooksRemoteSource,
    private val wrapper: ResultWrapper
) : BooksRepository {
    
    override suspend fun getBooksRemote(date: String): AppResult<List<Books>, Throwable> = wrapper.wrap {
        remoteSource.getBooksRemote(date).results.books.mapNotNull { it.mapToDomain() }
    }

}
