package com.example.test908.data.repository

import com.example.test908.data.repository.books.limitedSeries.LimitedSeriesRegistrationStatus
import com.example.test908.data.repository.books.limitedSeries.PostCompleteRegistrationsParams
import com.example.test908.domain.repository.limitedSeries.LimitedSeriesRemoteSource
import com.example.test908.domain.repository.limitedSeries.LimitedSeriesRepository
import com.example.test908.domain.repository.limitedSeries.model.BookOffer
import com.example.test908.utils.AppResult
import com.example.test908.utils.ResultWrapper
import javax.inject.Inject

class
LimitedSeriesRepositoryImpl
    @Inject
    constructor(
        private val remoteSource: LimitedSeriesRemoteSource,
        private val wrapper: ResultWrapper,
    ) : LimitedSeriesRepository {
        override suspend fun getBooksOffer(): AppResult<List<BookOffer>, Throwable> =
            wrapper.wrap {
                remoteSource.getBookOffers().map { it.mapToDomain() }
            }

        override suspend fun status(): AppResult<LimitedSeriesRegistrationStatus, Throwable> =
            wrapper.wrap {
                remoteSource.status()
            }

        override suspend fun startRegistration(): AppResult<Unit, Throwable> =
            wrapper.wrap {
                remoteSource.startRegistration()
            }

        override suspend fun postCompleteRegistrationParams(params: PostCompleteRegistrationsParams): AppResult<Unit, Throwable> =
            wrapper.wrap {
                remoteSource.postCompleteRegistrationParams(params)
            }
    }
