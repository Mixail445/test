package com.example.test908.domain.repository.limitedSeries

import com.example.test908.data.repository.books.limitedSeries.LimitedSeriesRegistrationStatus
import com.example.test908.data.repository.books.limitedSeries.PostCompleteRegistrationsParams
import com.example.test908.domain.repository.limitedSeries.model.BookOffer
import com.example.test908.utils.AppResult

interface LimitedSeriesRepository {
    suspend fun status(): AppResult<LimitedSeriesRegistrationStatus, Throwable>

    suspend fun getBooksOffer(): AppResult<List<BookOffer>, Throwable>

    suspend fun startRegistration(): AppResult<Unit, Throwable>

    suspend fun postCompleteRegistrationParams(params: PostCompleteRegistrationsParams): AppResult<Unit, Throwable>
}
