package com.example.test908.presentation.books

import com.example.test908.data.repository.books.limitedSeries.LimitedSeriesRegistrationStatus
import com.example.test908.domain.repository.books.model.Books
import com.example.test908.domain.repository.featureFlag.FeatureFlags
import com.example.test908.presentation.common.BaseItem

interface BooksStore {
    data class State(
        val isLoading: Boolean = false,
        val books: List<Books> = emptyList(),
        val bannerUi: List<BannerUi> = emptyList(),
        val blackBannerUi: List<BlackBannerUi> = emptyList(),
        val bannerUiEmpty: List<BannerUiEmpty> = emptyList(),
        val featureFlags: FeatureFlags? = null,
        val limitedSeriesBannerStatus: LimitedSeriesRegistrationStatus? = null,
    ) {
        fun mapToUi(): BooksView.Model {
            return BooksView.Model(
                isLoading = isLoading,
                items = mapToItems(),
            )
        }

        private fun mapToItems(): List<BaseItem> {
            val items = mutableListOf<BaseItem>()

            if (featureFlags?.isLimitedSeriesEnabled == true) {
                when (limitedSeriesBannerStatus) {
                    LimitedSeriesRegistrationStatus.NOT_STARTED -> {
                        items += bannerUi
                    }

                    LimitedSeriesRegistrationStatus.STARTED -> {
                        items += bannerUiEmpty
                    }

                    LimitedSeriesRegistrationStatus.COMPLETED -> {
                        items += blackBannerUi
                    }

                    LimitedSeriesRegistrationStatus.NOT_ALLOWED, null -> Unit
                }
            }
            items += books.map { it.mapToUi() }
            return items
        }
    }
}
