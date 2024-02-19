package com.example.test908.presentation.limitedSeriesBooks

import com.example.test908.domain.repository.limitedSeries.model.BookOffer
import com.example.test908.presentation.common.BaseItem
import com.example.test908.utils.format

interface LimitedSeriesBooks {
    data class State(
        val isLoading: Boolean = false,
        val books: List<BookOffer> = emptyList(),
        val date: List<BookDateUi> = emptyList(),
    ) {
        private fun mapToItems(): List<BaseItem> {
            val items = mutableListOf<BaseItem>()
            val old = books
            val groupMap = old.sortedBy { it.expiresDate }.groupBy { it.expiresDate }
            groupMap.forEach { (k, v) ->
                items.add(BookDateUi(date = k?.format("dd MMM yyyy").orEmpty(), itemId = ""))
                items.addAll(v.map { it.mapToUi() })
            }
            return items
        }

        fun mapToUi(): LimitedSeriesView.Model {
            return LimitedSeriesView.Model(
                items = mapToItems(),
            )
        }
    }
}
