package com.example.test908.domain.repository.limitedSeries

import com.example.test908.data.repository.books.limitedSeries.LimitedSeriesRegistrationStatus
import com.example.test908.data.repository.books.limitedSeries.PostCompleteRegistrationsParams
import com.example.test908.data.repository.books.limitedSeries.model.BookOfferDto

interface LimitedSeriesRemoteSource {
    suspend fun status(): LimitedSeriesRegistrationStatus
    suspend fun getBookOffers(): List<BookOfferDto>
    suspend fun startRegistration()
    suspend fun postCompleteRegistrationParams(params: PostCompleteRegistrationsParams)
}
