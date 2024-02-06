package com.example.test908.domain.repository.limitedSeries.model

import com.example.test908.presentation.rcWithDate.BookOfferUi

data class BookOffer(
    val id: String,
    val expiresDate: String,
    val title: String,
    val description: String,
    val price: String
) {
    fun mapToUi() =
        BookOfferUi(
            itemId = id,
            expiresDate = expiresDate,
            title = title,
            description = description,
            price = price
        )
}
