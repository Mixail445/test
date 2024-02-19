package com.example.test908.domain.repository.limitedSeries.model

import com.example.test908.presentation.limitedSeriesBooks.BookOfferUi
import com.example.test908.utils.format
import java.time.LocalDate

data class BookOffer(
    val id: String,
    val expiresDate: LocalDate?,
    val title: String,
    val description: String,
    val price: String,
) {
    fun mapToUi() =
        BookOfferUi(
            itemId = id,
            expiresDate = expiresDate?.format("dd MMM yyyy").orEmpty(),
            title = title,
            description = description,
            price = price,
        )
}
