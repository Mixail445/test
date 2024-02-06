package com.example.test908.presentation.rcWithDate

import com.example.test908.presentation.common.BaseItem
data class BookOfferUi(
        override val itemId: String,
        val expiresDate: String,
        val title: String,
        val description: String,
        val price: String
) : BaseItem {
        fun mapToDate() = BookDateUi(
                itemId = itemId,
                date = expiresDate
        )
}

