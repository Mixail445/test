package com.example.test908.data.repository.books.limitedSeries.remote

import com.example.test908.data.repository.books.limitedSeries.LimitedSeriesApi
import com.example.test908.data.repository.books.limitedSeries.LimitedSeriesRegistrationStatus
import com.example.test908.data.repository.books.limitedSeries.PostCompleteRegistrationsParams
import com.example.test908.data.repository.books.limitedSeries.model.BookOfferDto
import com.example.test908.data.repository.review.remote.DispatchersProvider
import com.example.test908.domain.repository.limitedSeries.LimitedSeriesRemoteSource
import kotlinx.coroutines.withContext

class LimitedSeriesRemoteSourceImpl(
    private val api: LimitedSeriesApi,
    private val dispatchersProvider: DispatchersProvider
) : LimitedSeriesRemoteSource {
    override suspend fun status(): LimitedSeriesRegistrationStatus = withContext(
        dispatchersProvider.io
    ) {
        api.status()
    }
    override suspend fun getBookOffers(): List<BookOfferDto> = withContext(dispatchersProvider.io) {
        api.getBookOffers()
    }

    override suspend fun startRegistration() = withContext(dispatchersProvider.io) {
       api.startRegistration()
    }

    override suspend fun postCompleteRegistrationParams(params: PostCompleteRegistrationsParams) = withContext(
        dispatchersProvider.io
    ) {
        api.postCompleteRegistrationParams(params)
    }

}
