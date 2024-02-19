package com.example.test908.data.repository.books.limitedSeries

import com.example.test908.data.repository.books.limitedSeries.model.BookOfferDto

/**
 * [IMITATION API]
 */
interface LimitedSeriesApi {
    suspend fun status(): LimitedSeriesRegistrationStatus

    suspend fun getBookOffers(): List<BookOfferDto>

    suspend fun startRegistration()

    suspend fun postCompleteRegistrationParams(params: PostCompleteRegistrationsParams)
}

data class PostCompleteRegistrationsParams(val promoCode: String?)

enum class LimitedSeriesRegistrationStatus {
    NOT_STARTED,
    NOT_ALLOWED,
    STARTED,
    COMPLETED,
}
